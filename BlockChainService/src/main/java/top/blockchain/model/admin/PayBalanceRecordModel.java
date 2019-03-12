package top.blockchain.model.admin;

import top.blockchain.entity.TbPayBalanceRecord;
import top.blockchain.model.base.AdminBaseModel;

public class PayBalanceRecordModel extends AdminBaseModel {

  private static final long serialVersionUID = 6770334165123054324L;

  private TbPayBalanceRecord record = new TbPayBalanceRecord();

  public PayBalanceRecordModel() {
  }

  public TbPayBalanceRecord getRecord() {
    return record;
  }

  public void setRecord(TbPayBalanceRecord record) {
    this.record = record;
  }

}
