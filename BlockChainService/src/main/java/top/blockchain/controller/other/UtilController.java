package top.blockchain.controller.other;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.blockchain.controller.NotNeedLogin;
import top.blockchain.model.other.PhoneCodeModel;
import top.blockchain.model.user.UserUtilModel;
import top.blockchain.service.ConfigService;
import top.blockchain.service.TokenInfoService;
import top.blockchain.util.ImageCode;
import top.blockchain.util.JsonMessage;

@RestController
@RequestMapping("/util")
public class UtilController implements NotNeedLogin {

  @Autowired
  private TokenInfoService tokenInfoService;
  @Autowired
  private ConfigService configService;

  @RequestMapping("/sendPhoneCode")
  public JsonMessage sendPhoneCode(PhoneCodeModel model) throws Exception {
    if (model.getPhoneCode().getPhone().length() != 11) {
      return JsonMessage.getFailMessage("电话号码只能是11位数！");
    }
    return tokenInfoService.sendPhoneCode(model);
  }

  @RequestMapping("/validate.jpg")
  public void imageCode(UserUtilModel model, HttpServletResponse response) throws Exception {
    ImageIO.write(ImageCode.makeImage(tokenInfoService.imageCode(model.getServertoken())), "jpeg", response.getOutputStream());
  }

  @RequestMapping("/getConfig")
  public JsonMessage getConfig(UserUtilModel model) throws Exception {
    JsonMessage message = JsonMessage.getSuccessMessage("读取成功");
    message.putData("config", configService.queryDownloadSiteConfig());
    return message;
  }
}
