package top.blockchain.entity;

import top.blockchain.util.EntityBase;

public class SmsBean extends EntityBase {

  private static final long serialVersionUID = 1831297059639841846L;
  private String product;// 短信API产品名称（短信产品名固定，无需修改）
  private String domain;// 短信API产品域名（接口地址固定，无需修改）
  private String region1;// 区域1
  private String region2;// 区域2
  private String accesskeyid;
  private String accesskeysecret;
  private String sign;
  private PhoneTemplate template;

  public SmsBean() {
  }

  public SmsBean(String product, String domain, String region1, String region2, String accesskeyid, String accesskeysecret, String sign) {
    this.product = product;
    this.domain = domain;
    this.region1 = region1;
    this.region2 = region2;
    this.accesskeyid = accesskeyid;
    this.accesskeysecret = accesskeysecret;
    this.sign = sign;
  }

  public String getProduct() {
    return product;
  }

  public void setProduct(String product) {
    this.product = product;
  }

  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public String getRegion1() {
    return region1;
  }

  public void setRegion1(String region1) {
    this.region1 = region1;
  }

  public String getRegion2() {
    return region2;
  }

  public void setRegion2(String region2) {
    this.region2 = region2;
  }

  public String getAccesskeyid() {
    return accesskeyid;
  }

  public void setAccesskeyid(String accesskeyid) {
    this.accesskeyid = accesskeyid;
  }

  public String getAccesskeysecret() {
    return accesskeysecret;
  }

  public void setAccesskeysecret(String accesskeysecret) {
    this.accesskeysecret = accesskeysecret;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

  public PhoneTemplate getTemplate() {
    return template;
  }

  public void setTemplate(PhoneTemplate template) {
    this.template = template;
  }

  public static SmsBean getTestSmsBean() {
    SmsBean smsBean = new SmsBean("Dysmsapi", "dysmsapi.aliyuncs.com", "cn-hangzhou", "cn-hangzhou", "LTAI01oN4k6mir3j", "H4BiKPZwUafc7ReOTXf94w4UmN6k2J", "胡辉煜");
    return smsBean;
  }
}
