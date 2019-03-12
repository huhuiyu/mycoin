package top.blockchain.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import top.blockchain.entity.TbSiteNotice;

/**
 * TbSiteNotice的数据访问接口类
 */
@Mapper
public interface TbSiteNoticeDAO {

  /**
   * 保存TbSiteNotice数据
   * 
   * @param tbSiteNotice
   *          实体数据
   * @throws Exception
   */
  public int save(TbSiteNotice tbSiteNotice) throws Exception;

  /**
   * 修改TbSiteNotice数据
   * 
   * @param tbSiteNotice
   *          实体数据
   * @throws Exception
   */
  public int update(TbSiteNotice tbSiteNotice) throws Exception;

  /**
   * 删除TbSiteNotice数据
   * 
   * @param tbSiteNotice
   *          实体数据
   * @throws Exception
   */
  public int delete(TbSiteNotice tbSiteNotice) throws Exception;

  /**
   * 按照主键查询TbSiteNotice数据
   * 
   * @param tbSiteNotice
   *          带主键数据的实体数据
   * @return 主键查询结果，没有值返回null
   * @throws Exception
   */
  public TbSiteNotice queryByKey(TbSiteNotice tbSiteNotice) throws Exception;

  /**
   * 查询全部的TbSiteNotice数据
   * 
   * @return 全部的TbSiteNotice数据
   * @throws Exception
   */
  public List<TbSiteNotice> queryAll(TbSiteNotice tbSiteNotice) throws Exception;

  public List<TbSiteNotice> queryUserAll() throws Exception;

}
