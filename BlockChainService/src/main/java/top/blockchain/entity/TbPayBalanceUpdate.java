package top.blockchain.entity;

import top.blockchain.util.EntityBase;

public class TbPayBalanceUpdate extends EntityBase {
  private static final long serialVersionUID = -4386762130630343785L;
  private java.lang.Integer bruid;
  private java.math.BigDecimal balance;
  private java.lang.Integer uid;
  private java.util.Date created;
  private TbUser user;

  public TbPayBalanceUpdate() {
  }

  public TbPayBalanceUpdate(java.lang.Integer bruid, java.math.BigDecimal balance, java.lang.Integer uid, java.util.Date created) {
    this.bruid = bruid;
    this.balance = balance;
    this.uid = uid;
    this.created = created;
  }

  public TbUser getUser() {
    return user;
  }

  public void setUser(TbUser user) {
    this.user = user;
  }

  public void setBruid(java.lang.Integer bruid) {
    this.bruid = bruid;
  }

  public java.lang.Integer getBruid() {
    return bruid;
  }

  public void setBalance(java.math.BigDecimal balance) {
    this.balance = balance;
  }

  public java.math.BigDecimal getBalance() {
    return balance;
  }

  public void setUid(java.lang.Integer uid) {
    this.uid = uid;
  }

  public java.lang.Integer getUid() {
    return uid;
  }

  public void setCreated(java.util.Date created) {
    this.created = created;
  }

  public java.util.Date getCreated() {
    return created;
  }

}
