package top.blockchain.service;

import top.blockchain.model.admin.SiteNoticeModel;
import top.blockchain.model.user.UserSiteNoticeModel;
import top.blockchain.util.JsonMessage;

public interface SiteNoticeService {

  JsonMessage queryAll(SiteNoticeModel model) throws Exception;

  JsonMessage save(SiteNoticeModel model) throws Exception;

  JsonMessage update(SiteNoticeModel model) throws Exception;

  JsonMessage queryUserAll(UserSiteNoticeModel model) throws Exception;

}
