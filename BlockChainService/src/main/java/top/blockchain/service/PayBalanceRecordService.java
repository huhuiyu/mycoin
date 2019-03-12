package top.blockchain.service;

import top.blockchain.model.admin.PayBalanceRecordModel;

public interface PayBalanceRecordService {

  /**
   * 发币功能
   * 
   * @param model
   * @return
   * @throws Exception
   */
  void addPayBalanceRecord(PayBalanceRecordModel model) throws Exception;

}
