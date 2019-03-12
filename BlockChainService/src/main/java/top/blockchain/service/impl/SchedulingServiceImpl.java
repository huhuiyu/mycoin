package top.blockchain.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import top.blockchain.config.MyAppConfigBean;
import top.blockchain.dao.TbBlockIdsDAO;
import top.blockchain.dao.TbConfigDAO;
import top.blockchain.dao.TbTokenDAO;
import top.blockchain.dao.TbTokenInfoDAO;
import top.blockchain.dao.TbUserBalanceRecordDAO;
import top.blockchain.dao.TbUserInfoDAO;
import top.blockchain.entity.SiteConfig;
import top.blockchain.entity.TbUserBalanceRecord;
import top.blockchain.entity.TbUserInfo;
import top.blockchain.service.BalanceRecordService;
import top.blockchain.service.ConfigService;
import top.blockchain.service.SchedulingService;

@Service
public class SchedulingServiceImpl implements SchedulingService {

  private static final Logger LOG = LoggerFactory.getLogger(SchedulingServiceImpl.class);
  private static final SimpleDateFormat FORMAT = new SimpleDateFormat("当前时间为：yyyy年MM月dd日 HH:mm:ss");

  @Autowired
  private MyAppConfigBean myAppConfigBean;
  @Autowired
  private ConfigService configService;

  private static final Random RAND = new Random();

  @Autowired
  private TbTokenDAO tbTokenDAO;
  @Autowired
  private TbBlockIdsDAO tbBlockIdsDAO;
  @Autowired
  private TbTokenInfoDAO tbTokenInfoDAO;
  @Autowired
  private TbConfigDAO tbConfigDAO;
  @Autowired
  private TbUserInfoDAO tbUserInfoDAO;
  // @Autowired
  // private TbBalanceRecordDAO tbBalanceRecordDAO;
  @Autowired
  private TbUserBalanceRecordDAO tbUserBalanceRecordDAO;
  @Autowired
  private BalanceRecordService balanceRecordService;

  @Override
  @Scheduled(fixedDelay = 1000 * 60 * 60)
  public void showTime() {
    try {
      LOG.info(FORMAT.format(tbConfigDAO.queryNow()));
    } catch (Exception ex) {
      LOG.error(ex.getMessage());
    }
  }

  @Override
  @Scheduled(initialDelay = 1000 * 60, fixedDelay = 1000 * 600)
  public void removeToken() {
    LOG.info("定时移除过期token");
    try {
      tbTokenInfoDAO.deleteToken(myAppConfigBean.tokenRemoveTimes);
      int count = tbTokenDAO.removeTimeoutToken(myAppConfigBean.tokenRemoveTimes);
      LOG.info("过期token数量" + count);
    } catch (Exception ex) {
      LOG.error("移除token错误", ex);
    }
  }

  @Override
  @Scheduled(initialDelay = 1000 * 10, fixedDelay = 1000 * 60)
  public void checkBlockId() {
    LOG.info("定时检测BlockId使用");
    try {
      int count = tbBlockIdsDAO.queryCount();
      LOG.info("可用BlockId数量" + count);
      if (count > myAppConfigBean.blockidLimit) {
        return;
      }
      // 批量创建长度7位的BlockId
      int checkAmount = myAppConfigBean.blockidAmount / myAppConfigBean.blockidBatch;
      int check = 0;
      while (check < checkAmount) {
        List<String> blockIds = makeBlockIds(myAppConfigBean.blockidBatch);
        if (tbBlockIdsDAO.checkBatch(blockIds) > 0) {
          continue;
        }
        int add = tbBlockIdsDAO.addBatch(blockIds);
        if (add <= 0) {
          continue;
        }
        check++;
      }
      LOG.info("批量生产BlockId完成");
    } catch (Exception ex) {
      LOG.error("检测BlockId使用错误", ex);
    }
  }

  @Override
  @Scheduled(cron = "0 30 0 * * ? ")
  public void updatePayBalance() throws Exception {
    long start = 0;
    int r = 0;
    // 超时自动撤单处理
    // start = System.currentTimeMillis();
    // r = tbBalanceRecordDAO.cancelTimeoutOrders(myAppConfigBean.freezeHours);
    // LOG.info(String.format("处理超时撤单完成，数量：%s，时间：%s", r, System.currentTimeMillis()
    // - start));
    start = System.currentTimeMillis();
    r = balanceRecordService.resetTimeoutOrders();
    LOG.info(String.format("处理超时未完成交易撤单完成，数量：%s，时间：%s", r, System.currentTimeMillis() - start));
    start = System.currentTimeMillis();
    LOG.info("更新用户待释放币");
    SiteConfig siteConfig = configService.querySiteConfig();
    int count = 0;
    while (true) {
      // 查询一笔没有处理待释放币的记录
      List<TbUserInfo> list = tbUserInfoDAO.queryTopPaybalanceUserInfo();
      if (list.isEmpty()) { // 没有记录就表示处理完毕
        LOG.info(String.format("处理更新用户待释放币完毕，数量：%s,时间：%s", count, System.currentTimeMillis() - start));
        return;
      }
      for (TbUserInfo userInfo : list) {
        count++;
        LOG.debug("userinfo:" + userInfo);
        // 处理待释放币信息
        TbUserBalanceRecord ubr = new TbUserBalanceRecord();
        ubr.setUid(userInfo.getUid());
        BigDecimal paybalance = userInfo.getPaybalance();
        BigDecimal ubalance = paybalance.multiply(siteConfig.getPayback());
        ubalance = ubalance.setScale(2, BigDecimal.ROUND_FLOOR);
        ubr.setBalance(ubalance);
        ubr.setBdir(1); // 资金增加
        ubr.setBtype(10); // 待释放币发放
        ubr.setInfo("待释放币发放");
        LOG.debug(String.format("%s,%s", ubalance, paybalance));
        // 更新用户资金
        userInfo.setBalance(userInfo.getBalance().add(ubalance));
        userInfo.setPaybalance(userInfo.getPaybalance().subtract(ubalance));
        tbUserInfoDAO.modifyPayBalance(userInfo);
        // 添加交易记录
        tbUserBalanceRecordDAO.save(ubr);
      }
    }
  }

  private List<String> makeBlockIds(int amount) {
    List<String> blockIds = new ArrayList<String>();
    for (int i = 0; i < amount; i++) {
      blockIds.add("" + (RAND.nextInt(9999999 - 1000000 + 1) + 1000000));
    }
    return blockIds;
  }

}
