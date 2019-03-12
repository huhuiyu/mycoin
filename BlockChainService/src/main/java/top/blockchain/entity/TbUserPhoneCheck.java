package top.blockchain.entity;

import java.util.Date;

import top.blockchain.util.EntityBase;

public class TbUserPhoneCheck extends EntityBase {

  // {"status":"01","msg":"验证通过","idCard":"430703199907242015","mobile":"15973673696","mobileType":"移动","name":"杨尚易","sex":"男","area":"湖南省常德市鼎城区","province":"湖南省","city":"常德市","prefecture":"鼎城区","birthday":"1999-07-24","addrCode":"430703","lastCode":"5"}

  private static final long serialVersionUID = -5317472429026888959L;
  private int uid;
  private String status;
  private String msg;
  private String idCard;
  private String mobile;
  private String mobileType;

  private String name;
  private String sex;
  private String area;
  private String province;
  private String city;

  private String prefecture;
  private String birthday;
  private String addrCode;
  private String lastCode;
  private Date created;

  private int lastCheck; // 虚拟字段，最后查询时间

  public int getUid() {
    return uid;
  }

  public void setUid(int uid) {
    this.uid = uid;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public TbUserPhoneCheck() {
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public String getIdCard() {
    return idCard;
  }

  public void setIdCard(String idCard) {
    this.idCard = idCard;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getMobileType() {
    return mobileType;
  }

  public void setMobileType(String mobileType) {
    this.mobileType = mobileType;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getPrefecture() {
    return prefecture;
  }

  public void setPrefecture(String prefecture) {
    this.prefecture = prefecture;
  }

  public String getBirthday() {
    return birthday;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }

  public String getAddrCode() {
    return addrCode;
  }

  public void setAddrCode(String addrCode) {
    this.addrCode = addrCode;
  }

  public String getLastCode() {
    return lastCode;
  }

  public void setLastCode(String lastCode) {
    this.lastCode = lastCode;
  }

  public int getLastCheck() {
    return lastCheck;
  }

  public void setLastCheck(int lastCheck) {
    this.lastCheck = lastCheck;
  }

}
