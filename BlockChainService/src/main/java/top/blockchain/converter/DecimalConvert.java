package top.blockchain.converter;

import java.math.BigDecimal;

import org.springframework.core.convert.converter.Converter;

public class DecimalConvert implements Converter<String, BigDecimal> {

  @Override
  public BigDecimal convert(String source) {
    if (StringConverter.isEmpty(source)) {
      return null;
    }

    try {
      return new BigDecimal(source.trim());
    } catch (Exception e) {
      return null;
    }
  }

}
