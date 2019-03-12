package top.blockchain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import top.blockchain.config.MyAppConfigBean;
import top.blockchain.dao.TbUserDAO;
import top.blockchain.dao.TbUserInfoDAO;
import top.blockchain.dao.TbUserPhoneCheckDAO;
import top.blockchain.entity.PhoneCheck;
import top.blockchain.entity.TbUser;
import top.blockchain.entity.TbUserInfo;
import top.blockchain.entity.TbUserPhoneCheck;
import top.blockchain.model.user.UserUtilModel;
import top.blockchain.service.ConfigService;
import top.blockchain.service.UserUtilService;
import top.blockchain.util.JsonMessage;
import top.blockchain.util.JsonUtil;
import top.blockchain.util.SmsUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserUtilServiceImpl implements UserUtilService {

  @Autowired
  private TbUserDAO tbUserDAO;
  @Autowired
  private TbUserInfoDAO tbUserInfoDAO;
  @Autowired
  private TbUserPhoneCheckDAO tbUserPhoneCheckDAO;
  @Autowired
  private MyAppConfigBean myAppConfigBean;
  @Autowired
  private ConfigService configService;

  public UserUtilServiceImpl() {
  }

  @Override
  public JsonMessage phoneCheck(UserUtilModel model) throws Exception {
    // 校验用户信息
    TbUser user = tbUserDAO.queryByPhone(model.getLoginUser());
    if (user == null) {
      return JsonMessage.getFailMessage("用户登陆信息不正确");
    }
    model.getPhoneCheck().setMobile(user.getPhone());
    // 查询是否存在
    TbUserPhoneCheck scheck = tbUserPhoneCheckDAO.queryByPhone(model.getLoginUser().getPhone());
    if (scheck != null && "01".equals(scheck.getStatus())) { // 存在认证信息且认证已经通过
      return JsonMessage.getFailMessage("已经认证通过，不需要再次认证");
    }
    if (scheck != null && scheck.getLastCheck() < myAppConfigBean.phoneCheck) { // 存在认证信息且认证时间小于间隔
      return JsonMessage.getFailMessage("认证需要间隔" + myAppConfigBean.phoneCheck + "分钟，请稍后再试");
    }
    // 发起认证
    PhoneCheck checkInfo = configService.queryPhoneCheck();
    String result = SmsUtil.getPhoneCheck(model.getPhoneCheck().getIdCard(), model.getPhoneCheck().getMobile(), model.getPhoneCheck().getName(), checkInfo.getUrl(), checkInfo.getAppCode());
    TbUserPhoneCheck oresult = JsonUtil.parse(result, TbUserPhoneCheck.class);
    oresult.setUid(user.getUid());
    if (scheck == null) { // 数据不存在就添加，否则就修改
      tbUserPhoneCheckDAO.add(oresult);
    } else {
      tbUserPhoneCheckDAO.modify(oresult);
    }
    if (!"01".equals(oresult.getStatus())) { // 认证通过
      return JsonMessage.getFailMessage("认证失败：" + oresult.getMsg());
    }
    // 需要将用户身份证信息写入TbUserInfo表
    TbUserInfo userInfo = new TbUserInfo();
    userInfo.setUid(user.getUid());
    userInfo.setIdCard(model.getPhoneCheck().getIdCard());
    userInfo.setNickname(model.getPhoneCheck().getName());
    int uresult = tbUserInfoDAO.modifyIdCard(userInfo);
    if (uresult != 1) {
      return JsonMessage.getFailMessage("更新身份证信息失败");
    }
    return JsonMessage.getSuccessMessage("认证成功");
  }

}
