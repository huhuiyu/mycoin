package top.blockchain.entity;

import java.util.Date;

import top.blockchain.util.EntityBase;

public class TbConfig extends EntityBase {

  private static final long serialVersionUID = 842614904564218061L;

  private int configId;
  private String configKey;
  private String configVal;
  private Date lastupdate;

  public TbConfig() {
  }

  public int getConfigId() {
    return configId;
  }

  public void setConfigId(int configId) {
    this.configId = configId;
  }

  public String getConfigKey() {
    return configKey;
  }

  public void setConfigKey(String configKey) {
    this.configKey = configKey;
  }

  public String getConfigVal() {
    return configVal;
  }

  public void setConfigVal(String configVal) {
    this.configVal = configVal;
  }

  public Date getLastupdate() {
    return lastupdate;
  }

  public void setLastupdate(Date lastupdate) {
    this.lastupdate = lastupdate;
  }

}
