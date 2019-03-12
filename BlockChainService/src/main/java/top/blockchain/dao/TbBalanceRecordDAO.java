package top.blockchain.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import top.blockchain.entity.TbBalanceRecord;
import top.blockchain.entity.TbUser;

/**
 * TbBalanceRecord的数据访问接口类
 */
@Mapper
public interface TbBalanceRecordDAO {

  /**
   * 按照主键查询TbBalanceRecord数据
   * 
   * @param tbBalanceRecord
   *          带主键数据的实体数据
   * @return 主键查询结果，没有值返回null
   * @throws Exception
   */
  public TbBalanceRecord queryByKey(TbBalanceRecord tbBalanceRecord) throws Exception;

  /**
   * 查询全部的TbBalanceRecord数据
   * 
   * @param record
   *          查询
   * @return 全部的TbBalanceRecord数据
   * @throws Exception
   */
  public List<TbBalanceRecord> queryAll(TbBalanceRecord record) throws Exception;

  /**
   * 查询用户是否存在正在交易的记录，只能有一笔记录
   * 
   * @param user
   *          用户
   * @return
   * @throws Exception
   */
  public TbBalanceRecord queryUserRecord(TbUser user) throws Exception;

  /**
   * 添加用户挂单信息
   * 
   * @param tbBalanceRecord
   * @return
   * @throws Exception
   */
  public int addUserRecord(TbBalanceRecord tbBalanceRecord) throws Exception;

  /**
   * 添加用户挂买单信息
   * 
   * @param tbBalanceRecord
   * @return
   * @throws Exception
   */
  public int addUserBuyRecord(TbBalanceRecord tbBalanceRecord) throws Exception;

  /**
   * 添加点对点交易信息
   * 
   * @param tbBalanceRecord
   * @return
   * @throws Exception
   */
  public int addSendToUserRecord(TbBalanceRecord tbBalanceRecord) throws Exception;

  /**
   * 修改挂单状态
   * 
   * @param tbBalanceRecord
   * @return
   * @throws Exception
   */
  public int modifyStatus(TbBalanceRecord tbBalanceRecord) throws Exception;

  /**
   * 查询挂买单的信息
   * 
   * @return
   * @throws Exception
   */
  public List<TbBalanceRecord> queryBuys() throws Exception;

  /**
   * 查询用户全部交易的信息
   * 
   * @param user
   * @return
   * @throws Exception
   */
  public List<TbBalanceRecord> queryUserOrders(TbUser user) throws Exception;

  /***
   * 查询查询所有交易中挂单信息
   * 
   * @param tbBalanceRecord
   * @return
   * @throws Exception
   */
  public List<TbBalanceRecord> querybstatus() throws Exception;

  /***
   * 查询用户自己当前挂单信息和历史挂单信息
   * 
   * @return
   * @throws Exception
   */
  public List<TbBalanceRecord> querybstatucmyself(TbBalanceRecord record) throws Exception;

  /**
   * 将超过times还没有交易的订单撤单
   * 
   * @param times
   * @return
   * @throws Exception
   */
  public int cancelTimeoutOrders(int times) throws Exception;

  /**
   * 冻结未完成交易的用户
   * @param times
   * @return
   * @throws Exception
   */
  public int freezeUser(int times) throws Exception;

  /**
   * 重置未完成交易的用户的冻结资金
   * @param times
   * @return
   * @throws Exception
   */
  public int resetFreezeUser(int times) throws Exception;

  /**
   * 将未完成交易标记为撤单
   * @param times
   * @return
   * @throws Exception
   */
  public int resetTimeoutOrders(int times) throws Exception;
}
