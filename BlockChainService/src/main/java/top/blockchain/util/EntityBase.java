package top.blockchain.util;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EntityBase implements Serializable {
  private static final long serialVersionUID = -1857855680451434586L;

  public EntityBase() {
  }

  @Override
  public String toString() {
    try {
      return JsonUtil.stringify(this);
    } catch (Exception ex) {
      return ex.toString();
    }
  }

}
