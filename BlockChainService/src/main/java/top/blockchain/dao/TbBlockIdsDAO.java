package top.blockchain.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbBlockIdsDAO {
  public String queryId() throws Exception;

  public int queryCount() throws Exception;

  public int check(String blockId) throws Exception;

  public int add(String blockId) throws Exception;

  public int checkBatch(List<String> blockIds) throws Exception;

  public int addBatch(List<String> blockIds) throws Exception;

  public int updateisUser(String str) throws Exception;
}
