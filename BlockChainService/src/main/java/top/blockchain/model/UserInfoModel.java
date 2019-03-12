package top.blockchain.model;

import top.blockchain.entity.TbUserInfo;
import top.blockchain.model.base.UserBaseModel;

public class UserInfoModel extends UserBaseModel {
  private static final long serialVersionUID = 7273531283746965051L;
  private TbUserInfo userInfo = new TbUserInfo();
  private String paypwd;

  public UserInfoModel() {
  }

  public TbUserInfo getUserInfo() {
    return userInfo;
  }

  public void setUserInfo(TbUserInfo userInfo) {
    this.userInfo = userInfo;
  }

  public String getPaypwd() {
    return paypwd;
  }

  public void setPaypwd(String paypwd) {
    this.paypwd = paypwd;
  }
  

}
