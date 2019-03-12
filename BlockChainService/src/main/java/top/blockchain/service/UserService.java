package top.blockchain.service;

import top.blockchain.entity.TbUser;
import top.blockchain.model.admin.ManageUserModel;
import top.blockchain.model.user.UserModel;
import top.blockchain.util.JsonMessage;

public interface UserService {

  public JsonMessage login(UserModel model) throws Exception;

  public void add(UserModel model) throws Exception;

  public int delete(TbUser user) throws Exception;

  public int modify(TbUser user) throws Exception;

  public JsonMessage foundPassword(UserModel model) throws Exception;

  public JsonMessage queryAll(ManageUserModel model) throws Exception;

  public int undelete(TbUser user) throws Exception;

  JsonMessage queryTeam(UserModel model) throws Exception;
}
