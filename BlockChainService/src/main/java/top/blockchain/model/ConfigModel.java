package top.blockchain.model;

import top.blockchain.entity.TbConfig;
import top.blockchain.model.base.AdminBaseModel;

public class ConfigModel extends AdminBaseModel {

  private static final long serialVersionUID = -2529715270208651266L;

  private TbConfig config = new TbConfig();

  public ConfigModel() {
  } 

  public TbConfig getConfig() {
    return config;
  }

  public void setConfig(TbConfig config) {
    this.config = config;
  }
}
