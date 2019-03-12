package top.blockchain.converter;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DateConverter implements Converter<String, Date> {
  private static final String[] FORMATS = new String[] { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd" };
  private static final SimpleDateFormat FORMAT = new SimpleDateFormat();

  @Override
  public Date convert(String source) {
    if (StringConverter.isEmpty(source)) {
      return null;
    }
    for (String f : FORMATS) {
      try {
        FORMAT.applyPattern(f);
        return FORMAT.parse(source.trim());
      } catch (Exception e) {
      }
    }
    return null;
  }

}
