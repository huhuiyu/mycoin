package top.blockchain.entity;

import top.blockchain.util.EntityBase;

public class UcPassSmsResult extends EntityBase {

  private static final long serialVersionUID = 8608568182023335290L;

  private String code = "";
  private String msg = "";
  private String count = "";
  private String create_date = "";
  private String smsid = "";
  private String mobile = "";
  private String uid = "";
  public String getCode() {
    return code;
  }
  public void setCode(String code) {
    this.code = code;
  }
  public String getMsg() {
    return msg;
  }
  public void setMsg(String msg) {
    this.msg = msg;
  }
  public String getCount() {
    return count;
  }
  public void setCount(String count) {
    this.count = count;
  }
  public String getCreate_date() {
    return create_date;
  }
  public void setCreate_date(String create_date) {
    this.create_date = create_date;
  }
  public String getSmsid() {
    return smsid;
  }
  public void setSmsid(String smsid) {
    this.smsid = smsid;
  }
  public String getMobile() {
    return mobile;
  }
  public void setMobile(String mobile) {
    this.mobile = mobile;
  }
  public String getUid() {
    return uid;
  }
  public void setUid(String uid) {
    this.uid = uid;
  }
}
