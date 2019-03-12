package top.blockchain.service;

import top.blockchain.model.user.UserUtilModel;
import top.blockchain.util.JsonMessage;

public interface UserUtilService {

  /**
   * 电话实名校验
   * 
   * @param model
   * @throws Exception
   */
  JsonMessage phoneCheck(UserUtilModel model) throws Exception;
}
