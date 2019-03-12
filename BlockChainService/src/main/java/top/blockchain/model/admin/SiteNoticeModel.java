package top.blockchain.model.admin;

import top.blockchain.entity.TbSiteNotice;
import top.blockchain.model.base.AdminBaseModel;

public class SiteNoticeModel extends AdminBaseModel {

  private static final long serialVersionUID = -1959608569244957622L;

  private TbSiteNotice siteNotice = new TbSiteNotice();

  public SiteNoticeModel() {
  }

  public TbSiteNotice getSiteNotice() {
    return siteNotice;
  }

  public void setSiteNotice(TbSiteNotice siteNotice) {
    this.siteNotice = siteNotice;
  }

}
