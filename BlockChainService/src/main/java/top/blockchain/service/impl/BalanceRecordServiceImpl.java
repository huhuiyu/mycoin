package top.blockchain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import top.blockchain.config.MyAppConfigBean;
import top.blockchain.dao.TbBalanceRecordDAO;
import top.blockchain.entity.TbBalanceRecord;
import top.blockchain.exception.MyAppException;
import top.blockchain.model.admin.BalanceRecordModel;
import top.blockchain.service.BalanceRecordService;
import top.blockchain.util.JsonMessage;

@Service
@Transactional(rollbackFor = Exception.class)
public class BalanceRecordServiceImpl implements BalanceRecordService {

  @Autowired
  private TbBalanceRecordDAO tbBalanceRecordDAO;
  @Autowired
  private MyAppConfigBean myAppConfigBean;

  @Override
  public JsonMessage queryAll(BalanceRecordModel model) throws Exception {
    PageHelper.startPage(model.getPage().getPageNumber(), model.getPage().getPageSize());
    Page<TbBalanceRecord> list = (Page<TbBalanceRecord>) tbBalanceRecordDAO.queryAll(model.getRecord());
    model.getPage().setPageInfo(list);
    JsonMessage message = JsonMessage.getSuccessMessage("查询成功！");
    message.putData("list", list);
    message.putData("page", model.getPage());
    return message;
  }

  @Override
  @Transactional(isolation = Isolation.SERIALIZABLE)
  public JsonMessage cancel(BalanceRecordModel model) throws Exception {
    TbBalanceRecord record = tbBalanceRecordDAO.queryByKey(model.getRecord());
    if (record == null) {
      return JsonMessage.getFailMessage("挂单信息不存在");
    }
    if (!"01".equals(record.getBstatus())) { // 必须是挂单
      return JsonMessage.getFailMessage("挂单状态不正确");
    }
    if (!"02".equals(record.getBrtype())) {
      return JsonMessage.getFailMessage("挂单类型不正确");
    }
    record.setBstatus("06"); // 系统撤单
    int r = tbBalanceRecordDAO.modifyStatus(record);
    if (r != 1) {
      throw MyAppException.getMyAppException("交易状态变动异常，挂单失败");
    }
    return JsonMessage.getSuccessMessage("撤单完成。");
  }

  @Override
  public int resetTimeoutOrders() throws Exception {
    int r = tbBalanceRecordDAO.freezeUser(myAppConfigBean.freezeHours);
    tbBalanceRecordDAO.resetFreezeUser(myAppConfigBean.freezeHours);
    tbBalanceRecordDAO.resetTimeoutOrders(myAppConfigBean.freezeHours);
    return r;
  }

}
