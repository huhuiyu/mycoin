package top.blockchain.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import top.blockchain.entity.TbPayBalanceUpdate;

/**
 * TbPayBalanceUpdate的数据访问接口类
 */
@Mapper
public interface TbPayBalanceUpdateDAO {

  /**
   * 保存TbPayBalanceUpdate数据
   * 
   * @param tbPayBalanceUpdate
   *          实体数据
   * @throws Exception
   */
  public int save(TbPayBalanceUpdate tbPayBalanceUpdate) throws Exception;

  /**
   * 修改TbPayBalanceUpdate数据
   * 
   * @param tbPayBalanceUpdate
   *          实体数据
   * @throws Exception
   */
  public int update(TbPayBalanceUpdate tbPayBalanceUpdate) throws Exception;

  /**
   * 删除TbPayBalanceUpdate数据
   * 
   * @param tbPayBalanceUpdate
   *          实体数据
   * @throws Exception
   */
  public int delete(TbPayBalanceUpdate tbPayBalanceUpdate) throws Exception;

  /**
   * 按照主键查询TbPayBalanceUpdate数据
   * 
   * @param tbPayBalanceUpdate
   *          带主键数据的实体数据
   * @return 主键查询结果，没有值返回null
   * @throws Exception
   */
  public TbPayBalanceUpdate queryByKey(TbPayBalanceUpdate tbPayBalanceUpdate) throws Exception;

  /**
   * 查询全部的TbPayBalanceUpdate数据
   * 
   * @return 全部的TbPayBalanceUpdate数据
   * @throws Exception
   */
  public List<TbPayBalanceUpdate> queryAll(TbPayBalanceUpdate balanceUpdate) throws Exception;

}
