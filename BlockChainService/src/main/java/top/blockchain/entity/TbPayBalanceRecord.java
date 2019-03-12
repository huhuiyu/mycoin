package top.blockchain.entity;

import top.blockchain.util.EntityBase;

public class TbPayBalanceRecord extends EntityBase {
  private static final long serialVersionUID = 7570119884487789162L;
  private java.lang.Integer pbrid;
  private java.math.BigDecimal balance;
  private java.lang.String target;
  private java.lang.Integer uid;
  private java.lang.Integer aid;
  private java.lang.String info;
  private java.util.Date created;

  public TbPayBalanceRecord() {
  }

  public TbPayBalanceRecord(java.lang.Integer pbrid, java.math.BigDecimal balance, java.lang.String target, java.lang.Integer uid, java.lang.Integer aid, java.lang.String info,
      java.util.Date created) {
    this.pbrid = pbrid;
    this.balance = balance;
    this.target = target;
    this.uid = uid;
    this.aid = aid;
    this.info = info;
    this.created = created;
  }

  public void setPbrid(java.lang.Integer pbrid) {
    this.pbrid = pbrid;
  }

  public java.lang.Integer getPbrid() {
    return pbrid;
  }

  public void setBalance(java.math.BigDecimal balance) {
    this.balance = balance;
  }

  public java.math.BigDecimal getBalance() {
    return balance;
  }

  public void setTarget(java.lang.String target) {
    this.target = target;
  }

  public java.lang.String getTarget() {
    return target;
  }

  public void setUid(java.lang.Integer uid) {
    this.uid = uid;
  }

  public java.lang.Integer getUid() {
    return uid;
  }

  public void setAid(java.lang.Integer aid) {
    this.aid = aid;
  }

  public java.lang.Integer getAid() {
    return aid;
  }

  public void setInfo(java.lang.String info) {
    this.info = info;
  }

  public java.lang.String getInfo() {
    return info;
  }

  public void setCreated(java.util.Date created) {
    this.created = created;
  }

  public java.util.Date getCreated() {
    return created;
  }

}
