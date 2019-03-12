package top.blockchain.model.admin;

import top.blockchain.entity.TbUser;
import top.blockchain.model.base.AdminBaseModel;

public class ManageUserModel extends AdminBaseModel {

  private static final long serialVersionUID = 5157682394051543352L;
  
  private TbUser tbUser=new TbUser();
  
  public ManageUserModel() {
  
  }

  public TbUser getTbUser() {
    return tbUser;
  }

  public void setTbUser(TbUser tbUser) {
    this.tbUser = tbUser;
  }

}
