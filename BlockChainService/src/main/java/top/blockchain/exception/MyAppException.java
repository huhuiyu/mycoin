package top.blockchain.exception;

/**
 * 自定义异常类，用于服务层或者控制器层抛出自定义异常
 * 
 * @author DarkKnight
 *
 */
public class MyAppException extends Exception {
  private static final long serialVersionUID = -1966089021638344640L;

  private int code = 500;

  public MyAppException() {
    super();
  }

  public MyAppException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public MyAppException(String message, Throwable cause) {
    super(message, cause);
  }

  public MyAppException(String message) {
    super(message);
  }

  public MyAppException(Throwable cause) {
    super(cause);
  }

  public MyAppException(String message, int code) {
    super(message);
    this.code = code;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  /**
   * 获取自定义异常
   * 
   * @param message
   *          异常原因
   * @return
   */
  public static MyAppException getMyAppException(String message) {
    return new MyAppException(message, 500);
  }

  /**
   * 获取自定义异常
   * 
   * @param message
   *          异常原因
   * @param code
   *          异常代码
   * @return
   */
  public static MyAppException getMyAppException(String message, int code) {
    return new MyAppException(message, code);
  }

}
