package top.blockchain.service;

import top.blockchain.entity.TbUser;
import top.blockchain.model.UserInfoModel;
import top.blockchain.util.JsonMessage;

public interface UserInfoService {

  JsonMessage modifyPassword(TbUser user) throws Exception;

  JsonMessage modifyPaypwd(TbUser user) throws Exception;

  JsonMessage modifyWechat(UserInfoModel model) throws Exception;

  JsonMessage modifyNickName(UserInfoModel model) throws Exception;

  JsonMessage modifyAlipay(UserInfoModel model) throws Exception;

  JsonMessage queryUserInfoByKey(UserInfoModel model) throws Exception;

  JsonMessage vip(UserInfoModel model) throws Exception;

  JsonMessage queryBalanceRecord(UserInfoModel model) throws Exception;

}
