package top.blockchain.model.base;

import top.blockchain.entity.TbTokenInfo;
import top.blockchain.util.EntityBase;
import top.blockchain.util.PageBean;

public abstract class BaseModel extends EntityBase {
  private static final long serialVersionUID = -137055849508844216L;

  private PageBean page = new PageBean();
  private String servertoken;
  private String imageTokenCode;

  public BaseModel() {
  }

  public PageBean getPage() {
    return page;
  }

  public void setPage(PageBean page) {
    this.page = page;
  }

  public String getServertoken() {
    return servertoken;
  }

  public void setServertoken(String servertoken) {
    this.servertoken = servertoken;
  }

  public TbTokenInfo getToken() {
    return TbTokenInfo.getTokenInfo(servertoken);
  }

  public TbTokenInfo getToken(String key) {
    return TbTokenInfo.getTokenInfo(servertoken, key);
  }

  public String getImageTokenCode() {
    return imageTokenCode;
  }

  public void setImageTokenCode(String imageTokenCode) {
    this.imageTokenCode = imageTokenCode;
  }

}
