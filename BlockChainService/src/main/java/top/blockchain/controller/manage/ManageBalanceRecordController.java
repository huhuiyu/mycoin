package top.blockchain.controller.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.blockchain.model.admin.BalanceRecordModel;
import top.blockchain.service.BalanceRecordService;
import top.blockchain.util.JsonMessage;

@RestController
@RequestMapping("/manage/balanceRecord")
public class ManageBalanceRecordController {

  @Autowired
  private BalanceRecordService balanceRecordService;

  public ManageBalanceRecordController() {
  }

  @RequestMapping("/queryBalanceRecord")
  public JsonMessage queryBalanceRecord(BalanceRecordModel model) throws Exception {
    return balanceRecordService.queryAll(model);
  }

  @RequestMapping("/cancel")
  public JsonMessage cancel(BalanceRecordModel model) throws Exception {
    return balanceRecordService.cancel(model);
  }

}
