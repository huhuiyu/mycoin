package top.blockchain.controller.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.blockchain.model.admin.ManageUserBalanceRecordModel;
import top.blockchain.service.ManageUserBalanceRecordService;
import top.blockchain.util.JsonMessage;

@RestController
@RequestMapping("/manage/userbalancerecord")
public class ManageUserBalanceRecordController {
  @Autowired
 private ManageUserBalanceRecordService manageBalanceRecordService;
  @RequestMapping("/queryAll")
  public JsonMessage queryAll( ManageUserBalanceRecordModel model) throws Exception{
    return manageBalanceRecordService.queryAll(model);
  }

}
