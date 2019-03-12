package top.blockchain.entity;

import top.blockchain.util.EntityBase;

public class PhoneTemplate extends EntityBase{

  private static final long serialVersionUID = 4786155038043378764L;

  private String validateCode;
  
  public PhoneTemplate() {
  }

  public String getValidateCode() {
    return validateCode;
  }

  public void setValidateCode(String validateCode) {
    this.validateCode = validateCode;
  }

}
