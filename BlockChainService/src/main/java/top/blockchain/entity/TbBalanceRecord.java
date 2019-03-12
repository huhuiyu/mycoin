package top.blockchain.entity;

import top.blockchain.util.EntityBase;

public class TbBalanceRecord extends EntityBase {
  private static final long serialVersionUID = 2360741092539228201L;
  private java.lang.Integer brid;
  private java.math.BigDecimal balance;
  private java.math.BigDecimal price;
  private java.lang.Integer auid;
  private TbUser auser;
  private java.lang.Integer buid;
  private TbUser buser;
  private String bstatus;
  private String brtype;
  private java.lang.String info;
  private java.util.Date created;

  public TbBalanceRecord() {
  }

  public TbBalanceRecord(java.lang.Integer brid, java.math.BigDecimal balance, java.math.BigDecimal price, java.lang.Integer auid, java.lang.Integer buid, String bstatus, String brtype,
      java.lang.String info, java.util.Date created) {
    this.brid = brid;
    this.balance = balance;
    this.price = price;
    this.auid = auid;
    this.buid = buid;
    this.bstatus = bstatus;
    this.brtype = brtype;
    this.info = info;
    this.created = created;
  }

  public TbUser getAuser() {
    return auser;
  }

  public void setAuser(TbUser auser) {
    this.auser = auser;
  }

  public TbUser getBuser() {
    return buser;
  }

  public void setBuser(TbUser buser) {
    this.buser = buser;
  }

  public String getBstatus() {
    return bstatus;
  }

  public void setBstatus(String bstatus) {
    this.bstatus = bstatus;
  }

  public String getBrtype() {
    return brtype;
  }

  public void setBrtype(String brtype) {
    this.brtype = brtype;
  }

  public void setBrid(java.lang.Integer brid) {
    this.brid = brid;
  }

  public java.lang.Integer getBrid() {
    return brid;
  }

  public void setBalance(java.math.BigDecimal balance) {
    this.balance = balance;
  }

  public java.math.BigDecimal getBalance() {
    return balance;
  }

  public java.math.BigDecimal getPrice() {
    return price;
  }

  public void setPrice(java.math.BigDecimal price) {
    this.price = price;
  }

  public void setAuid(java.lang.Integer auid) {
    this.auid = auid;
  }

  public java.lang.Integer getAuid() {
    return auid;
  }

  public void setBuid(java.lang.Integer buid) {
    this.buid = buid;
  }

  public java.lang.Integer getBuid() {
    return buid;
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
