package top.blockchain.service;

import top.blockchain.model.admin.PayBalanceUpdateModel;
import top.blockchain.util.JsonMessage;

public interface PayBalanceUpdateService {

  JsonMessage query(PayBalanceUpdateModel model) throws Exception;

}
