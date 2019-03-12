package top.blockchain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import top.blockchain.dao.TbPayBalanceUpdateDAO;
import top.blockchain.entity.TbPayBalanceUpdate;
import top.blockchain.model.admin.PayBalanceUpdateModel;
import top.blockchain.service.PayBalanceUpdateService;
import top.blockchain.util.JsonMessage;
import top.blockchain.util.PageBean;

@Service
@Transactional(rollbackFor = Exception.class)
public class PayBalanceUpdateServiceImpl implements PayBalanceUpdateService {
  @Autowired
  private TbPayBalanceUpdateDAO tbPayBalanceUpdateDAO;

  @Override
  public JsonMessage query(PayBalanceUpdateModel model) throws Exception {
    PageBean page = model.getPage();
    PageHelper.startPage(page.getPageNumber(), page.getPageSize());
    Page<TbPayBalanceUpdate> list = (Page<TbPayBalanceUpdate>) tbPayBalanceUpdateDAO.queryAll(model.getUpdateInfo());
    page.setPageInfo(list);
    JsonMessage message = JsonMessage.getSuccessMessage("查询成功！");
    message.putData("list", list);
    message.putData("page", page);
    return message;
  }

}
