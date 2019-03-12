package top.blockchain.service;

import top.blockchain.model.user.UserBalanceRecordModel;
import top.blockchain.util.JsonMessage;

public interface UserBalanceRecordService {

  /**
   * 挂卖单（我要卖）
   * 
   * @param model
   * @return
   * @throws Exception
   */
  JsonMessage addBalanceRecord(UserBalanceRecordModel model) throws Exception;

  /**
   * 挂买单（我要买）
   * 
   * @param model
   * @return
   * @throws Exception
   */
  JsonMessage addBuyBalanceRecord(UserBalanceRecordModel model) throws Exception;

  /**
   * 卖给挂买单的
   * 
   * @param model
   * @return
   * @throws Exception
   */
  JsonMessage sell(UserBalanceRecordModel model) throws Exception;

  /**
   * 更改状态为已经付款
   * 
   * @param model
   * @return
   * @throws Exception
   */
  JsonMessage pay(UserBalanceRecordModel model) throws Exception;

  /**
   * 完成交易
   * 
   * @param model
   * @return
   * @throws Exception
   */
  JsonMessage finish(UserBalanceRecordModel model) throws Exception;
  
  /**
   * 查询用户当前挂单信息（最多一笔记录）
   * @param model
   * @return
   * @throws Exception
   */
  JsonMessage queryUserNowOrder(UserBalanceRecordModel model) throws Exception;
  
  /**
   * 直接发送给用户币
   * @param model
   * @return
   * @throws Exception
   */
  JsonMessage sendToUser(UserBalanceRecordModel model) throws Exception;
  
  /**
   * 查询挂买单的信息
   * 
   * @param model
   * @return
   * @throws Exception
   */
  JsonMessage queryBuys(UserBalanceRecordModel model) throws Exception;
  /**
   * 查询用户挂单的信息
   * 
   * @param model
   * @return
   * @throws Exception
   */
  JsonMessage queryUserOrders(UserBalanceRecordModel model) throws Exception;
  
  /**
   * 用户撤单
   * @param model
   * @return
   * @throws Exception
   */
  JsonMessage cancel(UserBalanceRecordModel model) throws Exception;

  JsonMessage queryBstatus(UserBalanceRecordModel model) throws Exception;

  JsonMessage queryBstatusmyself(UserBalanceRecordModel model) throws Exception;

  JsonMessage recast(UserBalanceRecordModel model) throws Exception;

  

  

}
