package top.blockchain.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.blockchain.converter.StringConverter;
import top.blockchain.model.user.UserUtilModel;
import top.blockchain.service.UserUtilService;
import top.blockchain.util.JsonMessage;

@RestController
@RequestMapping("/user/util")
public class UserUtilController {
  @Autowired
  private UserUtilService userUtilService;

  public UserUtilController() {
  }

  @RequestMapping("/phoneCheck")
  public JsonMessage phoneCheck(UserUtilModel model) throws Exception {
    if (StringConverter.isEmpty(model.getPhoneCheck().getIdCard())||model.getPhoneCheck().getIdCard().length()!=18) {
      return JsonMessage.getFailMessage("身份证必须填写且必须是18位数");
    }
    if (StringConverter.isEmpty(model.getPhoneCheck().getName())) {
      return JsonMessage.getFailMessage("姓名必须填写");
    }
    return userUtilService.phoneCheck(model);
  }

}
