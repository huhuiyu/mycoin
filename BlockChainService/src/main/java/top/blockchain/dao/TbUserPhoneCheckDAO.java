package top.blockchain.dao;

import org.apache.ibatis.annotations.Mapper;

import top.blockchain.entity.TbUserPhoneCheck;

@Mapper
public interface TbUserPhoneCheckDAO {
  TbUserPhoneCheck queryByPhone(String phone) throws Exception;

  int add(TbUserPhoneCheck phoneCheck) throws Exception;

  int modify(TbUserPhoneCheck phoneCheck) throws Exception;
}
