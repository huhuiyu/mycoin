package top.blockchain.controller.temp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.blockchain.controller.NotNeedLogin;
import top.blockchain.model.temp.TestModel;
import top.blockchain.service.ConfigService;
import top.blockchain.util.JsonMessage;

@RestController
@RequestMapping("/temp/test")
public class TestController implements NotNeedLogin {

  @Autowired
  private ConfigService configService;

  @RequestMapping("")
  public JsonMessage index(TestModel model) {
    return JsonMessage.getSuccessMessage("测试");
  }

  @RequestMapping("/addSmsConfig")
  public JsonMessage addSmsConfig(TestModel model) throws Exception {
    // /temp/test/addSmsConfig {"sms.product":"abc"}
    configService.addSmsConfig(model.getSms());
    return JsonMessage.getSuccessMessage("测试");
  }

  @RequestMapping("/loadSmsConfig")
  public JsonMessage loadSmsConfig(TestModel model) throws Exception {
    // /temp/test/loadSmsConfig {}
    JsonMessage message = JsonMessage.getSuccessMessage("测试");
    message.putData("sms", configService.querySms());
    return message;
  }
}
