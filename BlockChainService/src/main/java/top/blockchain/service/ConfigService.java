package top.blockchain.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import top.blockchain.entity.PhoneCheck;
import top.blockchain.entity.SiteConfig;
import top.blockchain.entity.SmsBean;
import top.blockchain.model.admin.ManageConfigModel;
import top.blockchain.util.JsonMessage;

public interface ConfigService {
  public static final String SMS_BEAN = "sms_bean_info";
  public static final String PHONECHECK_BEAN_INFO = "phonecheck_bean_info";
  public static final String SITE_CONFIG = "site_config";

  public static final SimpleDateFormat UPDATE_PAY_BALANCE_FORMAT = new SimpleDateFormat("yyyyMMdd");

  int addSmsConfig(SmsBean config) throws Exception;

  SmsBean querySms() throws Exception;

  int addPhoneCheckConfig(PhoneCheck config) throws Exception;

  PhoneCheck queryPhoneCheck() throws Exception;

  SiteConfig querySiteConfig() throws Exception;

  Date queryNow() throws Exception;

  /**
   * 检测是否在交易冻结时间内，冻结时间内返回true，否则返回false
   * 
   * @return
   * @throws Exception
   */
  boolean checkFreezeHour() throws Exception;

  JsonMessage loadConfig() throws Exception;

  JsonMessage modifySms(ManageConfigModel model) throws Exception;

  JsonMessage modifySiteConfig(ManageConfigModel model) throws Exception;

  JsonMessage modifyPhoneCheck(ManageConfigModel model) throws Exception;

  SiteConfig queryForntSiteConfig() throws Exception;

  SiteConfig queryDownloadSiteConfig() throws Exception;

}
