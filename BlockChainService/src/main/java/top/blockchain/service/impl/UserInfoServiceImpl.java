package top.blockchain.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import top.blockchain.converter.StringConverter;
import top.blockchain.dao.TbUserBalanceRecordDAO;
import top.blockchain.dao.TbUserDAO;
import top.blockchain.dao.TbUserInfoDAO;
import top.blockchain.entity.SiteConfig;
import top.blockchain.entity.TbUser;
import top.blockchain.entity.TbUserBalanceRecord;
import top.blockchain.entity.TbUserInfo;
import top.blockchain.exception.MyAppException;
import top.blockchain.model.UserInfoModel;
import top.blockchain.service.ConfigService;
import top.blockchain.service.UserInfoService;
import top.blockchain.util.JsonMessage;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserInfoServiceImpl implements UserInfoService {
  @Autowired
  private TbUserDAO tbUserDAO;
  @Autowired
  private TbUserInfoDAO tbUserInfoDAO;
  @Autowired
  private ConfigService configService;
  @Autowired
  private TbUserBalanceRecordDAO tbUserBalanceRecordDAO;

  @Override
  public JsonMessage modifyPassword(TbUser user) throws Exception {
    int r = tbUserDAO.modifyPassword(user);
    return r == 1 ? JsonMessage.getSuccessMessage("修改成功") : JsonMessage.getFailMessage("修改失败");
  }

  @Override
  public JsonMessage modifyPaypwd(TbUser user) throws Exception {
    int r = tbUserDAO.modifyPaypwd(user);
    return r == 1 ? JsonMessage.getSuccessMessage("修改成功") : JsonMessage.getFailMessage("修改失败");
  }

  @Override
  public JsonMessage modifyWechat(UserInfoModel model) throws Exception {
    TbUserInfo userInfo = new TbUserInfo();
    userInfo.setUid(model.getLoginUser().getUid());
    userInfo = tbUserInfoDAO.queryByKey(userInfo);
    if (userInfo == null) {
      return JsonMessage.getFailMessage("用户信息不存在");
    }
    userInfo.setWechat(model.getUserInfo().getWechat());
    int r = tbUserInfoDAO.modify(userInfo);
    return r == 1 ? JsonMessage.getSuccessMessage("微信号修改成功") : JsonMessage.getFailMessage("微信号修改失败");
  }

  @Override
  public JsonMessage modifyNickName(UserInfoModel model) throws Exception {
    TbUserInfo userInfo = new TbUserInfo();
    userInfo.setUid(model.getLoginUser().getUid());
    userInfo = tbUserInfoDAO.queryByKey(userInfo);
    if (userInfo == null) {
      return JsonMessage.getFailMessage("用户信息不存在");
    }
    userInfo.setNickname(model.getUserInfo().getNickname());
    int r = tbUserInfoDAO.modify(userInfo);
    return r == 1 ? JsonMessage.getSuccessMessage("昵称修改成功") : JsonMessage.getFailMessage("昵称修改失败");
  }

  @Override
  public JsonMessage modifyAlipay(UserInfoModel model) throws Exception {
    TbUserInfo userInfo = new TbUserInfo();
    userInfo.setUid(model.getLoginUser().getUid());
    userInfo = tbUserInfoDAO.queryByKey(userInfo);
    if (userInfo == null) {
      return JsonMessage.getFailMessage("用户信息不存在");
    }
    userInfo.setAlipay(model.getUserInfo().getAlipay());
    int r = tbUserInfoDAO.modify(userInfo);
    return r == 1 ? JsonMessage.getSuccessMessage("支付宝修改成功") : JsonMessage.getFailMessage("支付修改失败");
  }

  @Override
  public JsonMessage queryUserInfoByKey(UserInfoModel model) throws Exception {
    TbUserInfo userInfo = new TbUserInfo();
    userInfo.setUid(model.getLoginUser().getUid());
    userInfo = tbUserInfoDAO.queryUserInfoByKey(userInfo);
    JsonMessage message = JsonMessage.getSuccessMessage("查询成功！");
    message.putData("userInfo", userInfo);
    return message;
  }

  @Override
  public JsonMessage vip(UserInfoModel model) throws Exception {
    // 基本信息校验
    TbUserInfo userInfo = new TbUserInfo();
    userInfo.setUid(model.getLoginUser().getUid());
    userInfo = tbUserInfoDAO.queryByKey(userInfo);
    if (userInfo == null) {
      return JsonMessage.getFailMessage("用户信息不存在");
    }
    if ("y".equals(userInfo.getVip())) {
      return JsonMessage.getFailMessage("已经是会员");
    }
    if (StringConverter.isEmpty(userInfo.getIdCard())) {
      return JsonMessage.getFailMessage("必须先实名认证才能交易");
    }
    // 校验支付密码
    // 第一步查询当前登录用户信息
    TbUser user = tbUserDAO.queryByKey(model.getLoginUser());
    if (!user.getPaypwd().equals(model.getPaypwd())) {
      return JsonMessage.getFailMessage("支付密码不正确！");
    }
    // 第二步比对是否相等

    // 读取vip配置
    SiteConfig siteConfig = configService.querySiteConfig();
    BigDecimal vipcost = siteConfig.getVip();
    if (userInfo.getBalance().compareTo(vipcost) < 0) {
      return JsonMessage.getFailMessage(String.format("成为会员需要%s,账号余额%s,请先充值。", vipcost, userInfo.getBalance()));
    }
    // 添加
    TbUserBalanceRecord ubr = new TbUserBalanceRecord();
    ubr.setUid(userInfo.getUid());
    ubr.setBalance(vipcost); // vip扣款金额
    ubr.setBdir(-1); // 资金减少
    ubr.setBtype(15); // vip扣款
    ubr.setInfo("vip扣款");
    int r = tbUserBalanceRecordDAO.save(ubr);
    if (r != 1) {
      throw MyAppException.getMyAppException("资金变动失败，请稍后重试。");
    }
    // 更新会员信息
    userInfo.setBalance(userInfo.getBalance().subtract(vipcost));
    userInfo.setVip("y");
    r = tbUserInfoDAO.modifyVip(userInfo);
    return r == 1 ? JsonMessage.getSuccessMessage("开通会员成功") : JsonMessage.getFailMessage("开通会员失败");
  }

  @Override
  public JsonMessage queryBalanceRecord(UserInfoModel model) throws Exception {
    PageHelper.startPage(model.getPage().getPageNumber(), model.getPage().getPageSize());
    Page<TbUserBalanceRecord> list = (Page<TbUserBalanceRecord>) tbUserBalanceRecordDAO.queryUserAll(model.getLoginUser());
    model.getPage().setPageInfo(list);
    JsonMessage message = JsonMessage.getSuccessMessage("查询成功！");
    message.putData("list", list);
    message.putData("page", model.getPage());
    return message;
  }

}
