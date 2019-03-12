package top.blockchain.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.blockchain.converter.StringConverter;
import top.blockchain.model.UserInfoModel;
import top.blockchain.model.user.UserModel;
import top.blockchain.service.UserInfoService;
import top.blockchain.util.JsonMessage;

@RestController
@RequestMapping("/user")
public class UserInfoController {
  @Autowired
  private UserInfoService userInfoService;

  @RequestMapping("/modifyPassword")
  public JsonMessage modifyPassword(UserModel model) throws Exception {
    String password = model.getUser().getPassword();
    if (StringConverter.isEmpty(model.getUser().getPassword()) || password.length() < 6 || password.length() > 16) {
      return JsonMessage.getFailMessage("密码不能为空且大于6位数小于16位数！");
    }
    model.getUser().setUid(model.getLoginUser().getUid());
    return userInfoService.modifyPassword(model.getUser());
  }

  @RequestMapping("/modifyPaypwd")
  public JsonMessage modifyPaypwd(UserModel model) throws Exception {
    if (StringConverter.isEmpty(model.getUser().getPaypwd()) || model.getUser().getPaypwd().length() != 6) {
      return JsonMessage.getFailMessage("支付密码不能为空且为6位数！！");
    }
    model.getUser().setUid(model.getLoginUser().getUid());
    return userInfoService.modifyPaypwd(model.getUser());
  }

  @RequestMapping("/modifyWechat")
  public JsonMessage modifyWechat(UserInfoModel model) throws Exception {
    if (StringConverter.isEmpty(model.getUserInfo().getWechat())) {
      return JsonMessage.getFailMessage("微信号不能为空");
    }
    return userInfoService.modifyWechat(model);
  }

  @RequestMapping("/modifyAlipay")
  public JsonMessage modifyAlipay(UserInfoModel model) throws Exception {
    if (StringConverter.isEmpty(model.getUserInfo().getAlipay())) {
      return JsonMessage.getFailMessage("支付宝不能为空");
    }
    return userInfoService.modifyAlipay(model);
  }

  @RequestMapping("/modifyNickName")
  public JsonMessage modifyNickName(UserInfoModel model) throws Exception {
    if (StringConverter.isEmpty(model.getUserInfo().getNickname())) {
      return JsonMessage.getFailMessage("昵称不能为空");
    }
    return userInfoService.modifyNickName(model);
  }

  @RequestMapping("/queryUserInfoByKey")
  public JsonMessage queryUserInfoByKey(UserInfoModel model) throws Exception {
    return userInfoService.queryUserInfoByKey(model);
  }

  @RequestMapping("/vip")
  public JsonMessage vip(UserInfoModel model) throws Exception {
    if (StringConverter.isEmpty(model.getPaypwd())) {
      return JsonMessage.getFailMessage("支付密码不能为空！");
    }
    return userInfoService.vip(model);
  }

  @RequestMapping("/queryBalanceRecord")
  public JsonMessage queryBalanceRecord(UserInfoModel model) throws Exception {
    return userInfoService.queryBalanceRecord(model);
  }

}
