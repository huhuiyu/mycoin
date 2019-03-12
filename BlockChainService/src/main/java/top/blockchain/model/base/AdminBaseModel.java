package top.blockchain.model.base;

import top.blockchain.entity.TbAdmin;

public class AdminBaseModel extends BaseModel {
  private static final long serialVersionUID = 6053979721496642610L;

  private TbAdmin loginUser;

  public AdminBaseModel() {
  }

  public TbAdmin getLoginUser() {
    return loginUser;
  }

  public void setLoginUser(TbAdmin loginUser) {
    this.loginUser = loginUser;
  }

}
