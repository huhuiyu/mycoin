package top.blockchain.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import top.blockchain.entity.TbAdmin;

@Mapper
public interface TbAdminDAO {
  public List<TbAdmin> query() throws Exception;

  public int delete(TbAdmin admin) throws Exception;

  public int modify(TbAdmin admin) throws Exception;

  public TbAdmin login(TbAdmin admin) throws Exception;

  public int add(TbAdmin admin) throws Exception;

  public int queryFound(TbAdmin admin) throws Exception;

  public int updatePassword(TbAdmin admin) throws Exception;

  public TbAdmin queryByKey(TbAdmin admin) throws Exception;
}
