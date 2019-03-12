package top.blockchain.service;

import java.util.List;

import top.blockchain.entity.TbAdmin;
import top.blockchain.model.admin.AdminModel;
import top.blockchain.util.JsonMessage;

public interface AdminService {
  public List<TbAdmin> query() throws Exception;

  public int delete(TbAdmin admin) throws Exception;

  public int modify(TbAdmin admin) throws Exception;

  /***
   * 后台用户登录
   * 
   * @param model
   * @throws Exception
   */
  public JsonMessage login(AdminModel model) throws Exception;

  /***
   * 管理用户注册
   * 
   * @param model
   * @throws Exception
   */

  public int add(AdminModel model) throws Exception;

  JsonMessage updatePassword(AdminModel model) throws Exception;
}
