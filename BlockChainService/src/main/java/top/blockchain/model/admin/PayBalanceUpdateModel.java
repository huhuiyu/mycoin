package top.blockchain.model.admin;

import top.blockchain.entity.TbPayBalanceUpdate;
import top.blockchain.model.base.AdminBaseModel;

public class PayBalanceUpdateModel extends AdminBaseModel {
  private static final long serialVersionUID = -9032433612334059816L;
  private TbPayBalanceUpdate updateInfo = new TbPayBalanceUpdate();

  public TbPayBalanceUpdate getUpdateInfo() {
    return updateInfo;
  }

  public void setUpdateInfo(TbPayBalanceUpdate updateInfo) {
    this.updateInfo = updateInfo;
  }

}
