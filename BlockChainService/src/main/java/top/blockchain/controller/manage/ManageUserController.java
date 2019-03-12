package top.blockchain.controller.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.blockchain.converter.StringConverter;
import top.blockchain.entity.TbAdmin;
import top.blockchain.model.admin.AdminModel;
import top.blockchain.model.admin.ManageUserModel;
import top.blockchain.service.AdminService;
import top.blockchain.service.UserService;
import top.blockchain.util.JsonMessage;

@RestController
@RequestMapping("/manage/user")
public class ManageUserController {

  @Autowired
  private UserService userService;
  
  @Autowired
  private AdminService adminService;

  @RequestMapping("/queryAll")
  // /manage/user/queryAll
  public JsonMessage queryAll(ManageUserModel model) throws Exception {
    return userService.queryAll(model);
  }

  @RequestMapping("/delete")
  // /manage/user/delete {"tbUser.uid":1}
  public JsonMessage delete(ManageUserModel model) throws Exception {
    userService.delete(model.getTbUser());
    JsonMessage message = JsonMessage.getSuccessMessage("冻结账号成功！");
    return message;
  }

  @RequestMapping("/undelete")
  // / manage/user/undelete {"tbUser.uid":1}
  public JsonMessage undelete(ManageUserModel model) throws Exception {
    userService.undelete(model.getTbUser());
    JsonMessage message = JsonMessage.getSuccessMessage("激活账号成功！");
    return message;
  }

  @RequestMapping("/updatePassword")
  public JsonMessage updatePassword(AdminModel model) throws Exception {
    TbAdmin admin = model.getAdmin();
    if (StringConverter.isEmpty(admin.getPassword())) {
      return JsonMessage.getFailMessage("请填写修改的密码");
    }
    return adminService.updatePassword(model);
  }

}
