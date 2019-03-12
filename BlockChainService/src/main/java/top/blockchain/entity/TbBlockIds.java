package top.blockchain.entity;

import java.util.Date;

import top.blockchain.util.EntityBase;

public class TbBlockIds extends EntityBase {
  private static final long serialVersionUID = 5126286313171931030L;

  private String blockId;
  private String isUsed;
  private Date created;

  public TbBlockIds() {
  }

  public String getBlockId() {
    return blockId;
  }

  public void setBlockId(String blockId) {
    this.blockId = blockId;
  }

  public String getIsUsed() {
    return isUsed;
  }

  public void setIsUsed(String isUsed) {
    this.isUsed = isUsed;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

}
