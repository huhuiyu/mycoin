package top.blockchain.entity;

import java.util.Date;

import top.blockchain.util.EntityBase;

public class TbToken extends EntityBase {
  private static final long serialVersionUID = -901038073760933920L;

  private String token;
  private Date lastupdate;

  public TbToken() {
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Date getLastupdate() {
    return lastupdate;
  }

  public void setLastupdate(Date lastupdate) {
    this.lastupdate = lastupdate;
  }

}
