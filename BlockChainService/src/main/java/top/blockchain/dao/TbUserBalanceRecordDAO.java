package top.blockchain.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import top.blockchain.entity.TbUser;
import top.blockchain.entity.TbUserBalanceRecord;

/**
 * TbUserBalanceRecord的数据访问接口类
 */
@Mapper
public interface TbUserBalanceRecordDAO {

  /**
   * 保存TbUserBalanceRecord数据
   * 
   * @param tbUserBalanceRecord
   *          实体数据
   * @throws Exception
   */
  public int save(TbUserBalanceRecord tbUserBalanceRecord) throws Exception;

  /**
   * 修改TbUserBalanceRecord数据
   * 
   * @param tbUserBalanceRecord
   *          实体数据
   * @throws Exception
   */
  public int update(TbUserBalanceRecord tbUserBalanceRecord) throws Exception;

  /**
   * 按照主键查询TbUserBalanceRecord数据
   * 
   * @param tbUserBalanceRecord
   *          带主键数据的实体数据
   * @return 主键查询结果，没有值返回null
   * @throws Exception
   */
  public TbUserBalanceRecord queryByKey(TbUserBalanceRecord tbUserBalanceRecord) throws Exception;

  /**
   * 查询全部的TbUserBalanceRecord数据
   * 
   * @return 全部的TbUserBalanceRecord数据
   * @throws Exception
   */
  public List<TbUserBalanceRecord> queryAll(TbUserBalanceRecord tbUserBalanceRecord) throws Exception;

  /**
   * 查询用户全部的TbUserBalanceRecord数据
   * 
   * @return 全部的TbUserBalanceRecord数据
   * @throws Exception
   */
  public List<TbUserBalanceRecord> queryUserAll(TbUser user) throws Exception;
}
