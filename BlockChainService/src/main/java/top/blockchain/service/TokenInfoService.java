package top.blockchain.service;

import top.blockchain.entity.PhoneCode;
import top.blockchain.entity.TbAdmin;
import top.blockchain.entity.TbUser;
import top.blockchain.model.other.PhoneCodeModel;
import top.blockchain.util.JsonMessage;

public interface TokenInfoService {
  public String USER_KEY = "user";
  public String ADMIN_USER_KEY = "admin_user";
  public String PHONE_CODE_KEY = "phone_code";
  public String IMAGE_CODE_KEY = "image_code";

  /**
   * 保存前台登陆用户信息
   * 
   * @param uid
   *          用户id
   * @param token
   *          令牌环
   * @return 前台登陆用户信息
   * @throws Exception
   */
  int addUserInfo(int uid, String token) throws Exception;

  /**
   * 加载登陆用户信息
   * 
   * @param token
   *          令牌
   * @return 登陆用户信息
   * @throws Exception
   */
  TbUser loadUser(String token) throws Exception;

  /**
   * 移除登陆用户信息
   * 
   * @param token
   *          令牌
   * @return
   * @throws Exception
   */
  int removeUserInfo(String token) throws Exception;

  /**
   * 保存后台登陆用户信息
   * 
   * @param aid
   *          用户id
   * @param token
   *          令牌环
   * @return 后台登陆用户信息
   * @throws Exception
   */
  int addAdminUserInfo(int aid, String token) throws Exception;

  /**
   * 加载后台用户信息
   * 
   * @param token
   *          令牌
   * @return 后台登陆用户信息
   * @throws Exception
   */
  TbAdmin loadAdminUser(String token) throws Exception;

  /**
   * 移除后台登陆用户信息
   * 
   * @param token
   *          令牌
   * @return
   * @throws Exception
   */
  int removeAdminUserInfo(String token) throws Exception;

  /**
   * 发送电话校验码
   * 
   * @return 发送的结果
   * @throws Exception
   */
  JsonMessage sendPhoneCode(PhoneCodeModel model) throws Exception;

  /**
   * 获取电话校验码
   * 
   * @param token
   *          令牌
   * @return
   * @throws Exception
   */
  PhoneCode getPhoneCode(String token) throws Exception;

  /**
   * 获取图片校验码
   * 
   * @return
   * @throws Exception
   */
  String imageCode(String token) throws Exception;

  /**
   * 校验图片校验码
   * 
   * @param token
   * @param imageCode
   * @return
   * @throws Exception
   */
  boolean checkImageCode(String token, String imageCode) throws Exception;

}
