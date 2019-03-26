package top.blockchain.util;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import top.blockchain.entity.SmsBean;

@Component
public class SmsUtil {

  private static final Logger LOG = LoggerFactory.getLogger(SmsUtil.class);

  public static final Random           RAND   = new Random();
  public static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyyMMdd");

  public SmsUtil() {
  }

  public static String makeCode() {
    return (RAND.nextInt(999999 - 100000 + 1) + 100000) + "";
  }

  public SendSmsResponse send(SmsBean smsBean, String phone, String template, Map<String, Object> params) throws Exception {
    LOG.debug("phone:" + phone + ",smsBean:" + smsBean + ",template:" + template);
    // 初始化ascClient,暂时不支持多region
    IClientProfile profile = DefaultProfile.getProfile(smsBean.getRegion1(), smsBean.getAccesskeyid(), smsBean.getAccesskeysecret());
    DefaultProfile.addEndpoint(smsBean.getRegion1(), smsBean.getRegion2(), smsBean.getProduct(), smsBean.getDomain());
    IAcsClient acsClient = new DefaultAcsClient(profile);
    // 组装请求对象
    SendSmsRequest request = new SendSmsRequest();
    // 使用post提交
    request.setMethod(MethodType.POST);
    // 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
    request.setPhoneNumbers(phone);
    // 必填:短信签名-可在短信控制台中找到
    request.setSignName(smsBean.getSign());
    // 必填:短信模板-可在短信控制台中找到
    request.setTemplateCode(template);
    // 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
    // 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
    String info = JsonUtil.stringify(params); // new JSONObject(params).toString();
    LOG.debug(info);
    request.setTemplateParam(info);
    String outid = new Date().getTime() + "";
    LOG.debug(outid);
    request.setOutId(outid);
    // 请求失败这里会抛ClientException异常
    SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
    return sendSmsResponse;
  }

  public QuerySendDetailsResponse querySendDetails(SmsBean smsBean, String bizId, String phone) throws ServerException, ClientException {
    // 初始化acsClient,暂不支持region化
    IClientProfile profile = DefaultProfile.getProfile(smsBean.getRegion1(), smsBean.getAccesskeyid(), smsBean.getAccesskeysecret());
    DefaultProfile.addEndpoint(smsBean.getRegion1(), smsBean.getRegion2(), smsBean.getProduct(), smsBean.getDomain());
    IAcsClient acsClient = new DefaultAcsClient(profile);
    // 组装请求对象
    QuerySendDetailsRequest request = new QuerySendDetailsRequest();
    // 必填-号码
    request.setPhoneNumber(phone);
    // 可选-流水号
    request.setBizId(bizId);
    // 必填-发送日期 支持30天内记录查询，格式yyyyMMdd
    request.setSendDate(FORMAT.format(new Date()));
    // 必填-页大小
    request.setPageSize(10L);
    // 必填-当前页码从1开始计数
    request.setCurrentPage(1L);
    QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);
    return querySendDetailsResponse;
  }

  public static String getPhoneCheck(String id, String phone, String name, String url, String appcode) throws Exception {
    String        geturl     = String.format(url, id, phone, URLEncoder.encode(name, "UTF-8"));
    URLConnection connection = new URL(geturl).openConnection();
    connection.setRequestProperty("Authorization", "APPCODE " + appcode);
    connection.connect();
    Scanner       scanner = new Scanner(connection.getInputStream());
    StringBuilder sb      = new StringBuilder();
    while (scanner.hasNextLine()) {
      sb.append(scanner.nextLine());
    }
    scanner.close();
    return sb.toString();
  }

  public static void sendTest(String phone) throws Exception {
    SmsUtil             util = new SmsUtil();
    Map<String, Object> data = new HashMap<String, Object>();
    data.put("code", makeCode());
    SendSmsResponse sendSmsResponse = util.send(SmsBean.getTestSmsBean(), phone, "SMS_136785015", data);
    if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
      LOG.debug("ok");
    }
    LOG.debug(String.format("%s,%s,%s,%s", sendSmsResponse.getCode(), sendSmsResponse.getMessage(), sendSmsResponse.getBizId(), sendSmsResponse.getRequestId()));
  }

  public static void queryTest() throws Exception {
    SmsUtil                  util                     = new SmsUtil();
    QuerySendDetailsResponse querySendDetailsResponse = util.querySendDetails(SmsBean.getTestSmsBean(), "999320428524809051^0", "15973637383");
    for (QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs()) {
      LOG.debug("Content=" + smsSendDetailDTO.getContent());
      LOG.debug("ErrCode=" + smsSendDetailDTO.getErrCode());
      LOG.debug("OutId=" + smsSendDetailDTO.getOutId());
      LOG.debug("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
      LOG.debug("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
      LOG.debug("SendDate=" + smsSendDetailDTO.getSendDate());
      LOG.debug("SendStatus=" + smsSendDetailDTO.getSendStatus());
      LOG.debug("Template=" + smsSendDetailDTO.getTemplateCode());
    }
  }

  public static void main(String[] args) throws Exception {

    // 999320428524809051^0
    LOG.debug("begin=================>");
    // sendTest("15973637383");
    // queryTest();
    // LOG.debug(
    // JsonUtil.parse(getPhoneCheck("430703199907242015", "15973673696", "杨尚易",
    // "http://fephone.market.alicloudapi.com/phoneCheck?idCard=%s&mobile=%s&name=%s",
    // "4988f0f6061241bd969defff43666ff4"),
    // TbUserPhoneCheck.class).toString());
    // PhoneCheck pc = new PhoneCheck();
    // pc.setUrl("http://fephone.market.alicloudapi.com/phoneCheck?idCard=%s&mobile=%s&name=%s");
    // pc.setAppCode("4988f0f6061241bd969defff43666ff4");
    // System.out.println(JsonUtil.stringify(pc));

    // http://fephone.market.alicloudapi.com/phoneCheck?idCard=%s&mobile=%s&name=%s

    LOG.debug("end=================>");
  }
}