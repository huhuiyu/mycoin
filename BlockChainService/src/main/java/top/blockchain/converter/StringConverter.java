package top.blockchain.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringConverter implements Converter<String, String> {

  public static boolean isEmpty(String str) {
    return (str == null) || str.trim().equals("");
  }

  @Override
  public String convert(String source) {
    return source == null ? "" : source.trim();
  }

}
