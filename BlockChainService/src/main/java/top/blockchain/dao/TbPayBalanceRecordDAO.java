package top.blockchain.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import top.blockchain.entity.TbPayBalanceRecord;
import top.blockchain.entity.TbUserInfo;

/**
 * TbPayBalanceRecord的数据访问接口类
 */
@Mapper
public interface TbPayBalanceRecordDAO {
  
  /**
   * 保存TbPayBalanceRecord数据
   * 
   * @param tbPayBalanceRecord
   *            实体数据
   * @throws Exception
   */
  public int save(TbPayBalanceRecord tbPayBalanceRecord) throws Exception;
  
  /**
   * 修改TbPayBalanceRecord数据
   * 
   * @param tbPayBalanceRecord
   *            实体数据
   * @throws Exception
   */
  public int update(TbPayBalanceRecord tbPayBalanceRecord) throws Exception;
  
  /**
   * 删除TbPayBalanceRecord数据
   * 
   * @param tbPayBalanceRecord
   *            实体数据
   * @throws Exception
   */
  public int delete(TbPayBalanceRecord tbPayBalanceRecord) throws Exception;
  
  /**
   * 按照主键查询TbPayBalanceRecord数据
   * 
   * @param tbPayBalanceRecord
   *            带主键数据的实体数据
   * @return 主键查询结果，没有值返回null
   * @throws Exception
   */
  public TbPayBalanceRecord queryByKey(TbPayBalanceRecord tbPayBalanceRecord) throws Exception;
  
  /**
   * 查询全部的TbPayBalanceRecord数据
   * 
   * @return 全部的TbPayBalanceRecord数据
   * @throws Exception
   */
  public List<TbPayBalanceRecord> queryAll() throws Exception;
  
  /**
   * 更新用户资金
   * @param userInfo
   * @return
   * @throws Exception
   */
  public int updateUserBalance(TbUserInfo userInfo) throws Exception;

  /**
   * 按照主键查询用户
   * @param userInfo
   * @return
   * @throws Exception
   */
  public TbUserInfo queryUserByKey(TbUserInfo userInfo) throws Exception;
  
}
