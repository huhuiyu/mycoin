package top.blockchain.controller.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.blockchain.converter.StringConverter;
import top.blockchain.entity.TbSiteNotice;
import top.blockchain.model.admin.SiteNoticeModel;
import top.blockchain.service.SiteNoticeService;
import top.blockchain.util.JsonMessage;

@RestController
@RequestMapping("/manage/siteNotice")
public class ManageSiteNoticeController {

  @Autowired
  private SiteNoticeService siteNoticeService;

  public ManageSiteNoticeController() {
  }

  @RequestMapping("/queryAll")
  public JsonMessage queryAll(SiteNoticeModel model) throws Exception {
    return siteNoticeService.queryAll(model);
  }

  @RequestMapping("/save")
  public JsonMessage save(SiteNoticeModel model) throws Exception {
    if (StringConverter.isEmpty(model.getSiteNotice().getTitle())) {
      return JsonMessage.getFailMessage("公告标题必须填写");
    }
    if (StringConverter.isEmpty(model.getSiteNotice().getContent())) {
      return JsonMessage.getFailMessage("公告内容必须填写");
    }
    siteNoticeService.save(model);
    return JsonMessage.getSuccessMessage("添加成功");
  }

  @RequestMapping("/update")
  public JsonMessage update(SiteNoticeModel model) throws Exception {
    TbSiteNotice siteNotice = new TbSiteNotice();
    siteNotice.setSnid(model.getSiteNotice().getSnid());
    if (StringConverter.isEmpty(model.getSiteNotice().getTitle())) {
      return JsonMessage.getFailMessage("公告标题必须填写");
    }
    if (StringConverter.isEmpty(model.getSiteNotice().getContent())) {
      return JsonMessage.getFailMessage("公告内容必须填写");
    }
    if (StringConverter.isEmpty(model.getSiteNotice().getIntop())) {
      return JsonMessage.getFailMessage("是否置顶必须填写");
    }
    if (StringConverter.isEmpty(model.getSiteNotice().getIsEnable())) {
      return JsonMessage.getFailMessage("是否启用必须填写");
    }
    model.getSiteNotice().setSnid(model.getSiteNotice().getSnid());
    siteNoticeService.update(model);
    return JsonMessage.getSuccessMessage("修改公告信息成功!");
  }

}
