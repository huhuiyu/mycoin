package top.blockchain.entity;

import top.blockchain.util.EntityBase;

public class TbSiteNotice extends EntityBase {
  private static final long serialVersionUID = 2361292678551028014L;
  private java.lang.Integer snid;
  private java.lang.String title;
  private java.lang.String content;
  private java.lang.String intop;
  private java.lang.String isEnable;
  private java.util.Date lastupdate;

  public TbSiteNotice() {
  }

  public TbSiteNotice(java.lang.Integer snid, java.lang.String title, java.lang.String content, java.lang.String intop, java.lang.String isEnable, java.util.Date lastupdate) {
    this.snid = snid;
    this.title = title;
    this.content = content;
    this.intop = intop;
    this.isEnable = isEnable;
    this.lastupdate = lastupdate;
  }

  public void setSnid(java.lang.Integer snid) {
    this.snid = snid;
  }

  public java.lang.Integer getSnid() {
    return snid;
  }

  public void setTitle(java.lang.String title) {
    this.title = title;
  }

  public java.lang.String getTitle() {
    return title;
  }

  public void setContent(java.lang.String content) {
    this.content = content;
  }

  public java.lang.String getContent() {
    return content;
  }

  public void setIntop(java.lang.String intop) {
    this.intop = intop;
  }

  public java.lang.String getIntop() {
    return intop;
  }

  public void setIsEnable(java.lang.String isEnable) {
    this.isEnable = isEnable;
  }

  public java.lang.String getIsEnable() {
    return isEnable;
  }

  public void setLastupdate(java.util.Date lastupdate) {
    this.lastupdate = lastupdate;
  }

  public java.util.Date getLastupdate() {
    return lastupdate;
  }

}
