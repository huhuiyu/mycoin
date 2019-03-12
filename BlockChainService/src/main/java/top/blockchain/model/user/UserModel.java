package top.blockchain.model.user;

import top.blockchain.entity.TbUser;
import top.blockchain.model.base.UserBaseModel;

public class UserModel extends UserBaseModel {
  private static final long serialVersionUID = -362355078724409663L;

  private TbUser user = new TbUser();
  private String phoneCode = ""; // 电话校验码

  public UserModel() {
  }

  public TbUser getUser() {
    return user;
  }

  public void setUser(TbUser user) {
    this.user = user;
  }

  public String getPhoneCode() {
    return phoneCode;
  }

  public void setPhoneCode(String phoneCode) {
    this.phoneCode = phoneCode;
  }
}
