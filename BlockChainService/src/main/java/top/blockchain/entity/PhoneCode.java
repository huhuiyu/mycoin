package top.blockchain.entity;

import top.blockchain.util.EntityBase;

public class PhoneCode extends EntityBase {

  private static final long serialVersionUID = 1132405489953192294L;
  private String phone;
  private String code;

  public PhoneCode() {
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

}
