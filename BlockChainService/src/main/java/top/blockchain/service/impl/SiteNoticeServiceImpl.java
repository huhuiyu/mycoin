package top.blockchain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import top.blockchain.dao.TbSiteNoticeDAO;
import top.blockchain.entity.TbSiteNotice;
import top.blockchain.exception.MyAppException;
import top.blockchain.model.admin.SiteNoticeModel;
import top.blockchain.model.user.UserSiteNoticeModel;
import top.blockchain.service.SiteNoticeService;
import top.blockchain.util.JsonMessage;

@Service
@Transactional(rollbackFor = Exception.class)
public class SiteNoticeServiceImpl implements SiteNoticeService {
  @Autowired
  private TbSiteNoticeDAO tbSiteNoticeDAO;

  @Override
  public JsonMessage queryAll(SiteNoticeModel model) throws Exception {
    PageHelper.startPage(model.getPage().getPageNumber(), model.getPage().getPageSize());
    Page<TbSiteNotice> list = (Page<TbSiteNotice>) tbSiteNoticeDAO.queryAll(model.getSiteNotice());
    model.getPage().setPageInfo(list);
    JsonMessage message = JsonMessage.getSuccessMessage("查询成功！");
    message.putData("list", list);
    message.putData("page", model.getPage());
    return message;
  }

  @Override
  public JsonMessage save(SiteNoticeModel model) throws Exception {
    TbSiteNotice siteNotice = model.getSiteNotice();
    tbSiteNoticeDAO.save(siteNotice);
    JsonMessage message = JsonMessage.getSuccessMessage("添加成功!");
    return message;
  }

  @Override
  public JsonMessage update(SiteNoticeModel model) throws Exception {
    int r = tbSiteNoticeDAO.update(model.getSiteNotice());
    if (r == 1) {
      return JsonMessage.getFailMessage("公告信息修改成功");
    }
    throw MyAppException.getMyAppException("公告信息修改失败");
  }

  @Override
  public JsonMessage queryUserAll(UserSiteNoticeModel model) throws Exception {
    PageHelper.startPage(model.getPage().getPageNumber(), model.getPage().getPageSize());
    Page<TbSiteNotice> list = (Page<TbSiteNotice>) tbSiteNoticeDAO.queryUserAll();
    model.getPage().setPageInfo(list);
    JsonMessage message = JsonMessage.getSuccessMessage("查询成功！");
    message.putData("list", list);
    message.putData("page",model.getPage());
    return message;
  }

}
