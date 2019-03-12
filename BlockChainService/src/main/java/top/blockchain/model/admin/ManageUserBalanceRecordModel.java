package top.blockchain.model.admin;

import top.blockchain.entity.TbUserBalanceRecord;
import top.blockchain.model.base.AdminBaseModel;

public class ManageUserBalanceRecordModel extends AdminBaseModel {
  private static final long serialVersionUID = 8389752471780161574L;
  
  private TbUserBalanceRecord record =new TbUserBalanceRecord();
  
  public ManageUserBalanceRecordModel() {
  }

  public TbUserBalanceRecord getRecord() {
    return record;
  }

  public void setRecord(TbUserBalanceRecord record) {
    this.record = record;
  }

}
