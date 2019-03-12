package top.blockchain.dao;

import org.apache.ibatis.annotations.Mapper;

import top.blockchain.entity.TbToken;

@Mapper
public interface TbTokenDAO {
  public int add(TbToken token) throws Exception;

  public TbToken queryByKey(TbToken token) throws Exception;

  public int modifyLast(TbToken token) throws Exception;

  public int removeTimeoutToken(int times) throws Exception;
}
