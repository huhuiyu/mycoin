package top.blockchain.filter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import top.blockchain.controller.NotNeedLogin;
import top.blockchain.entity.TbToken;
import top.blockchain.entity.TbUser;
import top.blockchain.exception.MyAppException;
import top.blockchain.model.base.AdminBaseModel;
import top.blockchain.model.base.BaseModel;
import top.blockchain.model.base.UserBaseModel;
import top.blockchain.service.ConfigService;
import top.blockchain.service.TokenInfoService;
import top.blockchain.service.TokenService;
import top.blockchain.util.JsonMessage;

@Aspect
@Component
public class ControllerToken {

  private static final Logger LOG = LoggerFactory.getLogger(ControllerToken.class);
  @Autowired
  private TokenService tokenService;
  @Autowired
  private TokenInfoService tokenInfoService;
  @Autowired
  private ConfigService configService;

  @Pointcut("execution(public * top.blockchain.controller..*.*(..))")
  public void token() {
  }

  @Around("token()")
  public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
    LOG.debug("控制器拦截处理：" + pjp.getTarget() + "-----" + pjp.getTarget().hashCode());
    // 用户登陆相关信息管理
    String token = null; // 令牌信息
    Object result = null;
    Object[] args = pjp.getArgs();
    int needUser = 0; // 是否需要应答user信息，0：不需要，1：前台，2：后台
    UserBaseModel usermodel;
    AdminBaseModel adminmodel;
    Object user = null; // 用户信息
    TbUser fuser = null; // 前台登陆用户信息

    StringBuilder strArgs = new StringBuilder();
    if (args != null && args.length > 0) {
      for (Object arg : args) {
        // 处理token信息=============================================================
        if (arg != null && arg instanceof BaseModel) {
          strArgs.append(arg).append("\r\n");
          BaseModel model = (BaseModel) arg;
          token = model.getServertoken();
          // 校验和保持服务器token
          TbToken tbToken = tokenService.saveOrUpdate(token);
          model.setServertoken(tbToken.getToken());
          token = tbToken.getToken();
        }
        // 处理前台登陆用户信息=======================================================
        if (arg != null && arg instanceof UserBaseModel) {
          // 前端需要检测是否关闭网站
          if ("y".equals(configService.querySiteConfig().getClosed())) {
            JsonMessage jsonMessage = JsonMessage.getJsonMessage(2000, false, "网站维护", null);
            return jsonMessage;
          }
          usermodel = (UserBaseModel) arg;
          usermodel.setLoginUser(tokenInfoService.loadUser(usermodel.getServertoken()));
          user = usermodel.getLoginUser();
          fuser = usermodel.getLoginUser();
          needUser = 1;
        }
        // 处理后台登陆用户信息=======================================================
        if (arg != null && arg instanceof AdminBaseModel) {
          adminmodel = (AdminBaseModel) arg;
          adminmodel.setLoginUser(tokenInfoService.loadAdminUser(adminmodel.getServertoken()));
          user = adminmodel.getLoginUser();
          needUser = 2;
        }
      }
    }
    LOG.debug("请求的参数：" + strArgs);
    // 用户权限拦截处理
    boolean baselogincheck = !(pjp.getTarget() instanceof NotNeedLogin);
    LOG.debug("登陆拦截信息：" + baselogincheck + "," + user);
    if (baselogincheck && user == null) {
      throw MyAppException.getMyAppException("必须登陆。。。", 1000);
    }
    if (baselogincheck && needUser == 1 && fuser != null && !"y".equals(fuser.getIsEnable())) {
      throw MyAppException.getMyAppException("账号已经冻结，请联系客服", 1001);
    }
    result = pjp.proceed();
    // 处理令牌环和用户信息
    if (result != null && result instanceof JsonMessage) {
      JsonMessage message = (JsonMessage) result;
      message.setServertoken(token);
      if (needUser == 1) {
        message.setLoginUser(tokenInfoService.loadUser(token));
        message.putData("siteBaseConfig", configService.queryForntSiteConfig()); // 添加站点配置
      }
      if (needUser == 2) {
        message.setLoginUser(tokenInfoService.loadAdminUser(token));
      }
    }
    return result;
  }
}
