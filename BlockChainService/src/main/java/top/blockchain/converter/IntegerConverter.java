package top.blockchain.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IntegerConverter implements Converter<String, Integer> {

  public static boolean isEmpty(int intv) {
    return Integer.MIN_VALUE == intv;
  }

  @Override
  public Integer convert(String source) {
    if (StringConverter.isEmpty(source)) {
      return Integer.MIN_VALUE;
    }
    try {
      return Integer.parseInt(source.trim());
    } catch (Exception e) {
      return Integer.MIN_VALUE;
    }
  }

}
