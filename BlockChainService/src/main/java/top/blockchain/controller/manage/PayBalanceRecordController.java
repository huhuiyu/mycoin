package top.blockchain.controller.manage;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.blockchain.converter.IntegerConverter;
import top.blockchain.converter.StringConverter;
import top.blockchain.model.admin.PayBalanceRecordModel;
import top.blockchain.service.PayBalanceRecordService;
import top.blockchain.util.JsonMessage;

@RestController
@RequestMapping("/manage/paybalancerecord")
public class PayBalanceRecordController {

  @Autowired
  private PayBalanceRecordService payBalanceRecordService;

  public PayBalanceRecordController() {
  }

  @RequestMapping("/addPayBalanceRecord")
  public JsonMessage addPayBalanceRecord(PayBalanceRecordModel model) throws Exception {
    // /manage/paybalancerecord/addPayBalanceRecord
    // {"record.balance":200,"record.target":1,"record.uid":2,"record.info":"测试1"}
    if (model.getRecord().getBalance() == null || BigDecimal.ZERO.compareTo(model.getRecord().getBalance()) > 0) {
      return JsonMessage.getFailMessage("资金必须填写成大于0的数");
    }
    if (!model.getRecord().getTarget().equals("1") && !model.getRecord().getTarget().equals("2")) {
      return JsonMessage.getFailMessage("发币类型不正确");
    }
    if (IntegerConverter.isEmpty(model.getRecord().getUid())) {
      return JsonMessage.getFailMessage("发币用户不能为空");
    }
    if (StringConverter.isEmpty(model.getRecord().getInfo())) {
      return JsonMessage.getFailMessage("发币的说明附加信息不能为空");
    }

    payBalanceRecordService.addPayBalanceRecord(model);
    return JsonMessage.getSuccessMessage("发币成功！");
  }

}
