package top.blockchain.entity;

import java.math.BigDecimal;
import java.util.Date;

import top.blockchain.util.EntityBase;

public class TbUserInfo extends EntityBase {
  private static final long serialVersionUID = 8191650219277848082L;
  private int uid;
  private String wechat;
  private String alipay;
  private BigDecimal balance;
  private BigDecimal freeze;
  private BigDecimal paybalance;
  private String nickname;
  private String idCard;
  private String vip;
  private Date lastupdate;

  public TbUserInfo() {
  }

  public int getUid() {
    return uid;
  }

  public void setUid(int uid) {
    this.uid = uid;
  }

  public String getWechat() {
    return wechat;
  }

  public void setWechat(String wechat) {
    this.wechat = wechat;
  }

  public String getAlipay() {
    return alipay;
  }

  public void setAlipay(String alipay) {
    this.alipay = alipay;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public BigDecimal getFreeze() {
    return freeze;
  }

  public void setFreeze(BigDecimal freeze) {
    this.freeze = freeze;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public Date getLastupdate() {
    return lastupdate;
  }

  public void setLastupdate(Date lastupdate) {
    this.lastupdate = lastupdate;
  }

  public BigDecimal getPaybalance() {
    return paybalance;
  }

  public void setPaybalance(BigDecimal paybalance) {
    this.paybalance = paybalance;
  }

  public String getIdCard() {
    return idCard;
  }

  public void setIdCard(String idCard) {
    this.idCard = idCard;
  }

  public String getVip() {
    return vip;
  }

  public void setVip(String vip) {
    this.vip = vip;
  }

}
