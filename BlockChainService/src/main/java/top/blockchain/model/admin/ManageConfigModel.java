package top.blockchain.model.admin;

import top.blockchain.entity.PhoneCheck;
import top.blockchain.entity.SiteConfig;
import top.blockchain.entity.SmsBean;
import top.blockchain.model.base.AdminBaseModel;

public class ManageConfigModel extends AdminBaseModel {
  private static final long serialVersionUID = 4994338829927131124L;
  private PhoneCheck phoneCheck;
  private SiteConfig siteConfig;
  private SmsBean smsBean;

  public ManageConfigModel() {
  }

  public PhoneCheck getPhoneCheck() {
    return phoneCheck;
  }

  public void setPhoneCheck(PhoneCheck phoneCheck) {
    this.phoneCheck = phoneCheck;
  }

  public SiteConfig getSiteConfig() {
    return siteConfig;
  }

  public void setSiteConfig(SiteConfig siteConfig) {
    this.siteConfig = siteConfig;
  }

  public SmsBean getSmsBean() {
    return smsBean;
  }

  public void setSmsBean(SmsBean smsBean) {
    this.smsBean = smsBean;
  }
}
