package top.blockchain.entity;

import java.util.Date;

import top.blockchain.util.EntityBase;

public class TbUser extends EntityBase {
  private static final long serialVersionUID = -2450244933678263335L;
  private int uid;
  private String phone;
  private String tokenId;
  private String password;
  private String paypwd;

  private String blockId;
  private int linkman;
  private TbUser linkuser;
  private String isEnable;
  private Date regDate;
  private TbUserInfo userInfo;

  public TbUser() {
  }

  public TbUser getLinkuser() {
    return linkuser;
  }

  public void setLinkuser(TbUser linkuser) {
    this.linkuser = linkuser;
  }

  public int getUid() {
    return uid;
  }

  public void setUid(int uid) {
    this.uid = uid;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getTokenId() {
    return tokenId;
  }

  public void setTokenId(String tokenId) {
    this.tokenId = tokenId;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPaypwd() {
    return paypwd;
  }

  public void setPaypwd(String paypwd) {
    this.paypwd = paypwd;
  }

  public String getBlockId() {
    return blockId;
  }

  public void setBlockId(String qklid) {
    this.blockId = qklid;
  }

  public int getLinkman() {
    return linkman;
  }

  public void setLinkman(int linkman) {
    this.linkman = linkman;
  }

  public String getIsEnable() {
    return isEnable;
  }

  public void setIsEnable(String isEnable) {
    this.isEnable = isEnable;
  }

  public Date getRegDate() {
    return regDate;
  }

  public void setRegDate(Date regDate) {
    this.regDate = regDate;
  }

  public TbUserInfo getUserInfo() {
    return userInfo;
  }

  public void setUserInfo(TbUserInfo userInfo) {
    this.userInfo = userInfo;
  }

}
