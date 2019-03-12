package top.blockchain.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyAppConfigBean {

  @Value("${token.removetime}")
  public int tokenRemoveTimes; // token过期分钟数
  @Value("${blockid.limit}")
  public int blockidLimit; // 报警数
  @Value("${blockid.amount}")
  public int blockidAmount; // 生产数
  @Value("${blockid.batch}")
  public int blockidBatch; // 批量生产数
  @Value("${phone.check}")
  public int phoneCheck; // 三网认证间隔时间
  @Value("${freeze.hours}")
  public int freezeHours; // 交易冻结时间

  public MyAppConfigBean() {
  }

}
