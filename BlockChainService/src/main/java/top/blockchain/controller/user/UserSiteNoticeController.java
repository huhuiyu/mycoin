package top.blockchain.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.blockchain.model.user.UserSiteNoticeModel;
import top.blockchain.service.SiteNoticeService;
import top.blockchain.util.JsonMessage;

@RestController
@RequestMapping("/user/siteNotice")
public class UserSiteNoticeController {

  @Autowired
  private SiteNoticeService siteNoticeService;

  public UserSiteNoticeController() {

  }

  @RequestMapping("/queryAll")
  public JsonMessage queryAll(UserSiteNoticeModel model) throws Exception {
    return siteNoticeService.queryUserAll(model);
  }

}
