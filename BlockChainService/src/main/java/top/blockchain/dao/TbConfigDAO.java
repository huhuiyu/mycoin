package top.blockchain.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import top.blockchain.entity.TbConfig;

@Mapper
public interface TbConfigDAO {

  public List<TbConfig> query() throws Exception;

  public int add(TbConfig config) throws Exception;

  public int modify(TbConfig config) throws Exception;

  public TbConfig queryByKey(TbConfig config) throws Exception;
  
  public Date queryNow() throws Exception;

}
