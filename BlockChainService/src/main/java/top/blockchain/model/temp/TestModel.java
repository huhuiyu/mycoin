package top.blockchain.model.temp;

import top.blockchain.entity.SmsBean;
import top.blockchain.model.base.BaseModel;

public class TestModel extends BaseModel {
  private static final long serialVersionUID = 7071604480702191509L;

  private SmsBean sms = new SmsBean();

  public TestModel() {
  }

  public SmsBean getSms() {
    return sms;
  }

  public void setSms(SmsBean sms) {
    this.sms = sms;
  }

}
