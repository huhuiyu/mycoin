package top.blockchain.dao;

import org.apache.ibatis.annotations.Mapper;

import top.blockchain.entity.TbAdmin;
import top.blockchain.entity.TbTokenInfo;
import top.blockchain.entity.TbUser;

@Mapper
public interface TbTokenInfoDAO {
  public int deleteToken(int times) throws Exception;

  public int deleteTokenKey(TbTokenInfo tokenInfo) throws Exception;

  public int add(TbTokenInfo tokenInfo) throws Exception;

  public int modify(TbTokenInfo tokenInfo) throws Exception;

  public TbTokenInfo queryByKey(TbTokenInfo tokenInfo) throws Exception;

  public TbUser loadUser(TbTokenInfo tokenInfo) throws Exception;

  public TbAdmin loadAdminUser(TbTokenInfo tokenInfo) throws Exception;

  public TbTokenInfo checkPhoneCode(TbTokenInfo tokenInfo) throws Exception;

  public int removePhoneCode(TbTokenInfo tokenInfo) throws Exception;

}
