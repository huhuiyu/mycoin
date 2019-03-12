package top.blockchain.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import top.blockchain.dao.TbConfigDAO;
import top.blockchain.entity.PhoneCheck;
import top.blockchain.entity.SiteConfig;
import top.blockchain.entity.SmsBean;
import top.blockchain.entity.TbConfig;
import top.blockchain.exception.MyAppException;
import top.blockchain.model.admin.ManageConfigModel;
import top.blockchain.service.ConfigService;
import top.blockchain.util.JsonMessage;
import top.blockchain.util.JsonUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class ConfigServiceImpl implements ConfigService {

  @Autowired
  private TbConfigDAO tbConfigDAO;

  @Override
  public int addSmsConfig(SmsBean config) throws Exception {
    TbConfig conf = new TbConfig();
    conf.setConfigKey(SMS_BEAN);
    TbConfig sconfig = tbConfigDAO.queryByKey(conf);
    if (sconfig != null) {
      sconfig.setConfigVal(JsonUtil.stringify(config));
      return tbConfigDAO.modify(sconfig);
    }
    return tbConfigDAO.add(conf);
  }

  @Override
  public SmsBean querySms() throws Exception {
    TbConfig config = new TbConfig();
    config.setConfigKey(SMS_BEAN);
    config = tbConfigDAO.queryByKey(config);
    if (config == null) {
      return null;
    }
    return JsonUtil.parse(config.getConfigVal(), SmsBean.class);
  }

  @Override
  public JsonMessage modifySms(ManageConfigModel model) throws Exception {
    TbConfig config = new TbConfig();
    config.setConfigKey(SMS_BEAN);
    config = tbConfigDAO.queryByKey(config);
    if (config == null) {
      return JsonMessage.getFailMessage("配置信息不存在");
    }
    config.setConfigVal(JsonUtil.stringify(model.getSmsBean()));
    int r = tbConfigDAO.modify(config);
    if (r == 1) {
      return JsonMessage.getSuccessMessage("配置信息修改成功");
    }
    throw MyAppException.getMyAppException("配置信息修改失败");
  }

  @Override
  public int addPhoneCheckConfig(PhoneCheck config) throws Exception {
    TbConfig conf = new TbConfig();
    conf.setConfigKey(PHONECHECK_BEAN_INFO);
    TbConfig sconfig = tbConfigDAO.queryByKey(conf);
    if (sconfig != null) {
      sconfig.setConfigVal(JsonUtil.stringify(config));
      return tbConfigDAO.modify(sconfig);
    }
    return tbConfigDAO.add(conf);
  }

  @Override
  public JsonMessage modifyPhoneCheck(ManageConfigModel model) throws Exception {
    TbConfig config = new TbConfig();
    config.setConfigKey(PHONECHECK_BEAN_INFO);
    config = tbConfigDAO.queryByKey(config);
    if (config == null) {
      return JsonMessage.getFailMessage("配置信息不存在");
    }
    config.setConfigVal(JsonUtil.stringify(model.getPhoneCheck()));
    int r = tbConfigDAO.modify(config);
    if (r == 1) {
      return JsonMessage.getSuccessMessage("配置信息修改成功");
    }
    throw MyAppException.getMyAppException("配置信息修改失败");
  }

  @Override
  public JsonMessage modifySiteConfig(ManageConfigModel model) throws Exception {
    TbConfig config = new TbConfig();
    config.setConfigKey(SITE_CONFIG);
    config = tbConfigDAO.queryByKey(config);
    if (config == null) {
      return JsonMessage.getFailMessage("配置信息不存在");
    }
    config.setConfigVal(JsonUtil.stringify(model.getSiteConfig()));
    int r = tbConfigDAO.modify(config);
    if (r == 1) {
      return JsonMessage.getSuccessMessage("配置信息修改成功");
    }
    throw MyAppException.getMyAppException("配置信息修改失败");
  }

  @Override
  public PhoneCheck queryPhoneCheck() throws Exception {
    TbConfig config = new TbConfig();
    config.setConfigKey(PHONECHECK_BEAN_INFO);
    config = tbConfigDAO.queryByKey(config);
    if (config == null) {
      return null;
    }
    return JsonUtil.parse(config.getConfigVal(), PhoneCheck.class);
  }

  @Override
  public SiteConfig querySiteConfig() throws Exception {
    TbConfig config = new TbConfig();
    config.setConfigKey(SITE_CONFIG);
    config = tbConfigDAO.queryByKey(config);
    if (config == null) {
      return null;
    }
    return JsonUtil.parse(config.getConfigVal(), SiteConfig.class);
  }

  @Override
  public SiteConfig queryForntSiteConfig() throws Exception {
    SiteConfig config = querySiteConfig();
    config.setFreezeHour(0);
    config.setPayback(null);
    config.setParentReward(null);
    config.setApkUrl(null);
    config.setVersionUrl(null);
    config.setExchangeReward(null);
    return config;
  }

  @Override
  public SiteConfig queryDownloadSiteConfig() throws Exception {
    SiteConfig sconfig = querySiteConfig();
    SiteConfig config = new SiteConfig();
    config.setApkUrl(sconfig.getApkUrl());
    config.setVersionUrl(sconfig.getVersionUrl());
    return config;
  }

  @Override
  public Date queryNow() throws Exception {
    return tbConfigDAO.queryNow();
  }

  @Override
  public boolean checkFreezeHour() throws Exception {
    SiteConfig config = querySiteConfig();
    Calendar calendar = Calendar.getInstance();
    Date now = queryNow();
    calendar.setTime(now);
    return config.getFreezeHour() > calendar.get(Calendar.HOUR_OF_DAY);
  }

  @Override
  public JsonMessage loadConfig() throws Exception {
    JsonMessage message = JsonMessage.getSuccessMessage("加载配置成功");
    message.putData("phoneCheck", queryPhoneCheck());
    message.putData("siteConfig", querySiteConfig());
    message.putData("sms", querySms());
    return message;
  }

}
