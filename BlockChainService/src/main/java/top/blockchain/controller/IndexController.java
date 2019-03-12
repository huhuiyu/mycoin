package top.blockchain.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.blockchain.model.IndexModel;
import top.blockchain.util.JsonMessage;

@RestController
public class IndexController implements NotNeedLogin {

  @RequestMapping(path = "/")
  public JsonMessage index(IndexModel model) throws Exception {
    return JsonMessage.getSuccessMessage("欢迎使用区块链服务!");
  }

}
