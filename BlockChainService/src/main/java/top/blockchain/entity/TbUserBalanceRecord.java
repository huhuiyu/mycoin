package top.blockchain.entity;

import top.blockchain.util.EntityBase;

public class TbUserBalanceRecord extends EntityBase {
  private static final long serialVersionUID = 5146903354047207319L;
  private java.lang.Integer burid;
  private java.lang.Integer uid;
  private TbUser user;
  private java.lang.Integer bdir;
  private java.lang.Integer btype;
  private java.math.BigDecimal balance;
  private java.lang.String info;
  private java.util.Date created;

  public TbUserBalanceRecord() {
  }

  public TbUserBalanceRecord(java.lang.Integer burid, java.lang.Integer uid, java.lang.Integer bdir, java.lang.Integer btype, java.math.BigDecimal balance, java.lang.String info,
      java.util.Date created) {
    this.burid = burid;
    this.uid = uid;
    this.bdir = bdir;
    this.btype = btype;
    this.balance = balance;
    this.info = info;
    this.created = created;
  }

  public TbUser getUser() {
    return user;
  }

  public void setUser(TbUser user) {
    this.user = user;
  }

  public void setBurid(java.lang.Integer burid) {
    this.burid = burid;
  }

  public java.lang.Integer getBurid() {
    return burid;
  }

  public void setUid(java.lang.Integer uid) {
    this.uid = uid;
  }

  public java.lang.Integer getUid() {
    return uid;
  }

  public void setBdir(java.lang.Integer bdir) {
    this.bdir = bdir;
  }

  public java.lang.Integer getBdir() {
    return bdir;
  }

  public void setBtype(java.lang.Integer btype) {
    this.btype = btype;
  }

  public java.lang.Integer getBtype() {
    return btype;
  }

  public void setBalance(java.math.BigDecimal balance) {
    this.balance = balance;
  }

  public java.math.BigDecimal getBalance() {
    return balance;
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
