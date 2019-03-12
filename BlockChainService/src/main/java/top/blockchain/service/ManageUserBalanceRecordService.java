package top.blockchain.service;

import top.blockchain.model.admin.ManageUserBalanceRecordModel;
import top.blockchain.util.JsonMessage;

public interface ManageUserBalanceRecordService {

  JsonMessage queryAll(ManageUserBalanceRecordModel model) throws Exception;

}
