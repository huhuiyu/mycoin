package top.blockchain.util;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonMessage implements Serializable {
  private static final long serialVersionUID = 5414116991779501076L;
  private static final Logger log = LoggerFactory.getLogger(JsonMessage.class);

  private boolean success = false;
  private String message = "操作失败";
  private int code = 500;
  private Map<String, Object> dataMap;
  private String servertoken;
  private Object loginUser;

  public Object getLoginUser() {
    return loginUser;
  }

  public void setLoginUser(Object loginUser) {
    this.loginUser = loginUser;
  }

  public String getServertoken() {
    return servertoken;
  }

  public void setServertoken(String servertoken) {
    this.servertoken = servertoken;
  }

  public Map<String, Object> getDataMap() {
    return dataMap;
  }

  public void setDataMap(Map<String, Object> dataMap) {
    this.dataMap = dataMap;
  }

  public void putData(String key, Object data) {
    if (dataMap == null) {
      dataMap = new HashMap<>();
    }
    dataMap.put(key, data);
  }

  public void putDatas(Map<String, Object> datas) {
    if (dataMap == null) {
      dataMap = new HashMap<>();
    }
    dataMap.putAll(datas);
  }

  public void clearData() {
    if (dataMap == null) {
      dataMap = new HashMap<>();
    }
    dataMap.clear();
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public JsonMessage() {
    this(false, "操作失败");
  }

  public JsonMessage(boolean success, String message) {
    this.success = success;
    this.message = message;
  }

  public static JsonMessage getJsonMessage(int code, boolean success, String message, Map<String, Object> dataMap) {
    JsonMessage jsonMessage = new JsonMessage();
    jsonMessage.setCode(code);
    jsonMessage.setSuccess(success);
    jsonMessage.setMessage(message);
    jsonMessage.putData("timestamp", new Date().getTime());
    if (dataMap != null && !dataMap.isEmpty()) {
      jsonMessage.putDatas(dataMap);
    }
    return jsonMessage;
  }

  public static JsonMessage getSuccessMessage(String message) {
    return JsonMessage.getJsonMessage(200, true, message, null);
  }

  public static JsonMessage getSuccessMessage(String message, Map<String, Object> dataMap) {
    return JsonMessage.getJsonMessage(200, true, message, dataMap);
  }

  public static JsonMessage getFailMessage(String message) {
    return JsonMessage.getJsonMessage(500, false, message, null);
  }

  public static JsonMessage getFailMessage(Throwable ex) {
    log.error("程序错误。。。", ex);
    return JsonMessage.getJsonMessage(500, false, "服务器忙，请稍后重试。", null);
  }

  @Override
  public String toString() {
    return "JsonMessage [success=" + success + ", message=" + message + ", code=" + code + ", dataMap=" + dataMap + ", servertoken=" + servertoken + "]";
  }

}
