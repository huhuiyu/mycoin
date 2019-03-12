package top.blockchain.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

  public static String stringify(Object obj) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(obj);
  }

  public static <T> T parse(String json, Class<T> c) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(json, c);
  }

}
