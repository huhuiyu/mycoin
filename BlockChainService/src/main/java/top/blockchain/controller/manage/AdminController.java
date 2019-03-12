package top.blockchain.controller.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import top.blockchain.controller.NotNeedLogin;
import top.blockchain.converter.IntegerConverter;
import top.blockchain.converter.StringConverter;
import top.blockchain.entity.TbAdmin;
import top.blockchain.model.admin.AdminModel;
import top.blockchain.service.AdminService;
import top.blockchain.service.TokenInfoService;
import top.blockchain.util.JsonMessage;

@RestController
@RequestMapping("/manage/admin")
public class AdminController implements NotNeedLogin {

  @Autowired
  private AdminService adminService;
  @Autowired
  private TokenInfoService tokenInfoService;

  @RequestMapping("/login")
  public JsonMessage login(AdminModel model) throws Exception {
    if (model.getAdmin().getUsername() == null) {
      return JsonMessage.getFailMessage("用户名必须填写");
    }
    if (model.getAdmin().getPassword() == null) {
      return JsonMessage.getFailMessage("密码必须填写");
    }
    return adminService.login(model);
  }

  @RequestMapping("/logout")
  public JsonMessage logout(AdminModel model) throws Exception {
    tokenInfoService.removeAdminUserInfo(model.getServertoken());
    return JsonMessage.getSuccessMessage("退出登录成功！");
  }

  @RequestMapping("query")
  public JsonMessage query(AdminModel model) throws Exception {
    PageHelper.startPage(model.getPage().getPageNumber(), model.getPage().getPageSize());
    Page<TbAdmin> list = (Page<TbAdmin>) adminService.query();
    model.getPage().setPageInfo(list);
    JsonMessage message = JsonMessage.getSuccessMessage("查询成功！");
    message.putData("list", list);
    message.putData("page", model.getPage());
    return message;
  }

  @RequestMapping("/modify")
  public JsonMessage modify(AdminModel model) throws Exception {
    if (model.getLoginUser() == null) {
      return JsonMessage.getFailMessage("请先登陆账户！");
    }
    TbAdmin admin = model.getAdmin();
    if (StringConverter.isEmpty(admin.getPassword())) {
      return JsonMessage.getFailMessage("请填写修改的密码");
    }
    if (StringConverter.isEmpty(admin.getIsEnable())) {
      return JsonMessage.getFailMessage("请填写用户是否启用！");
    }
    adminService.modify(admin);
    JsonMessage message = JsonMessage.getSuccessMessage("修改成功");
    message.putData("modify", model.getAdmin());
    return message;
  }

  @RequestMapping("/delete")
  public JsonMessage delete(AdminModel model) throws Exception {
    if (model.getLoginUser() == null) {
      return JsonMessage.getFailMessage("请先登录账号！");
    }
    if (IntegerConverter.isEmpty(model.getAdmin().getAid())) {
      return JsonMessage.getFailMessage("请选择要删除的账号！");
    }
    adminService.delete(model.getAdmin());
    JsonMessage message = JsonMessage.getSuccessMessage("删除成功！");
    message.putData("delete", model.getAdmin());
    return message;
  }

  @RequestMapping("/add")
  public JsonMessage add(AdminModel model) throws Exception {
    String password = model.getAdmin().getPassword();
    if (StringConverter.isEmpty(model.getAdmin().getUsername())) {
      return JsonMessage.getFailMessage("请填写用户名！");
    }
    if (StringConverter.isEmpty(password) || password.length() < 6 || password.length() > 16) {
      return JsonMessage.getFailMessage("密码不能为空且大于6位数小于16位数！");
    }
    int n = adminService.add(model);
    if (n == -1) {
      return JsonMessage.getFailMessage("用户名已存在");
    }
    JsonMessage message = JsonMessage.getSuccessMessage("注册成功！");
    message.putData("add", model.getAdmin());
    return message;
  }

}
