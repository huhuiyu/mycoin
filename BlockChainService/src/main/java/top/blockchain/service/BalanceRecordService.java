package top.blockchain.service;

import top.blockchain.model.admin.BalanceRecordModel;
import top.blockchain.util.JsonMessage;

public interface BalanceRecordService {

  /**
   * 查询挂单信息
   * 
   * @return
   * @throws Exception
   */
  public JsonMessage queryAll(BalanceRecordModel model) throws Exception;

  /**
   * 处理24小时没有完成交易的信息
   * 
   * @return
   * @throws Exception
   */
  int resetTimeoutOrders() throws Exception;

  JsonMessage cancel(BalanceRecordModel model) throws Exception;

}
