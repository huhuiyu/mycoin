package test.top.blockchain;

import top.blockchain.util.Md5;

public class Temp {
  public static void main(String[] args) throws Exception {
    // System.out.println(JsonUtil.stringify(SmsBean.getTestSmsBean()));
    // System.out.println(JsonUtil.parse(JsonUtil.stringify(SmsBean.getTestSmsBean()),
    // SmsBean.class));

    // SiteConfig siteConfig = new SiteConfig();
    // siteConfig.setPoundage(new BigDecimal("0.3"));
    // siteConfig.setPayback(new BigDecimal("0.0005"));
    // String
    // teString="{\"poundage\":0.3,\"payback\":0.0005,\"freezeHour\":0,\"freezeHours\":0}";
    // System.out.println(JsonUtil.parse(teString, SiteConfig.class));
    // System.out.println(JsonUtil.stringify(siteConfig));

    // URLConnection connection = new URL("http://chndecoin.com/download/decoin.apk").openConnection();
    // InputStream is = connection.getInputStream();
    // OutputStream os = new FileOutputStream("test.apk");
    // byte[] bytes = new byte[8 * 1024];
    // int len = is.read(bytes);
    // while (len > 0) {
    // os.write(bytes, 0, len);
    // os.flush();
    // len = is.read(bytes);
    // }
    // os.close();
    // is.close();
    System.out.println(Md5.md5("user17702349425"));
    System.out.println(Md5.md5("user15886618632"));
    System.out.println("=====================>");
  }
}
