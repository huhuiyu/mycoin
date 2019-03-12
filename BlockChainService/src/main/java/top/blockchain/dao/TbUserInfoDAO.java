package top.blockchain.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import top.blockchain.entity.TbUser;
import top.blockchain.entity.TbUserInfo;

@Mapper
public interface TbUserInfoDAO {

  public TbUserInfo queryTotalBalance() throws Exception;

  public List<TbUserInfo> query() throws Exception;

  public String add(TbUserInfo userInfo) throws Exception;

  public int modify(TbUserInfo userInfo) throws Exception;

  public int addNew(TbUserInfo userInfo) throws Exception;

  public int modifyIdCard(TbUserInfo userInfo) throws Exception;

  public TbUserInfo queryByKey(TbUserInfo userInfo) throws Exception;

  public int modifyBalance(TbUserInfo userInfo) throws Exception;

  public TbUserInfo queryUserInfoByKey(TbUserInfo userInfo) throws Exception;

  public List<TbUserInfo> queryTopPaybalanceUserInfo() throws Exception;

  public int modifyPayBalance(TbUserInfo userInfo) throws Exception;

  public int modifyVip(TbUserInfo userInfo) throws Exception;

  public TbUserInfo queryParentByUid(TbUserInfo userInfo) throws Exception;

  public TbUser queryUserInfoByPhone(TbUser user) throws Exception;

  public TbUser queryUserInfoByTokenId(TbUser user) throws Exception;

}
