package top.blockchain.service.impl;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import top.blockchain.converter.StringConverter;
import top.blockchain.dao.TbTokenInfoDAO;
import top.blockchain.entity.PhoneCode;
import top.blockchain.entity.SmsBean;
import top.blockchain.entity.TbAdmin;
import top.blockchain.entity.TbTokenInfo;
import top.blockchain.entity.TbUser;
import top.blockchain.model.other.PhoneCodeModel;
import top.blockchain.service.ConfigService;
import top.blockchain.service.TokenInfoService;
import top.blockchain.util.ImageCode;
import top.blockchain.util.JsonMessage;
import top.blockchain.util.JsonUtil;
import top.blockchain.util.SmsUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class TokenInfoServiceImpl implements TokenInfoService {

  // private static final Logger log = LoggerFactory.getLogger(TokenInfoServiceImpl.class);

  @Autowired
  private TbTokenInfoDAO tbTokenInfoDAO;
  @Autowired
  private ConfigService  configService;

  @Autowired
  private SmsUtil smsUtil;

  @Override
  public int addUserInfo(int uid, String token) throws Exception {
    TbTokenInfo tokenInfo = TbTokenInfo.getTokenInfo(token, USER_KEY);
    tokenInfo.setInfo(uid + "");
    TbTokenInfo info = tbTokenInfoDAO.queryByKey(tokenInfo);
    if (info == null) {
      return tbTokenInfoDAO.add(tokenInfo);
    }
    else {
      return tbTokenInfoDAO.modify(tokenInfo);
    }
  }

  @Override
  public TbUser loadUser(String token) throws Exception {
    TbTokenInfo info = TbTokenInfo.getTokenInfo(token, USER_KEY);
    TbUser      user = tbTokenInfoDAO.loadUser(info);
    if (user != null) {
      // user.setUid(-1);
      user.setPassword("*****");
      user.setPaypwd("*****");
    }
    return user;
  }

  @Override
  public int removeUserInfo(String token) throws Exception {
    TbTokenInfo tokenInfo = TbTokenInfo.getTokenInfo(token, USER_KEY);
    return tbTokenInfoDAO.deleteTokenKey(tokenInfo);
  }

  @Override
  public int addAdminUserInfo(int aid, String token) throws Exception {
    TbTokenInfo tokenInfo = TbTokenInfo.getTokenInfo(token, ADMIN_USER_KEY);
    tokenInfo.setInfo(aid + "");
    TbTokenInfo info = tbTokenInfoDAO.queryByKey(tokenInfo);
    if (info == null) {
      return tbTokenInfoDAO.add(tokenInfo);
    }
    else {
      return tbTokenInfoDAO.modify(tokenInfo);
    }
  }

  @Override
  public TbAdmin loadAdminUser(String token) throws Exception {
    TbTokenInfo info = new TbTokenInfo();
    info.setInfokey(ADMIN_USER_KEY);
    info.setToken(token);
    TbAdmin user = tbTokenInfoDAO.loadAdminUser(info);
    if (user != null) {
      // user.setAid(-1);
      user.setPassword("*****");
      user.setIsEnable(null);
    }
    return user;
  }

  @Override
  public int removeAdminUserInfo(String token) throws Exception {
    TbTokenInfo tokenInfo = TbTokenInfo.getTokenInfo(token, ADMIN_USER_KEY);
    return tbTokenInfoDAO.deleteTokenKey(tokenInfo);
  }

  @Override
  public JsonMessage sendPhoneCode(PhoneCodeModel model) throws Exception {
    String token = model.getServertoken();
    String phone = model.getPhoneCode().getPhone();
    if (!checkImageCode(token, model.getImageTokenCode())) {
      return JsonMessage.getFailMessage("图片校验码错误");
    }
    // checkPhoneCode检查是否存在电话校验码，存在就提示不用再次发送，不存在就删除可能超时的电话校验码，第二步，发送校验码并写入数据库
    TbTokenInfo tokenInfo = TbTokenInfo.getTokenInfo(token, PHONE_CODE_KEY);
    TbTokenInfo check     = tbTokenInfoDAO.checkPhoneCode(tokenInfo);
    // 如果存在记录且电话号码相同就表示五分钟内重复发送
    if (check != null && JsonUtil.parse(check.getInfo(), PhoneCode.class).getPhone().equals(phone)) {
      return JsonMessage.getFailMessage("校验码五分钟内有效，不用重复发送");
    }
    tbTokenInfoDAO.deleteTokenKey(tokenInfo); // 删除可能超时的电话校验码

    // 发送校验码
    PhoneCode phoneCode = new PhoneCode();
    phoneCode.setCode(SmsUtil.makeCode());
    phoneCode.setPhone(phone);
    Map<String, Object> data = new HashMap<String, Object>();
    data.put("code", phoneCode.getCode());
    // 使用ucpass
    // UcPassSmsResult ucPassSmsResult = smsUtil.sendPhoneCode(phoneCode.getPhone(), phoneCode.getCode());
    // 使用阿里云
    SmsBean         sms             = configService.querySms(); // 获取sms配置
    SendSmsResponse sendSmsResponse = smsUtil.send(sms, phone, sms.getTemplate().getValidateCode(), data);
    if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
      // if (ucPassSmsResult != null && "OK".equals(ucPassSmsResult.getMsg())) {
      // 写入数据库
      tokenInfo.setInfo(JsonUtil.stringify(phoneCode));
      tbTokenInfoDAO.add(tokenInfo);
      return JsonMessage.getSuccessMessage("校验码发送成功");
    }
    // log.info(String.format("sms:%s", ucPassSmsResult));
    return JsonMessage.getFailMessage("校验码发送失败，请重试");
  }

  @Override
  public PhoneCode getPhoneCode(String token) throws Exception {
    TbTokenInfo tokenInfo = TbTokenInfo.getTokenInfo(token, PHONE_CODE_KEY);
    tokenInfo = tbTokenInfoDAO.queryByKey(tokenInfo);
    if (tokenInfo == null || StringConverter.isEmpty(tokenInfo.getInfo())) {
      return null;
    }
    return JsonUtil.parse(tokenInfo.getInfo(), PhoneCode.class);
  }

  @Override
  public String imageCode(String token) throws Exception {
    String      code      = ImageCode.makeCode(6);
    TbTokenInfo tokenInfo = TbTokenInfo.getTokenInfo(token, IMAGE_CODE_KEY);
    tokenInfo.setInfo(code);
    TbTokenInfo info = tbTokenInfoDAO.queryByKey(tokenInfo);
    if (info == null) {
      tbTokenInfoDAO.add(tokenInfo);
    }
    else {
      tbTokenInfoDAO.modify(tokenInfo);
    }
    return code;
  }

  @Override
  public boolean checkImageCode(String token, String imageCode) throws Exception {
    if (StringConverter.isEmpty(imageCode)) {
      return false;
    }
    TbTokenInfo info = TbTokenInfo.getTokenInfo(token, IMAGE_CODE_KEY);
    info = tbTokenInfoDAO.queryByKey(info);
    if (info == null) {
      return false;
    }
    if (StringConverter.isEmpty(info.getInfo())) {
      return false;
    }
    return imageCode.equalsIgnoreCase(info.getInfo());
  }

}
