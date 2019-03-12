package top.blockchain.entity;

import top.blockchain.util.EntityBase;

public class TbAdmin extends EntityBase {
  private static final long serialVersionUID = 259219941062821378L;

  private int aid;

  private String username;
  private String password;
  private String isEnable;

  public TbAdmin() {
  }

  public int getAid() {
    return aid;
  }

  public void setAid(int aid) {
    this.aid = aid;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getIsEnable() {
    return isEnable;
  }

  public void setIsEnable(String isEnable) {
    this.isEnable = isEnable;
  }

}
