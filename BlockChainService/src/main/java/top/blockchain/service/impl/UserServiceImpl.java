package top.blockchain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import top.blockchain.converter.StringConverter;
import top.blockchain.dao.TbBlockIdsDAO;
import top.blockchain.dao.TbUserDAO;
import top.blockchain.dao.TbUserInfoDAO;
import top.blockchain.entity.PhoneCode;
import top.blockchain.entity.TbTokenInfo;
import top.blockchain.entity.TbUser;
import top.blockchain.entity.TbUserInfo;
import top.blockchain.exception.MyAppException;
import top.blockchain.model.admin.ManageUserModel;
import top.blockchain.model.user.UserModel;
import top.blockchain.service.TokenInfoService;
import top.blockchain.service.UserService;
import top.blockchain.util.JsonMessage;
import top.blockchain.util.Md5;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
  public static final String TOKEN_PERFIX = "user";
  @Autowired
  private TbUserDAO tbUserDAO;
  @Autowired
  private TbUserInfoDAO tbUserInfoDAO;
  @Autowired
  private TbBlockIdsDAO tbBlockIdsDAO;
  @Autowired
  private TokenInfoService tokenInfoService;

  @Override
  public int undelete(TbUser user) throws Exception {
    return tbUserDAO.undelete(user);
  }

  @Override
  public JsonMessage queryAll(ManageUserModel model) throws Exception {
    if (!StringConverter.isEmpty(model.getTbUser().getPhone())) {
      model.getTbUser().setPhone("%" + model.getTbUser().getPhone() + "%");
    }
    PageHelper.startPage(model.getPage().getPageNumber(), model.getPage().getPageSize());
    Page<TbUser> list = (Page<TbUser>) tbUserDAO.queryAll(model.getTbUser());
    model.getPage().setPageInfo(list);
    JsonMessage message = JsonMessage.getSuccessMessage("查询成功！");
    message.putData("list", list);
    message.putData("page", model.getPage());
    TbUserInfo total = tbUserInfoDAO.queryTotalBalance();
    message.putData("total", total);
    return message;
  }

  @Override
  public void add(UserModel model) throws Exception {
    TbUser user = model.getUser();
    // 通过提交的blockId找到linkman的uid
    int uid = tbUserDAO.blockid(user);
    if (uid <= 0) {
      throw MyAppException.getMyAppException("推荐人不存在");
    }
    user.setLinkman(uid);
    // 获取用户自己的blockId
    String blockId = tbBlockIdsDAO.queryId();
    tbBlockIdsDAO.updateisUser(blockId);

    user.setBlockId(blockId);

    int phone = tbUserDAO.queryFound(user);
    if (phone > 0) {
      throw MyAppException.getMyAppException("电话号码已存在！");
    }
    // 获取电话校验码
    PhoneCode phoneCode = tokenInfoService.getPhoneCode(model.getServertoken());
    if (phoneCode == null || !phoneCode.getPhone().equals(user.getPhone()) || !phoneCode.getCode().equals(model.getPhoneCode())) {
      throw MyAppException.getMyAppException("电话校验码错误！");
    }
    // 添加tokenId
    user.setTokenId(Md5.md5(String.format("%s%s", TOKEN_PERFIX, user.getPhone())));
    int r = tbUserDAO.add(user);
    if (r != 1 || user.getUid() <= 0) {
      throw MyAppException.getMyAppException("注册失败！");
    }
    // 添加用户附加信息
    TbUserInfo userInfo = new TbUserInfo();
    userInfo.setUid(user.getUid());
    // 直接通过实名认证(电话号码作为idCard写入)
    userInfo.setIdCard(user.getPhone());
    r = tbUserInfoDAO.addNew(userInfo);
    if (r != 1) {
      throw MyAppException.getMyAppException("附加信息添加失败！");
    }
  }

  @Override
  public int delete(TbUser user) throws Exception {
    return tbUserDAO.delete(user);
  }

  @Override
  public int modify(TbUser user) throws Exception {
    return tbUserDAO.modify(user);
  }

  @Override
  public JsonMessage login(UserModel model) throws Exception {
    TbUser user = tbUserDAO.queryphone(model.getUser());
    if (user == null) {
      return JsonMessage.getFailMessage("用户不存在");
    }

    user = tbUserDAO.login(model.getUser());
    if (user == null) {
      return JsonMessage.getFailMessage("密码错误，登陆失败");
    }
    if ("n".equals(user.getIsEnable())) {
      return JsonMessage.getFailMessage("用户已冻结");
    }
    // 更新Token中的登陆用户信息
    TbTokenInfo info = new TbTokenInfo();
    info.setToken(model.getServertoken());
    info.setInfo("" + user.getUid());
    int r = tokenInfoService.addUserInfo(user.getUid(), model.getServertoken());
    if (r != 1) {
      return JsonMessage.getFailMessage("登陆信息保存失败。");
    }
    return JsonMessage.getSuccessMessage("登录成功！");
  }

  @Override
  public JsonMessage foundPassword(UserModel model) throws Exception {
    TbUser user = model.getUser();
    int phone = tbUserDAO.queryFound(user);
    if (phone != 1) {
      return JsonMessage.getFailMessage("电话不存在！");
    }
    PhoneCode phoneCode = tokenInfoService.getPhoneCode(model.getServertoken());
    if (phoneCode == null || !phoneCode.getPhone().equals(user.getPhone()) || !phoneCode.getCode().equals(model.getPhoneCode())) {
      return JsonMessage.getFailMessage("电话效验码错误！");
    }
    tbUserDAO.foundPassword(user);
    return JsonMessage.getSuccessMessage("密码找回成功！");
  }

  @Override
  public JsonMessage queryTeam(UserModel model) throws Exception {
    List<TbUser> list = tbUserDAO.queryTeam(model.getLoginUser());
    JsonMessage message = JsonMessage.getSuccessMessage("查询成功!");
    message.putData("list", list);
    return message;

  }
}
