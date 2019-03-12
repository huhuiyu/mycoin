package top.blockchain.entity;

import top.blockchain.util.EntityBase;

public class PhoneCheck extends EntityBase {

  private static final long serialVersionUID = -6133692949363072193L;
  private String appCode;
  private String url;

  public PhoneCheck() {
  }

  public String getAppCode() {
    return appCode;
  }

  public void setAppCode(String appCode) {
    this.appCode = appCode;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

}
