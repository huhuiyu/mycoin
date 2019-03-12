package top.blockchain.model.user;

import top.blockchain.entity.TbUserPhoneCheck;
import top.blockchain.model.base.UserBaseModel;

public class UserUtilModel extends UserBaseModel {

  private static final long serialVersionUID = -8057046981868170422L;

  private TbUserPhoneCheck phoneCheck = new TbUserPhoneCheck();

  public TbUserPhoneCheck getPhoneCheck() {
    return phoneCheck;
  }

  public void setPhoneCheck(TbUserPhoneCheck phoneCheck) {
    this.phoneCheck = phoneCheck;
  }

  public UserUtilModel() {
  }

}
