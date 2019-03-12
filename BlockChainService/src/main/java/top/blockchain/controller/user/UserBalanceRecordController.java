package top.blockchain.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.blockchain.converter.StringConverter;
import top.blockchain.model.user.UserBalanceRecordModel;
import top.blockchain.service.UserBalanceRecordService;
import top.blockchain.util.JsonMessage;

@RestController
@RequestMapping("/userBalanceRecord")
public class UserBalanceRecordController {

  @Autowired
  private UserBalanceRecordService userBalanceRecordService;

  @RequestMapping("/addBalanceRecord")
  public JsonMessage addBalanceRecord(UserBalanceRecordModel model) throws Exception {
    return userBalanceRecordService.addBalanceRecord(model);
  }

  @RequestMapping("/addBuyBalanceRecord")
  public JsonMessage addBuyBalanceRecord(UserBalanceRecordModel model) throws Exception {
    return userBalanceRecordService.addBuyBalanceRecord(model);
  }

  @RequestMapping("/sell")
  public JsonMessage sell(UserBalanceRecordModel model) throws Exception {
    return userBalanceRecordService.sell(model);
  }

  @RequestMapping("/pay")
  public JsonMessage pay(UserBalanceRecordModel model) throws Exception {
    if (StringConverter.isEmpty(model.getPaypwd())) {
      return JsonMessage.getFailMessage("支付密码不能为空！");
    }
    return userBalanceRecordService.pay(model);
  }

  @RequestMapping("/cancel")
  public JsonMessage cancel(UserBalanceRecordModel model) throws Exception {
    if (StringConverter.isEmpty(model.getPaypwd())) {
      return JsonMessage.getFailMessage("支付密码不能为空！");
    }
    return userBalanceRecordService.cancel(model);
  }

  @RequestMapping("/finish")
  public JsonMessage finish(UserBalanceRecordModel model) throws Exception {
    if (StringConverter.isEmpty(model.getPaypwd())) {
      return JsonMessage.getFailMessage("支付密码不能为空！");
    }
    return userBalanceRecordService.finish(model);
  }
@RequestMapping("/recast")
  public JsonMessage ReadOnlyClassToSerializerMap(UserBalanceRecordModel model) throws Exception{
    return userBalanceRecordService.recast(model);
  }
  @RequestMapping("/queryUserNowOrder")
  public JsonMessage queryUserNowOrder(UserBalanceRecordModel model) throws Exception {
    return userBalanceRecordService.queryUserNowOrder(model);
  }

  @RequestMapping("/sendToUser")
  public JsonMessage sendToUser(UserBalanceRecordModel model) throws Exception {
    if (StringConverter.isEmpty(model.getPaypwd())) {
      return JsonMessage.getFailMessage("支付密码不能为空！");
    }
    return userBalanceRecordService.sendToUser(model);
  }

  @RequestMapping("/queryBuys")
  public JsonMessage queryBuys(UserBalanceRecordModel model) throws Exception {
    return userBalanceRecordService.queryBuys(model);
  }

  @RequestMapping("/queryUserOrders")
  public JsonMessage queryUserOrders(UserBalanceRecordModel model) throws Exception {
    return userBalanceRecordService.queryUserOrders(model);
  }

  // @RequestMapping("/queryBstatus")
  // public JsonMessage queryBstatus(UserBalanceRecordModel model) throws
  // Exception {
  // return userBalanceRecordService.queryBstatus(model);
  // }
  //
  // @RequestMapping("/queryBstatusmyself")
  // public JsonMessage queryBstatuc(UserBalanceRecordModel model) throws
  // Exception {
  // return userBalanceRecordService.queryBstatusmyself(model);
  // }
}
