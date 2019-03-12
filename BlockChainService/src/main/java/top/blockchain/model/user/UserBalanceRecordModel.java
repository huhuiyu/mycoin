package top.blockchain.model.user;

import top.blockchain.entity.TbBalanceRecord;
import top.blockchain.model.base.UserBaseModel;

public class UserBalanceRecordModel extends UserBaseModel {

  private static final long serialVersionUID = 5858270496934302140L;

  private TbBalanceRecord record = new TbBalanceRecord();
  private String paypwd;

  public TbBalanceRecord getRecord() {
    return record;
  }

  public void setRecord(TbBalanceRecord record) {
    this.record = record;
  }

  public String getPaypwd() {
    return paypwd;
  }

  public void setPaypwd(String paypwd) {
    this.paypwd = paypwd;
  }
  
  

}
