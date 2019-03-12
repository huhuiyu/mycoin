package top.blockchain.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import top.blockchain.entity.TbUser;

@Mapper
public interface TbUserDAO {
  public TbUser login(TbUser user) throws Exception;

  public TbUser queryphone(TbUser user) throws Exception;

  public int add(TbUser user) throws Exception;

  public int delete(TbUser user) throws Exception;

  public int modify(TbUser user) throws Exception;

  public int blockid(TbUser user) throws Exception;

  public int queryFound(TbUser user) throws Exception;

  public int foundPassword(TbUser user) throws Exception;

  public List<TbUser> queryAll(TbUser user) throws Exception;

  public int undelete(TbUser user) throws Exception;

  public TbUser queryByPhone(TbUser user) throws Exception;

  public int modifyPassword(TbUser user) throws Exception;

  public int modifyPaypwd(TbUser user) throws Exception;

  public TbUser queryByKey(TbUser user) throws Exception;

  public List<TbUser> queryTeam(TbUser user) throws Exception;

}
