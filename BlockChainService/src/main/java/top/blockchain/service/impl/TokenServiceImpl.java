package top.blockchain.service.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import top.blockchain.converter.StringConverter;
import top.blockchain.dao.TbTokenDAO;
import top.blockchain.entity.TbToken;
import top.blockchain.service.TokenService;

@Service
@Transactional(rollbackFor = Exception.class)
public class TokenServiceImpl implements TokenService {

  private static final Logger LOG = LoggerFactory.getLogger(TokenServiceImpl.class);
  @Autowired
  private TbTokenDAO tbTokenDAO;

  @Override
  public TbToken saveOrUpdate(String token) throws Exception {
    LOG.debug(String.format("获取到的token%s", token));
    // 没有令牌环的情况
    if (StringConverter.isEmpty(token)) {
      return newToken();
    }
    // 查看令牌环是否存在
    TbToken tbToken = new TbToken();
    tbToken.setToken(token);
    tbToken = tbTokenDAO.queryByKey(tbToken);
    if (tbToken == null) {
      return newToken();
    }
    // 修改令牌环时间
    tbTokenDAO.modifyLast(tbToken);
    return tbToken;
  }

  private TbToken newToken() throws Exception {
    TbToken tbToken = new TbToken();
    tbToken.setToken(UUID.randomUUID().toString());
    tbTokenDAO.add(tbToken);
    return tbToken;
  }

}
