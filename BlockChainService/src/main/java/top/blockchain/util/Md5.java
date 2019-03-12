package top.blockchain.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * -md5加密
 *
 * @author 胡辉煜
 *
 */
public class Md5 {
  /**
   * -获取String的MD5值
   *
   * @param info 字符串
   * @return 该字符串的MD5值
   */
  public static String md5(String info) {
    try {
      // 获取 MessageDigest 对象，参数为 MD5 字符串，表示这是一个 MD5 算法（其他还有 SHA1 算法等）：
      MessageDigest md5 = MessageDigest.getInstance("MD5");
      // update(byte[])方法，输入原数据
      // 类似StringBuilder对象的append()方法，追加模式，属于一个累计更改的过程
      md5.update(info.getBytes("UTF-8"));
      // digest()被调用后,MessageDigest对象就被重置，即不能连续再次调用该方法计算原数据的MD5值。可以手动调用reset()方法重置输入源。
      // digest()返回值16位长度的哈希值，由byte[]承接
      byte[] md5Array = md5.digest();
      // byte[]通常我们会转化为十六进制的32位长度的字符串来使用,本文会介绍三种常用的转换方法
      return bytesToHex(md5Array);
    } catch (NoSuchAlgorithmException ex) {
      return "";
    } catch (UnsupportedEncodingException ex) {
      return "";
    }
  }

  /**
   * -通过位运算 将字节数组到十六进制的转换
   *
   * @param byteArray 原始字节数组
   * @return 十六进制转换结果
   */
  public static String bytesToHex(byte[] byteArray) {
    String chars = "0123456789abcdef";
    char[] hexDigits = chars.toCharArray();
    char[] resultCharArray = new char[byteArray.length * 2];
    int index = 0;
    for (byte b : byteArray) {
      resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
      resultCharArray[index++] = hexDigits[b & 0xf];
    }
    return new String(resultCharArray);
  }

}
