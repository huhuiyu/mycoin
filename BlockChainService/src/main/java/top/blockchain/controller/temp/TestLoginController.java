package top.blockchain.controller.temp;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.blockchain.model.temp.TestLoginModel;
import top.blockchain.util.JsonMessage;

@RestController
@RequestMapping("/temp/login")
public class TestLoginController {

  public TestLoginController() {
  }

  @RequestMapping("/test")
  public JsonMessage test(TestLoginModel model) throws Exception {
    // /temp/login/test {}
    return JsonMessage.getSuccessMessage("测试成功！");
  }
}
