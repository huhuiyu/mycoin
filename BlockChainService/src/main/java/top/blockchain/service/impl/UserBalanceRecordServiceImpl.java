package top.blockchain.service.impl;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import top.blockchain.converter.StringConverter;
import top.blockchain.dao.TbBalanceRecordDAO;
import top.blockchain.dao.TbUserBalanceRecordDAO;
import top.blockchain.dao.TbUserDAO;
import top.blockchain.dao.TbUserInfoDAO;
import top.blockchain.entity.SiteConfig;
import top.blockchain.entity.TbBalanceRecord;
import top.blockchain.entity.TbUser;
import top.blockchain.entity.TbUserBalanceRecord;
import top.blockchain.entity.TbUserInfo;
import top.blockchain.exception.MyAppException;
import top.blockchain.model.user.UserBalanceRecordModel;
import top.blockchain.service.ConfigService;
import top.blockchain.service.UserBalanceRecordService;
import top.blockchain.util.JsonMessage;
import top.blockchain.util.PageBean;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserBalanceRecordServiceImpl implements UserBalanceRecordService {
  private static final Logger LOG = LoggerFactory.getLogger(UserBalanceRecordServiceImpl.class);

  @Autowired
  private TbBalanceRecordDAO tbBalanceRecordDAO;
  @Autowired
  private TbUserInfoDAO tbUserInfoDAO;
  @Autowired
  private ConfigService configService;
  @Autowired
  private TbUserBalanceRecordDAO tbUserBalanceRecordDAO;
  @Autowired
  private TbUserDAO tbUserDAO;

  @Override
  public JsonMessage addBalanceRecord(UserBalanceRecordModel model) throws Exception {
    if (configService.checkFreezeHour()) {
      return JsonMessage.getFailMessage("当前时间段无法交易");
    }
    // 校验资金输入
    BigDecimal balance = model.getRecord().getBalance();
    if (balance == null || BigDecimal.ZERO.compareTo(balance) >= 0) {
      return JsonMessage.getFailMessage("挂单金额必须输入且大于零");
    }
    BigDecimal price = model.getRecord().getPrice();
    if (price == null || BigDecimal.ZERO.compareTo(price) >= 0) {
      return JsonMessage.getFailMessage("售价必须输入且大于零");
    }
    // 查询用户资金信息
    TbUserInfo userInfo = new TbUserInfo();
    userInfo.setUid(model.getLoginUser().getUid());
    userInfo = tbUserInfoDAO.queryByKey(userInfo);
    if (userInfo == null) {
      return JsonMessage.getFailMessage("用户资金信息不存在");
    }
    if (StringConverter.isEmpty(userInfo.getIdCard())) {
      return JsonMessage.getFailMessage("必须先实名认证才能交易");
    }
    if (!"y".equals(userInfo.getVip())) {
      return JsonMessage.getFailMessage("会员才能挂单交易");
    }
    BigDecimal ubalance = userInfo.getBalance();
    if (ubalance == null || ubalance.compareTo(BigDecimal.ZERO) <= 0 || ubalance.compareTo(balance) < 0) {
      return JsonMessage.getFailMessage("用户资金不够，可用余额：" + ubalance);
    }
    // 查询用户是否存在交易
    TbBalanceRecord record = tbBalanceRecordDAO.queryUserRecord(model.getLoginUser());
    if (record != null) {
      return JsonMessage.getFailMessage("存在没有完成的挂单信息，操作不能继续");
    }
    // 发布用户交易信息
    model.getRecord().setAuid(model.getLoginUser().getUid());
    model.getRecord().setPrice(price);
    int r = tbBalanceRecordDAO.addUserRecord(model.getRecord());
    if (r != 1) {
      throw MyAppException.getMyAppException("挂单失败，请稍后重试");
    }
    // 更改用户冻结金额和余额
    userInfo.setBalance(ubalance.subtract(balance));
    userInfo.setFreeze(balance);
    r = tbUserInfoDAO.modifyBalance(userInfo);
    if (r != 1) {
      throw MyAppException.getMyAppException("用户资金变动异常，挂单失败");
    }
    return JsonMessage.getSuccessMessage("挂单成功");
  }

  @Override
  @Transactional(isolation = Isolation.SERIALIZABLE)
  public JsonMessage addBuyBalanceRecord(UserBalanceRecordModel model) throws Exception {
    if (configService.checkFreezeHour()) {
      return JsonMessage.getFailMessage("当前时间段无法交易");
    }
    // 校验资金输入
    BigDecimal balance = model.getRecord().getBalance();
    if (balance == null || BigDecimal.ZERO.compareTo(balance) >= 0) {
      return JsonMessage.getFailMessage("挂单金额必须输入且大于零");
    }
    BigDecimal price = model.getRecord().getPrice();
    if (price == null || BigDecimal.ZERO.compareTo(price) >= 0) {
      return JsonMessage.getFailMessage("售价必须输入且大于零");
    }
    // 查询用户资金信息
    TbUserInfo userInfo = new TbUserInfo();
    userInfo.setUid(model.getLoginUser().getUid());
    userInfo = tbUserInfoDAO.queryByKey(userInfo);
    if (userInfo == null) {
      return JsonMessage.getFailMessage("用户资金信息不存在");
    }
    if (StringConverter.isEmpty(userInfo.getIdCard())) {
      return JsonMessage.getFailMessage("必须先实名认证才能交易");
    }
    if (!"y".equals(userInfo.getVip())) {
      return JsonMessage.getFailMessage("会员才能挂单交易");
    }
    // 查询用户是否存在交易
    TbBalanceRecord record = tbBalanceRecordDAO.queryUserRecord(model.getLoginUser());
    if (record != null) {
      return JsonMessage.getFailMessage("存在没有完成的挂单信息，操作不能继续");
    }
    // 发布用户交易信息
    model.getRecord().setBuid(model.getLoginUser().getUid());
    model.getRecord().setPrice(price);
    int r = tbBalanceRecordDAO.addUserBuyRecord(model.getRecord());
    if (r != 1) {
      throw MyAppException.getMyAppException("挂单失败，请稍后重试");
    }
    return JsonMessage.getSuccessMessage("挂单成功");
  }

  @Override
  @Transactional(isolation = Isolation.SERIALIZABLE)
  public JsonMessage sell(UserBalanceRecordModel model) throws Exception {
    if (configService.checkFreezeHour()) {
      return JsonMessage.getFailMessage("当前时间段无法交易");
    }
    // 查询交易信息
    TbBalanceRecord record = tbBalanceRecordDAO.queryByKey(model.getRecord());
    if (record == null) {
      return JsonMessage.getFailMessage("挂单信息不存在");
    }
    if (!"01".equals(record.getBstatus())) { // 必须是挂单
      return JsonMessage.getFailMessage("挂单状态不正确");
    }
    if (!"02".equals(record.getBrtype())) {
      return JsonMessage.getFailMessage("挂单类型不正确");
    }
    // 查询用户是否存在交易
    TbBalanceRecord urecord = tbBalanceRecordDAO.queryUserRecord(model.getLoginUser());
    if (urecord != null) {
      return JsonMessage.getFailMessage("存在没有完成的挂单信息，操作不能继续");
    }
    // 查询用户资金信息
    TbUserInfo userInfo = new TbUserInfo();
    userInfo.setUid(model.getLoginUser().getUid());
    userInfo = tbUserInfoDAO.queryByKey(userInfo);
    if (userInfo == null) {
      return JsonMessage.getFailMessage("用户资金信息不存在");
    }
    if (StringConverter.isEmpty(userInfo.getIdCard())) {
      return JsonMessage.getFailMessage("必须先实名认证才能交易");
    }
    if (!"y".equals(userInfo.getVip())) {
      return JsonMessage.getFailMessage("会员才能挂单交易");
    }
    // 获取配置信息
    SiteConfig siteConfig = configService.querySiteConfig();
    BigDecimal ubalance = userInfo.getBalance();
    BigDecimal freeze = record.getBalance().multiply(siteConfig.getPoundage()).setScale(2, BigDecimal.ROUND_FLOOR);// 需要扣除手续费
    freeze = freeze.add(record.getBalance());
    if (ubalance == null || ubalance.compareTo(BigDecimal.ZERO) <= 0 || ubalance.compareTo(freeze) < 0) {
      return JsonMessage.getFailMessage("用户资金不够，可用余额：" + ubalance);
    }
    // 更改用户冻结金额和余额
    userInfo.setBalance(ubalance.subtract(freeze));
    userInfo.setFreeze(freeze);
    int r = tbUserInfoDAO.modifyBalance(userInfo);
    if (r != 1) {
      throw MyAppException.getMyAppException("用户资金变动异常，挂单失败");
    }
    // 更新状态
    record.setBstatus("02"); // 交易中
    record.setAuid(model.getLoginUser().getUid());
    r = tbBalanceRecordDAO.modifyStatus(record);
    if (r != 1) {
      throw MyAppException.getMyAppException("交易状态变动异常，挂单失败");
    }
    return JsonMessage.getSuccessMessage("交易申请成功，请查询交易信息并联系买家。");
  }

  @Override
  @Transactional(isolation = Isolation.SERIALIZABLE)
  public JsonMessage cancel(UserBalanceRecordModel model) throws Exception {
    if (configService.checkFreezeHour()) {
      return JsonMessage.getFailMessage("当前时间段无法交易");
    }
    TbUser user = tbUserDAO.queryByKey(model.getLoginUser());
    if (!user.getPaypwd().equals(model.getPaypwd())) {
      return JsonMessage.getFailMessage("支付密码不正确！");
    }
    // 查询交易信息
    TbBalanceRecord record = tbBalanceRecordDAO.queryByKey(model.getRecord());
    if (record == null) {
      return JsonMessage.getFailMessage("挂单信息不存在");
    }
    if (!"01".equals(record.getBstatus())) { // 必须是挂单
      return JsonMessage.getFailMessage("挂单状态不正确");
    }
    if (!"02".equals(record.getBrtype())) {
      return JsonMessage.getFailMessage("挂单类型不正确");
    }
    if (record.getBuid() == null || !record.getBuid().equals(model.getLoginUser().getUid())) {
      return JsonMessage.getFailMessage("只能对自己的挂单做撤单");
    }
    record.setBstatus("04"); // 撤单
    int r = tbBalanceRecordDAO.modifyStatus(record);
    if (r != 1) {
      throw MyAppException.getMyAppException("交易状态变动异常，挂单失败");
    }
    return JsonMessage.getSuccessMessage("撤单完成。");
  }

  @Override
  @Transactional(isolation = Isolation.SERIALIZABLE)
  public JsonMessage pay(UserBalanceRecordModel model) throws Exception {
    if (configService.checkFreezeHour()) {
      return JsonMessage.getFailMessage("当前时间段无法交易");
    }
    TbUser user = tbUserDAO.queryByKey(model.getLoginUser());
    if (!user.getPaypwd().equals(model.getPaypwd())) {
      return JsonMessage.getFailMessage("支付密码不正确！");
    }

    // 必须是买家，必须是交易中
    // 查询交易信息
    TbBalanceRecord record = tbBalanceRecordDAO.queryByKey(model.getRecord());
    if (record == null) {
      return JsonMessage.getFailMessage("挂单信息不存在");
    }
    if (!"02".equals(record.getBstatus())) {
      return JsonMessage.getFailMessage("挂单状态异常");
    }
    LOG.debug(String.format("交易信息:%s,登陆用户信息%s", record, model.getLoginUser()));
    if (record.getBuid() == null || !record.getBuid().equals(model.getLoginUser().getUid())) {
      return JsonMessage.getFailMessage("你不是买家");
    }
    record.setBstatus("03"); // 已经付款
    int r = tbBalanceRecordDAO.modifyStatus(record);
    if (r != 1) {
      throw MyAppException.getMyAppException("交易状态变动异常，挂单失败");
    }
    return JsonMessage.getSuccessMessage("已经更改状态为已付款，请查询交易信息并联系卖家。");
  }

  @Override
  @Transactional(isolation = Isolation.SERIALIZABLE)
  public JsonMessage finish(UserBalanceRecordModel model) throws Exception {
    if (configService.checkFreezeHour()) {
      return JsonMessage.getFailMessage("当前时间段无法交易");
    }
    // 挂买单brtype=02，必须是卖出账号auid完成交易,必须已经付款状态的
    TbBalanceRecord record = tbBalanceRecordDAO.queryByKey(model.getRecord());
    if (record == null) {
      return JsonMessage.getFailMessage("挂单信息不存在");
    }
    if (!"03".equals(record.getBstatus())) {
      return JsonMessage.getFailMessage("挂单状态异常");
    }
    LOG.debug(String.format("交易信息:%s,登陆用户信息%s", record, model.getLoginUser()));
    if (record.getAuid() == null || !record.getAuid().equals(model.getLoginUser().getUid())) {
      return JsonMessage.getFailMessage("你不是卖家");
    }
    // 支付宝密码认证
    TbUser user = tbUserDAO.queryByKey(model.getLoginUser());
    if (!user.getPaypwd().equals(model.getPaypwd())) {
      return JsonMessage.getFailMessage("支付密码不正确！");
    }

    // 查询买家和卖家信息
    TbUserInfo auser = new TbUserInfo();
    auser.setUid(model.getLoginUser().getUid());
    auser = tbUserInfoDAO.queryUserInfoByKey(auser);
    if (auser == null) {
      return JsonMessage.getFailMessage("卖家信息异常");
    }
    // 获取配置信息
    SiteConfig siteConfig = configService.querySiteConfig();
    BigDecimal freeze = record.getBalance().multiply(siteConfig.getPoundage()).setScale(2, BigDecimal.ROUND_FLOOR);// 需要扣除手续费
    freeze = freeze.add(record.getBalance());
    if (!auser.getFreeze().equals(freeze)) {
      return JsonMessage.getFailMessage("交易金额信息异常");
    }
    auser.setUid(model.getLoginUser().getUid());
    TbUserInfo buser = new TbUserInfo();
    buser.setUid(record.getBuid());
    buser = tbUserInfoDAO.queryUserInfoByKey(buser);
    if (buser == null) {
      return JsonMessage.getFailMessage("买家信息异常");
    }
    buser.setUid(record.getBuid());
    LOG.debug(String.format("卖家信息:%s,买家信息%s", auser, buser));
    // 添加资金变动信息
    TbUserBalanceRecord ubr = new TbUserBalanceRecord();
    ubr.setUid(auser.getUid());
    ubr.setBalance(auser.getFreeze());
    ubr.setBdir(-1);
    ubr.setBtype(12); // 交易卖出
    ubr.setInfo("交易卖出");
    int r = tbUserBalanceRecordDAO.save(ubr);
    if (r != 1) {
      throw MyAppException.getMyAppException("卖家资金变动记录失败");
    }
    // 转账和解除冻结
    auser.setFreeze(BigDecimal.ZERO);
    r = tbUserInfoDAO.modifyBalance(auser);
    if (r != 1) {
      throw MyAppException.getMyAppException("卖家资金解冻失败");
    }
    ubr = new TbUserBalanceRecord();
    ubr.setUid(buser.getUid());
    ubr.setBalance(record.getBalance());
    ubr.setBdir(1);
    ubr.setBtype(11); // 交易买入
    ubr.setInfo("交易买入");
    r = tbUserBalanceRecordDAO.save(ubr);
    if (r != 1) {
      throw MyAppException.getMyAppException("买家资金变动记录失败");
    }
    buser.setBalance(buser.getBalance().add(record.getBalance()));
    // 买家要奖励待释放币
    BigDecimal userReward = record.getBalance().multiply(siteConfig.getUserReward()).setScale(2, BigDecimal.ROUND_FLOOR);
    buser.setPaybalance(buser.getPaybalance().add(userReward));
    r = tbUserInfoDAO.modifyBalance(buser);
    if (r != 1) {
      throw MyAppException.getMyAppException("买家资金处理失败");
    }
    ubr = new TbUserBalanceRecord();
    ubr.setUid(buser.getUid());
    ubr.setBalance(userReward);
    ubr.setBdir(1);
    ubr.setBtype(16); // 交易奖励待释放币
    ubr.setInfo("交易奖励待释放币");
    r = tbUserBalanceRecordDAO.save(ubr);
    if (r != 1) {
      throw MyAppException.getMyAppException("买家资金变动记录失败");
    }
    // 买家的上级也要奖励
    TbUserInfo pUserInfo = new TbUserInfo();
    pUserInfo.setUid(buser.getUid());
    int uid = pUserInfo.getUid();
    pUserInfo = tbUserInfoDAO.queryParentByUid(pUserInfo);
    if (pUserInfo != null && pUserInfo.getUid() != uid) {
      BigDecimal parentReward = record.getBalance().multiply(siteConfig.getParentReward()).setScale(2, BigDecimal.ROUND_FLOOR);
      BigDecimal parentPayReward = record.getBalance().multiply(siteConfig.getParentPayReward().setScale(2, BigDecimal.ROUND_FLOOR));
      // 待返奖励
      pUserInfo.setPaybalance(pUserInfo.getPaybalance().add(parentReward));
      // 资金奖励
      pUserInfo.setBalance(pUserInfo.getBalance().add(parentPayReward));
      r = tbUserInfoDAO.modifyBalance(pUserInfo);
      if (r != 1) {
        throw MyAppException.getMyAppException("上级资金处理失败");
      }
      ubr = new TbUserBalanceRecord();
      ubr.setUid(pUserInfo.getUid());
      ubr.setBalance(parentReward);
      ubr.setBdir(1);
      ubr.setBtype(16); // 交易奖励待释放币
      ubr.setInfo("交易奖励待释放币");
      r = tbUserBalanceRecordDAO.save(ubr);
      if (r != 1) {
        throw MyAppException.getMyAppException("上级资金变动记录失败");
      }
      ubr = new TbUserBalanceRecord();
      ubr.setUid(pUserInfo.getUid());
      ubr.setBalance(parentPayReward);
      ubr.setBdir(1);
      ubr.setBtype(22); // 交易奖励待币
      ubr.setInfo("交易奖励");
      r = tbUserBalanceRecordDAO.save(ubr);
      if (r != 1) {
        throw MyAppException.getMyAppException("上级资金变动记录失败");
      }
      // 二级奖励处理
      uid = pUserInfo.getUid();
      pUserInfo = tbUserInfoDAO.queryParentByUid(pUserInfo);
      if (pUserInfo != null && pUserInfo.getUid() != uid) {
        BigDecimal parentReward2 = record.getBalance().multiply(siteConfig.getParentReward2()).setScale(2, BigDecimal.ROUND_FLOOR);
        // 待返奖励
        pUserInfo.setPaybalance(pUserInfo.getPaybalance().add(parentReward2));
        r = tbUserInfoDAO.modifyBalance(pUserInfo);
        if (r != 1) {
          throw MyAppException.getMyAppException(" 二级资金处理失败");
        }
        ubr = new TbUserBalanceRecord();
        ubr.setUid(pUserInfo.getUid());
        ubr.setBalance(parentReward2);
        ubr.setBdir(1);
        ubr.setBtype(23); // 交易二级奖励待释放币
        ubr.setInfo("交易二级奖励待释放币");
        r = tbUserBalanceRecordDAO.save(ubr);
        if (r != 1) {
          throw MyAppException.getMyAppException("二级资金变动记录失败");
        }
        // 三级奖励处理
        uid = pUserInfo.getUid();
        pUserInfo = tbUserInfoDAO.queryParentByUid(pUserInfo);
        if (pUserInfo != null && pUserInfo.getUid() != uid) {
          BigDecimal parentReward3 = record.getBalance().multiply(siteConfig.getParentReward3()).setScale(2, BigDecimal.ROUND_FLOOR);
          // 待返奖励
          pUserInfo.setPaybalance(pUserInfo.getPaybalance().add(parentReward3));
          r = tbUserInfoDAO.modifyBalance(pUserInfo);
          if (r != 1) {
            throw MyAppException.getMyAppException(" 三级资金处理失败");
          }
          ubr = new TbUserBalanceRecord();
          ubr.setUid(pUserInfo.getUid());
          ubr.setBalance(parentReward3);
          ubr.setBdir(1);
          ubr.setBtype(24); // 交易三级奖励待释放币
          ubr.setInfo("交易三级奖励待释放币");
          r = tbUserBalanceRecordDAO.save(ubr);
          if (r != 1) {
            throw MyAppException.getMyAppException("三级资金变动记录失败");
          }
        }
      }
    }

    record.setBstatus("05"); // 交易完成
    r = tbBalanceRecordDAO.modifyStatus(record);
    if (r != 1) {
      throw MyAppException.getMyAppException("交易状态变动异常，挂单失败");
    }
    return JsonMessage.getSuccessMessage("交易完成。");
  }

  @Override
  @Transactional(isolation = Isolation.SERIALIZABLE)
  public JsonMessage recast(UserBalanceRecordModel model) throws Exception {
    // 复投
    // 校验支付密码
    TbUser user = tbUserDAO.queryByKey(model.getLoginUser());
    if (!user.getPaypwd().equals(model.getPaypwd())) {
      return JsonMessage.getFailMessage("支付密码不正确！");
    }
    // 校验提交的资金是否为合法数量 且余额是否足够
    BigDecimal balance = model.getRecord().getBalance();
    if (balance == null || BigDecimal.ZERO.compareTo(balance) >= 0) {
      return JsonMessage.getFailMessage("复投资金必须大于0！");
    }
    TbUserInfo userInfo = new TbUserInfo();
    userInfo.setUid(model.getLoginUser().getUid());
    userInfo = tbUserInfoDAO.queryByKey(userInfo);
    if (userInfo == null) {
      return JsonMessage.getFailMessage("用户资金信息不存在");
    }
    BigDecimal ubalance = userInfo.getBalance();
    if (ubalance == null || ubalance.compareTo(BigDecimal.ZERO) <= 0 || ubalance.compareTo(balance) < 0) {
      return JsonMessage.getFailMessage("用户资金不够，可用余额：" + ubalance);
    }
    // 获取配置信息（倍投比率和上级奖励）

    SiteConfig siteConfig = configService.querySiteConfig();
    BigDecimal recast = balance.multiply(siteConfig.getExchange()).setScale(2, BigDecimal.ROUND_FLOOR);// 复投比率
    BigDecimal freeze = balance.multiply(siteConfig.getExchangeReward()).setScale(2, BigDecimal.ROUND_FLOOR);// 上级奖励

    // 后面的代码执行失败都要抛出MyAppException异常
    // 修改用户资金，扣款并转换到待释放币中

    // 用戶扣款
    userInfo.setBalance(userInfo.getBalance().subtract(balance));
    userInfo.setPaybalance(userInfo.getPaybalance().add(recast));
    int r = tbUserInfoDAO.modifyBalance(userInfo);
    if (r != 1) {
      throw MyAppException.getMyAppException("扣款失败，交易取消。");
    }

    // 记录用户资金变动（两比，扣款和带释放）
    // 添加资金变动信息
    TbUserBalanceRecord ubr = new TbUserBalanceRecord();
    ubr.setUid(userInfo.getUid());
    ubr.setBalance(balance);
    ubr.setBdir(-1);
    ubr.setBtype(19); // 复投扣款
    ubr.setInfo("复投扣款");
    r = tbUserBalanceRecordDAO.save(ubr);
    if (r != 1) {
      throw MyAppException.getMyAppException("用户资金变动记录失败");
    }
    ubr = new TbUserBalanceRecord();
    ubr.setUid(userInfo.getUid());
    ubr.setBalance(recast);
    ubr.setBdir(1);
    ubr.setBtype(20); // 复投收待释放币
    ubr.setInfo("复投收待释放币");
    r = tbUserBalanceRecordDAO.save(ubr);
    if (r != 1) {
      throw MyAppException.getMyAppException("用户资金变动记录失败");
    }
    // 上级奖励待释放币
    TbUserInfo pUserInfo = new TbUserInfo();
    pUserInfo.setUid(userInfo.getUid());
    int uid = pUserInfo.getUid();
    pUserInfo = tbUserInfoDAO.queryParentByUid(pUserInfo);
    if (pUserInfo != null && pUserInfo.getUid() != uid) {
      pUserInfo.setPaybalance(pUserInfo.getPaybalance().add(freeze));
      r = tbUserInfoDAO.modifyBalance(pUserInfo);
      if (r != 1) {
        throw MyAppException.getMyAppException("复投上级处理失败");
      }
      // 记录上级用户资金变动
      ubr = new TbUserBalanceRecord();
      ubr.setUid(pUserInfo.getUid());
      ubr.setBalance(freeze);
      ubr.setBdir(1);
      ubr.setBtype(21); // 复投上级奖励
      ubr.setInfo("复投上级奖励");
      r = tbUserBalanceRecordDAO.save(ubr);
      if (r != 1) {
        throw MyAppException.getMyAppException("复投上级资金变动记录失败");
      }
      // 二级奖励
      uid = pUserInfo.getUid();
      pUserInfo = tbUserInfoDAO.queryParentByUid(pUserInfo);
      if (pUserInfo != null && pUserInfo.getUid() != uid) {
        freeze = balance.multiply(siteConfig.getExchangeReward2()).setScale(2, BigDecimal.ROUND_FLOOR);// 二级奖励
        pUserInfo.setPaybalance(pUserInfo.getPaybalance().add(freeze));
        r = tbUserInfoDAO.modifyBalance(pUserInfo);
        if (r != 1) {
          throw MyAppException.getMyAppException("复投二级处理失败");
        }
        // 记录上级用户资金变动
        ubr = new TbUserBalanceRecord();
        ubr.setUid(pUserInfo.getUid());
        ubr.setBalance(freeze);
        ubr.setBdir(1);
        ubr.setBtype(25); // 复投二级奖励
        ubr.setInfo("复投二级奖励");
        r = tbUserBalanceRecordDAO.save(ubr);
        if (r != 1) {
          throw MyAppException.getMyAppException("复投二级资金变动记录失败");
        }
        // 三级奖励
        uid = pUserInfo.getUid();
        pUserInfo = tbUserInfoDAO.queryParentByUid(pUserInfo);
        if (pUserInfo != null && pUserInfo.getUid() != uid) {
          freeze = balance.multiply(siteConfig.getExchangeReward3()).setScale(2, BigDecimal.ROUND_FLOOR);// 二级奖励
          pUserInfo.setPaybalance(pUserInfo.getPaybalance().add(freeze));
          r = tbUserInfoDAO.modifyBalance(pUserInfo);
          if (r != 1) {
            throw MyAppException.getMyAppException("复投三级处理失败");
          }
          // 记录上级用户资金变动
          ubr = new TbUserBalanceRecord();
          ubr.setUid(pUserInfo.getUid());
          ubr.setBalance(freeze);
          ubr.setBdir(1);
          ubr.setBtype(26); // 复投三级奖励
          ubr.setInfo("复投三级奖励");
          r = tbUserBalanceRecordDAO.save(ubr);
          if (r != 1) {
            throw MyAppException.getMyAppException("复投三级资金变动记录失败");
          }
        }
      }
    }
    return JsonMessage.getSuccessMessage("复投成功！");
  }

  @Override
  public JsonMessage queryUserNowOrder(UserBalanceRecordModel model) throws Exception {
    TbBalanceRecord record = tbBalanceRecordDAO.queryUserRecord(model.getLoginUser());
    JsonMessage message = JsonMessage.getSuccessMessage("");
    message.putData("record", record);
    if (record == null) {
      return message;
    }
    // 处理联系信息
    if (record.getAuid() != null && record.getAuid() == model.getLoginUser().getUid()) {
      record.setAuser(null);
      message.putData("contact", record.getBuser());
      record.setBuser(null);
    }
    if (record.getBuid() != null && record.getBuid() == model.getLoginUser().getUid()) {
      record.setBuser(null);
      message.putData("contact", record.getAuser());
      record.setAuser(null);
    }
    return message;
  }

  @Override
  @Transactional(isolation = Isolation.SERIALIZABLE)
  public JsonMessage sendToUser(UserBalanceRecordModel model) throws Exception {

    if (configService.checkFreezeHour()) {
      return JsonMessage.getFailMessage("当前时间段无法交易");
    }
    // 校验资金输入
    BigDecimal balance = model.getRecord().getBalance();
    if (balance == null || BigDecimal.ZERO.compareTo(balance) >= 0) {
      return JsonMessage.getFailMessage("转账金额必须输入且大于零");
    }
    BigDecimal price = model.getRecord().getPrice();
    if (price == null || BigDecimal.ZERO.compareTo(price) >= 0) {
      return JsonMessage.getFailMessage("售价必须输入且大于零");
    }

    TbUser tbUser = tbUserDAO.queryByKey(model.getLoginUser());
    if (!tbUser.getPaypwd().equals(model.getPaypwd())) {
      return JsonMessage.getFailMessage("支付密码不正确！");
    }
    TbUser cuser = tbUserDAO.queryByKey(model.getLoginUser());
    if (!cuser.getPaypwd().equals(model.getPaypwd())) {
      return JsonMessage.getFailMessage("支付密码不正确！");
    }

    // 查询用户资金信息
    TbUserInfo userInfo = new TbUserInfo();
    userInfo.setUid(model.getLoginUser().getUid());
    userInfo = tbUserInfoDAO.queryByKey(userInfo);
    if (userInfo == null) {
      return JsonMessage.getFailMessage("用户资金信息不存在");
    }
    if (StringConverter.isEmpty(userInfo.getIdCard())) {
      return JsonMessage.getFailMessage("必须先实名认证才能交易");
    }
    if (!"y".equals(userInfo.getVip())) {
      return JsonMessage.getFailMessage("会员才能交易");
    }
    TbBalanceRecord record = model.getRecord();
    // 点对点转账===================================================================
    if (record.getAuser() == null || StringConverter.isEmpty(record.getAuser().getTokenId())) {
      return JsonMessage.getFailMessage("必须填写要转账的用户信息");
    }
    TbUser user = tbUserInfoDAO.queryUserInfoByTokenId(model.getRecord().getAuser());
    if (user == null) {
      return JsonMessage.getFailMessage("转账的用户信息不存在");
    }
    TbUserInfo buyer = user.getUserInfo();
    LOG.debug("买家信息：" + buyer);
    if (buyer.getUid() == model.getLoginUser().getUid()) {
      return JsonMessage.getFailMessage("不能转给自己");
    }
    if (StringConverter.isEmpty(buyer.getIdCard())) {
      return JsonMessage.getFailMessage("对方没有实名认证");
    }
    // 获取配置信息
    SiteConfig siteConfig = configService.querySiteConfig();
    BigDecimal freeze = record.getBalance().multiply(siteConfig.getPoundage()).setScale(2, BigDecimal.ROUND_FLOOR);// 需要扣除手续费
    freeze = freeze.add(record.getBalance());
    // 检查用户资金
    TbUserInfo seller = new TbUserInfo();
    seller.setUid(model.getLoginUser().getUid());
    seller = tbUserInfoDAO.queryByKey(seller);
    if (seller == null) {
      return JsonMessage.getFailMessage("用户资金信息不存在");
    }
    if (seller.getBalance().compareTo(freeze) < 0) {
      return JsonMessage.getFailMessage(String.format("用户资金为%s,交易需要资金%s，无法发起交易", seller.getBalance(), freeze));
    }
    // 用戶扣款
    seller.setBalance(seller.getBalance().subtract(freeze));
    int r = tbUserInfoDAO.modifyBalance(seller);
    if (r != 1) {
      throw MyAppException.getMyAppException("扣款失败，交易取消。");
    }
    // 添加资金变动信息
    TbUserBalanceRecord ubr = new TbUserBalanceRecord();
    ubr.setUid(seller.getUid());
    ubr.setBalance(freeze);
    ubr.setBdir(-1);
    ubr.setBtype(14); // 点对点卖出
    ubr.setInfo("转账卖出");
    r = tbUserBalanceRecordDAO.save(ubr);
    if (r != 1) {
      throw MyAppException.getMyAppException("卖家资金变动记录失败");
    }

    // 买家资金增加
    buyer.setBalance(buyer.getBalance().add(record.getBalance()));
    r = tbUserInfoDAO.modifyBalance(buyer);
    if (r != 1) {
      throw MyAppException.getMyAppException("转账失败，交易取消。");
    }
    ubr = new TbUserBalanceRecord();
    ubr.setUid(buyer.getUid());
    ubr.setBalance(record.getBalance());
    ubr.setBdir(1);
    ubr.setBtype(13); // 点对点买入
    ubr.setInfo("转账买入");
    r = tbUserBalanceRecordDAO.save(ubr);
    if (r != 1) {
      throw MyAppException.getMyAppException("卖家资金变动记录失败");
    }
    // 写入交易信息
    TbBalanceRecord sendToUserRecord = new TbBalanceRecord();
    sendToUserRecord.setAuid(seller.getUid()); // 卖出方为自己
    sendToUserRecord.setBuid(buyer.getUid()); // 买入方为提交的用户
    sendToUserRecord.setBalance(record.getBalance()); // 交易金额
    sendToUserRecord.setPrice(record.getPrice()); // 交易价格
    sendToUserRecord.setInfo("点对点交易");
    r = tbBalanceRecordDAO.addSendToUserRecord(sendToUserRecord);
    if (r != 1) {
      throw MyAppException.getMyAppException("添加交易信息失败，交易取消。");
    }
    // 买家要奖励待释放币
    BigDecimal userReward = record.getBalance().multiply(siteConfig.getUserReward()).setScale(2, BigDecimal.ROUND_FLOOR);
    buyer.setPaybalance(buyer.getPaybalance().add(userReward));
    r = tbUserInfoDAO.modifyBalance(buyer);
    if (r != 1) {
      throw MyAppException.getMyAppException("买家资金处理失败");
    }
    ubr = new TbUserBalanceRecord();
    ubr.setUid(buyer.getUid());
    ubr.setBalance(userReward);
    ubr.setBdir(1);
    ubr.setBtype(16); // 交易奖励待释放币
    ubr.setInfo("交易奖励待释放币");
    r = tbUserBalanceRecordDAO.save(ubr);
    if (r != 1) {
      throw MyAppException.getMyAppException("买家资金变动记录失败");
    }
    // 买家的上级也要奖励
    TbUserInfo pUserInfo = new TbUserInfo();
    pUserInfo.setUid(buyer.getUid());
    int uid = pUserInfo.getUid();
    pUserInfo = tbUserInfoDAO.queryParentByUid(pUserInfo);
    if (pUserInfo != null && pUserInfo.getUid() != uid) {
      BigDecimal parentReward = record.getBalance().multiply(siteConfig.getParentReward()).setScale(2, BigDecimal.ROUND_FLOOR);
      pUserInfo.setPaybalance(pUserInfo.getPaybalance().add(parentReward));
      r = tbUserInfoDAO.modifyBalance(pUserInfo);
      if (r != 1) {
        throw MyAppException.getMyAppException("上级资金处理失败");
      }
      ubr = new TbUserBalanceRecord();
      ubr.setUid(pUserInfo.getUid());
      ubr.setBalance(parentReward);
      ubr.setBdir(1);
      ubr.setBtype(16); // 交易奖励待释放币
      ubr.setInfo("交易奖励待释放币");
      r = tbUserBalanceRecordDAO.save(ubr);
      if (r != 1) {
        throw MyAppException.getMyAppException("上级资金变动记录失败");
      }
    }
    return JsonMessage.getSuccessMessage("转账成功。");
  }

  @Override
  public JsonMessage queryBuys(UserBalanceRecordModel model) throws Exception {
    PageBean page = model.getPage();
    PageHelper.startPage(page.getPageNumber(), page.getPageSize());
    Page<TbBalanceRecord> list = (Page<TbBalanceRecord>) tbBalanceRecordDAO.queryBuys();
    page.setPageInfo(list);
    JsonMessage message = JsonMessage.getSuccessMessage("查询成功！");
    message.putData("list", list);
    message.putData("page", page);
    return message;
  }

  @Override
  public JsonMessage queryUserOrders(UserBalanceRecordModel model) throws Exception {
    PageBean page = model.getPage();
    PageHelper.startPage(page.getPageNumber(), page.getPageSize());
    Page<TbBalanceRecord> list = (Page<TbBalanceRecord>) tbBalanceRecordDAO.queryUserOrders(model.getLoginUser());
    page.setPageInfo(list);
    JsonMessage message = JsonMessage.getSuccessMessage("查询成功！");
    message.putData("list", list);
    message.putData("page", page);
    return message;
  }

  @Override
  public JsonMessage queryBstatus(UserBalanceRecordModel model) throws Exception {
    PageBean page = model.getPage();
    PageHelper.startPage(page.getPageNumber(), page.getPageSize());
    Page<TbBalanceRecord> querybstatus = (Page<TbBalanceRecord>) tbBalanceRecordDAO.querybstatus();
    page.setPageInfo(querybstatus);
    JsonMessage message = JsonMessage.getSuccessMessage("查询成功！");
    message.putData("list", querybstatus);
    message.putData("page", page);
    return message;
  }

  @Override
  public JsonMessage queryBstatusmyself(UserBalanceRecordModel model) throws Exception {
    model.getRecord().setAuid(model.getLoginUser().getUid());
    PageBean page = model.getPage();
    PageHelper.startPage(page.getPageNumber(), page.getPageSize());
    Page<TbBalanceRecord> querybstatusmyself = (Page<TbBalanceRecord>) tbBalanceRecordDAO.querybstatucmyself(model.getRecord());
    page.setPageInfo(querybstatusmyself);
    JsonMessage message = JsonMessage.getSuccessMessage("查询用户信息成功！");
    message.putData("list", querybstatusmyself);
    message.putData("page", page);
    return message;
  }

}
