package top.blockchain.controller.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.blockchain.model.admin.PayBalanceUpdateModel;
import top.blockchain.service.PayBalanceUpdateService;
import top.blockchain.util.JsonMessage;

@RestController
@RequestMapping("/manage/paybalanceupdate")
public class ManagePayBalanceUpdateController {
  @Autowired
  private PayBalanceUpdateService payBalanceUpdateService;

  @RequestMapping("/query")
  public JsonMessage query(PayBalanceUpdateModel model) throws Exception {
    return payBalanceUpdateService.query(model);
  }
}
