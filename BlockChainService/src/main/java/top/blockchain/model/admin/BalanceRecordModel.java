package top.blockchain.model.admin;

import top.blockchain.entity.TbBalanceRecord;
import top.blockchain.model.base.AdminBaseModel;

public class BalanceRecordModel extends AdminBaseModel {

  private static final long serialVersionUID = 3494653757554360581L;

  private TbBalanceRecord record = new TbBalanceRecord();

  public BalanceRecordModel() {
  }

  public TbBalanceRecord getRecord() {
    return record;
  }

  public void setRecord(TbBalanceRecord record) {
    this.record = record;
  }

}
