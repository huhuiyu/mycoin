package top.blockchain.controller.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.blockchain.model.admin.ManageConfigModel;
import top.blockchain.service.ConfigService;
import top.blockchain.util.JsonMessage;

@RestController
@RequestMapping("/manage/config")
public class ManageConfigController {

  @Autowired
  private ConfigService configService;

  @RequestMapping("/loadConfig")
  public JsonMessage loadConfig(ManageConfigModel model) throws Exception {
    return configService.loadConfig();
  }

  @RequestMapping("/modifySms")
  public JsonMessage modifySms(ManageConfigModel model) throws Exception {
    return configService.modifySms(model);
  }

  @RequestMapping("/modifyPhoneCheck")
  public JsonMessage modifyPhoneCheck(ManageConfigModel model) throws Exception {
    return configService.modifyPhoneCheck(model);
  }

  @RequestMapping("/modifySiteConfig")
  public JsonMessage modifySiteConfig(ManageConfigModel model) throws Exception {
    return configService.modifySiteConfig(model);
  }

}
