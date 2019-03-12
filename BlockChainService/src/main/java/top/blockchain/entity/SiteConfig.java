package top.blockchain.entity;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import top.blockchain.util.EntityBase;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SiteConfig extends EntityBase {
  private static final long serialVersionUID = -2201840567923610513L;
  // {"poundage":0.2,"payback":0.0005,"freezeHour":9,"vip":200,"userReward":1,"parentReward":0.1,"dayPrice":16,"dayMaxPrice":18,"minBalance":10,"maxBalance":10000}

  private BigDecimal poundage; // 交易手续费
  private BigDecimal payback; // 待交易返回费用
  private int freezeHour; // 交易冻结时间
  private BigDecimal vip; // 成为vip需要扣除的费用
  private BigDecimal userReward; // 用户购买奖励
  private BigDecimal parentReward; // 用户购买上级待返奖励
  private BigDecimal parentPayReward; // 用户购买上级奖励
  private BigDecimal parentReward2; // 用户购买二级奖励
  private BigDecimal parentReward3; // 用户购买三级奖励
  private BigDecimal dayPrice; // 最低指导价
  private BigDecimal dayMaxPrice;// 最高指导价
  private BigDecimal minBalance; // 最低交易量
  private BigDecimal maxBalance; // 最低交易量
  private String shareUrl; // 网站分享链接地址
  private String apkUrl; // apk下载链接地址
  private String versionUrl; // 版本号链接地址
  private String closed; // 网站是否关闭
  private BigDecimal exchange; // 复投比率
  private BigDecimal exchangeReward; // 复投上级待返奖励
  private BigDecimal exchangeReward2; // 复投2级待返奖励
  private BigDecimal exchangeReward3; // 复投3级待返奖励

  public SiteConfig() {
  }

  public String getApkUrl() {
    return apkUrl;
  }

  public void setApkUrl(String apkUrl) {
    this.apkUrl = apkUrl;
  }

  public String getVersionUrl() {
    return versionUrl;
  }

  public void setVersionUrl(String versionUrl) {
    this.versionUrl = versionUrl;
  }

  public BigDecimal getPoundage() {
    return poundage;
  }

  public void setPoundage(BigDecimal poundage) {
    this.poundage = poundage;
  }

  public BigDecimal getPayback() {
    return payback;
  }

  public void setPayback(BigDecimal payback) {
    this.payback = payback;
  }

  public int getFreezeHour() {
    return freezeHour;
  }

  public void setFreezeHour(int freezeHour) {
    this.freezeHour = freezeHour;
  }

  public BigDecimal getVip() {
    return vip;
  }

  public void setVip(BigDecimal vip) {
    this.vip = vip;
  }

  public BigDecimal getUserReward() {
    return userReward;
  }

  public void setUserReward(BigDecimal userReward) {
    this.userReward = userReward;
  }

  public BigDecimal getParentReward() {
    return parentReward;
  }

  public void setParentReward(BigDecimal parentReward) {
    this.parentReward = parentReward;
  }

  public BigDecimal getDayPrice() {
    return dayPrice;
  }

  public void setDayPrice(BigDecimal dayPrice) {
    this.dayPrice = dayPrice;
  }

  public BigDecimal getDayMaxPrice() {
    return dayMaxPrice;
  }

  public void setDayMaxPrice(BigDecimal dayMaxPrice) {
    this.dayMaxPrice = dayMaxPrice;
  }

  public BigDecimal getMinBalance() {
    return minBalance;
  }

  public void setMinBalance(BigDecimal minBalance) {
    this.minBalance = minBalance;
  }

  public BigDecimal getMaxBalance() {
    return maxBalance;
  }

  public void setMaxBalance(BigDecimal maxBalance) {
    this.maxBalance = maxBalance;
  }

  public String getShareUrl() {
    return shareUrl;
  }

  public void setShareUrl(String shareUrl) {
    this.shareUrl = shareUrl;
  }

  public String getClosed() {
    return closed;
  }

  public void setClosed(String closed) {
    this.closed = closed;
  }

  public BigDecimal getExchange() {
    return exchange;
  }

  public void setExchange(BigDecimal exchange) {
    this.exchange = exchange;
  }

  public BigDecimal getExchangeReward() {
    return exchangeReward;
  }

  public void setExchangeReward(BigDecimal exchangeReward) {
    this.exchangeReward = exchangeReward;
  }

  public BigDecimal getParentPayReward() {
    return parentPayReward;
  }

  public void setParentPayReward(BigDecimal parentPayReward) {
    this.parentPayReward = parentPayReward;
  }

  public BigDecimal getParentReward2() {
    return parentReward2;
  }

  public void setParentReward2(BigDecimal parentReward2) {
    this.parentReward2 = parentReward2;
  }

  public BigDecimal getParentReward3() {
    return parentReward3;
  }

  public void setParentReward3(BigDecimal parentReward3) {
    this.parentReward3 = parentReward3;
  }

  public BigDecimal getExchangeReward2() {
    return exchangeReward2;
  }

  public void setExchangeReward2(BigDecimal exchangeReward2) {
    this.exchangeReward2 = exchangeReward2;
  }

  public BigDecimal getExchangeReward3() {
    return exchangeReward3;
  }

  public void setExchangeReward3(BigDecimal exchangeReward3) {
    this.exchangeReward3 = exchangeReward3;
  }

}
