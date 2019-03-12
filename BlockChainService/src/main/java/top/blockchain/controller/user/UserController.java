package top.blockchain.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.blockchain.controller.NotNeedLogin;
import top.blockchain.converter.StringConverter;
import top.blockchain.model.user.UserModel;
import top.blockchain.service.TokenInfoService;
import top.blockchain.service.UserService;
import top.blockchain.util.JsonMessage;

@RestController
@RequestMapping("/user")
public class UserController implements NotNeedLogin {
  @Autowired
  private UserService userService;
  @Autowired
  private TokenInfoService tokenInfoService;

  @RequestMapping("/add")
  public JsonMessage add(UserModel model) throws Exception {
    String password = model.getUser().getPassword();
    if (StringConverter.isEmpty(model.getUser().getPhone()) || model.getUser().getPhone().length() != 11) {
      return JsonMessage.getFailMessage("电话号码不能为空且只能是11位数！");
    }
    if (StringConverter.isEmpty(model.getUser().getPassword()) || password.length() < 6 || password.length() > 16) {
      return JsonMessage.getFailMessage("密码不能为空且大于6位数小于16位数！");
    }
    if (StringConverter.isEmpty(model.getUser().getPaypwd()) || model.getUser().getPaypwd().length() != 6) {
      return JsonMessage.getFailMessage("支付密码不能为空且为6位数！！");
    }
    if (StringConverter.isEmpty(model.getUser().getBlockId()) || model.getUser().getBlockId().length() != 7) {
      return JsonMessage.getFailMessage("区块节点ID不能为空且只能位7位数！");
    }
    if (StringConverter.isEmpty(model.getPhoneCode()) || model.getPhoneCode().length() != 6) {
      return JsonMessage.getFailMessage("电话校验码不能为空且必须为6位数！");
    }
    userService.add(model);
    JsonMessage message = JsonMessage.getSuccessMessage("注册成功");
    return message;
  }

  @RequestMapping("/login")
  public JsonMessage login(UserModel model) throws Exception {
    String password = model.getUser().getPassword();
    if (StringConverter.isEmpty(model.getUser().getPhone()) || model.getUser().getPhone().length() != 11) {
      return JsonMessage.getFailMessage("电话号码不能为空且只能是11位数！");
    }
    if (StringConverter.isEmpty(model.getUser().getPassword()) || password.length() < 6 || password.length() > 16) {
      return JsonMessage.getFailMessage("密码不能为空且大于6位数小于16位数！");
    }
    return userService.login(model);
  }

  @RequestMapping("/logout")
  public JsonMessage logout(UserModel model) throws Exception {
    tokenInfoService.removeUserInfo(model.getServertoken());
    return JsonMessage.getSuccessMessage("退出成功");
  }

  @RequestMapping("/foundPassword")
  public JsonMessage foundPassword(UserModel model) throws Exception {
    String password = model.getUser().getPassword();
    if (StringConverter.isEmpty(model.getUser().getPhone()) || model.getUser().getPhone().length() != 11) {
      return JsonMessage.getFailMessage("电话号码不能为空且只能是11位数");
    }
    if (StringConverter.isEmpty(model.getUser().getPassword()) || password.length() < 6 || password.length() > 16) {
      return JsonMessage.getFailMessage("密码不能为空且大于6位数小于16位数！");
    }
    if (StringConverter.isEmpty(model.getPhoneCode()) || model.getPhoneCode().length() != 6) {
      return JsonMessage.getFailMessage("电话校验码不能为空且必须为6位数！");
    }
    return userService.foundPassword(model);
  }

  @RequestMapping("/queryTeam")
  public JsonMessage queryTeam(UserModel model) throws Exception {
    return userService.queryTeam(model);

  }

}
