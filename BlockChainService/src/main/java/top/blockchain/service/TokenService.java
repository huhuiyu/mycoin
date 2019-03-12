package top.blockchain.service;

import top.blockchain.entity.TbToken;

public interface TokenService {
  public TbToken saveOrUpdate(String token) throws Exception;

}
