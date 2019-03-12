package top.blockchain.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import top.blockchain.dao.TbPayBalanceRecordDAO;
import top.blockchain.dao.TbUserBalanceRecordDAO;
import top.blockchain.entity.TbPayBalanceRecord;
import top.blockchain.entity.TbUserBalanceRecord;
import top.blockchain.entity.TbUserInfo;
import top.blockchain.exception.MyAppException;
import top.blockchain.model.admin.PayBalanceRecordModel;
import top.blockchain.service.PayBalanceRecordService;

@Service
@Transactional(rollbackFor = Exception.class)
public class PayBalanceRecordServiceImpl implements PayBalanceRecordService {

  private static final Logger LOG = LoggerFactory.getLogger(PayBalanceRecordServiceImpl.class);

  @Autowired
  private TbPayBalanceRecordDAO tbPayBalanceRecordDAO;
  @Autowired
  private TbUserBalanceRecordDAO tbUserBalanceRecordDAO;

  public PayBalanceRecordServiceImpl() {
  }

  @Override
  public void addPayBalanceRecord(PayBalanceRecordModel model) throws Exception {
    TbPayBalanceRecord record = model.getRecord();
    record.setAid(model.getLoginUser().getAid());
    // 保存发币记录
    tbPayBalanceRecordDAO.save(record);
    // 更新用户账号
    TbUserInfo userInfo = new TbUserInfo();
    userInfo.setUid(record.getUid());
    userInfo = tbPayBalanceRecordDAO.queryUserByKey(userInfo);
    if (userInfo == null) {
      throw new Exception("用户信息不存在");
    }
    LOG.debug(userInfo.toString());

    // 添加资金变动信息
    TbUserBalanceRecord ubr = new TbUserBalanceRecord();
    ubr.setUid(record.getUid());
    ubr.setBalance(record.getBalance());
    ubr.setBdir(1);
    if ("1".equals(record.getTarget())) { // 修改返回账号
      userInfo.setPaybalance(userInfo.getPaybalance().add(record.getBalance()));
      ubr.setBtype(17); // 直接发放待释放币
      ubr.setInfo("直接发放待释放币");
    } else if ("2".equals(record.getTarget())) { // 修改返回账号
      userInfo.setBalance(userInfo.getBalance().add(record.getBalance()));
      ubr.setBtype(18); // 直接发放币
      ubr.setInfo("直接发放币");
    }
    int r = tbPayBalanceRecordDAO.updateUserBalance(userInfo);
    if (r != 1) {
      throw MyAppException.getMyAppException("用户信息更新失败");
    }
    r = tbUserBalanceRecordDAO.save(ubr);
    if (r != 1) {
      throw MyAppException.getMyAppException("交易信息添加失败");
    }
  }

}
