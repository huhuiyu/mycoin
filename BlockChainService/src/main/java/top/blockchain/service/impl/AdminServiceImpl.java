package top.blockchain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import top.blockchain.converter.StringConverter;
import top.blockchain.dao.TbAdminDAO;
import top.blockchain.entity.TbAdmin;
import top.blockchain.model.admin.AdminModel;
import top.blockchain.service.AdminService;
import top.blockchain.service.TokenInfoService;
import top.blockchain.util.JsonMessage;

@Service
@Transactional(rollbackFor = Exception.class)
public class AdminServiceImpl implements AdminService {
  @Autowired
  private TbAdminDAO tbAdminDAO;
  @Autowired
  private TokenInfoService tokenInfoService;

  @Override
  public List<TbAdmin> query() throws Exception {

    return tbAdminDAO.query();
  }

  @Override
  public int delete(TbAdmin admin) throws Exception {
    return tbAdminDAO.delete(admin);
  }

  @Override
  public int modify(TbAdmin admin) throws Exception {
    return tbAdminDAO.modify(admin);
  }

  @Override
  public JsonMessage login(AdminModel model) throws Exception {
    TbAdmin admin = tbAdminDAO.login(model.getAdmin());
    if (admin == null) {
      return JsonMessage.getFailMessage("用户名或密码错误，登录失败！");
    }
    int r = tokenInfoService.addAdminUserInfo(admin.getAid(), model.getServertoken());
    if (r >= 1) {
      return JsonMessage.getSuccessMessage("登录成功！");
    }
    return JsonMessage.getSuccessMessage("登录失败，登陆状态保存异常！");
  }

  @Override
  public int add(AdminModel model) throws Exception {
    TbAdmin admin = model.getAdmin();
    int username = tbAdminDAO.queryFound(admin);
    if (username > 0) {
      return -1;
    }
    tbAdminDAO.add(admin);
    return 1;
  }

  @Override
  public JsonMessage updatePassword(AdminModel model) throws Exception {
    model.getAdmin().setAid(model.getLoginUser().getAid());
    if (StringConverter.isEmpty(model.getOldPassword())) {
      return JsonMessage.getFailMessage("旧密码不能为空!");
    }
    TbAdmin admin = tbAdminDAO.queryByKey(model.getLoginUser());
    if (!admin.getPassword().equals(model.getOldPassword())) {
      return JsonMessage.getFailMessage("旧密码不一致");
    }
    int r = tbAdminDAO.updatePassword(model.getAdmin());
    if (r == 1) {
      return JsonMessage.getSuccessMessage("修改成功!");
    }
    return JsonMessage.getFailMessage("修改失败!");
  }
}
