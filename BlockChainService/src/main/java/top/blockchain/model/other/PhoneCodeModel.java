package top.blockchain.model.other;

import top.blockchain.entity.PhoneCode;
import top.blockchain.model.base.BaseModel;

public class PhoneCodeModel extends BaseModel {

  private static final long serialVersionUID = 5015684891310572991L;
  private PhoneCode phoneCode = new PhoneCode();

  public PhoneCodeModel() {
  }

  public PhoneCode getPhoneCode() {
    return phoneCode;
  }

  public void setPhoneCode(PhoneCode phoneCode) {
    this.phoneCode = phoneCode;
  }
}
