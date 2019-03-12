package top.blockchain.model.base;

import top.blockchain.entity.TbUser;

public class UserBaseModel extends BaseModel {
  private static final long serialVersionUID = -9153590801077595925L;
  private TbUser loginUser;

  public UserBaseModel() {
  }

  public TbUser getLoginUser() {
    return loginUser;
  }

  public void setLoginUser(TbUser user) {
    this.loginUser = user;
  }

}
