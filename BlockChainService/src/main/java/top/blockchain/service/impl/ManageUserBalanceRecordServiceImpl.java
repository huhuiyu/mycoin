package top.blockchain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import top.blockchain.dao.TbUserBalanceRecordDAO;
import top.blockchain.entity.TbUserBalanceRecord;
import top.blockchain.model.admin.ManageUserBalanceRecordModel;
import top.blockchain.service.ManageUserBalanceRecordService;
import top.blockchain.util.JsonMessage;

@Service
@Transactional(rollbackFor = Exception.class)
public class ManageUserBalanceRecordServiceImpl implements ManageUserBalanceRecordService {
  @Autowired
  private TbUserBalanceRecordDAO tbUserBalanceRecordDAO;

  @Override
  public JsonMessage queryAll(ManageUserBalanceRecordModel model) throws Exception {
    PageHelper.startPage(model.getPage().getPageNumber(), model.getPage().getPageSize());
    Page<TbUserBalanceRecord> list = (Page<TbUserBalanceRecord>) tbUserBalanceRecordDAO.queryAll(model.getRecord());
    model.getPage().setPageInfo(list);
    JsonMessage message = JsonMessage.getSuccessMessage("查询成功！");
    message.putData("list", list);
    message.putData("page", model.getPage());
    return message;
  }

}
