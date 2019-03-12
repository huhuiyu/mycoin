package top.blockchain.model.admin;

import top.blockchain.entity.TbAdmin;
import top.blockchain.model.base.AdminBaseModel;

public class AdminModel extends AdminBaseModel {
  private static final long serialVersionUID = 5516341312230655892L;
  private TbAdmin admin = new TbAdmin();
  private String oldPassword;

  public String getOldPassword() {
    return oldPassword;
  }

  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }

  public AdminModel() {
  }

  public TbAdmin getAdmin() {
    return admin;
  }

  public void setAdmin(TbAdmin admin) {
    this.admin = admin;
  }
}
