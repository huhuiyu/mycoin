package top.blockchain.model.user;

import top.blockchain.entity.TbSiteNotice;

public class UserSiteNoticeModel extends UserModel {

  private static final long serialVersionUID = -498216961583919949L;

  private TbSiteNotice siteNotice = new TbSiteNotice();

  public UserSiteNoticeModel() {
    
  }

  public TbSiteNotice getSiteNotice() {
    return siteNotice;
  }

  public void setSiteNotice(TbSiteNotice siteNotice) {
    this.siteNotice = siteNotice;
  }
  
  
}
