package top.blockchain.entity;

import java.util.Date;

import top.blockchain.util.EntityBase;

public class TbTokenInfo extends EntityBase {

  private static final long serialVersionUID = 3509600901764967804L;
  private String token;
  private String infokey;
  private String info;
  private Date lastupdate;

  public TbTokenInfo() {
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getInfokey() {
    return infokey;
  }

  public void setInfokey(String infokey) {
    this.infokey = infokey;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  public Date getLastupdate() {
    return lastupdate;
  }

  public void setLastupdate(Date lastupdate) {
    this.lastupdate = lastupdate;
  }

  public static TbTokenInfo getTokenInfo(String token) {
    TbTokenInfo info = new TbTokenInfo();
    info.setToken(token);
    return info;
  }

  public static TbTokenInfo getTokenInfo(String token, String key) {
    TbTokenInfo info = new TbTokenInfo();
    info.setToken(token);
    info.setInfokey(key);
    return info;
  }

}
