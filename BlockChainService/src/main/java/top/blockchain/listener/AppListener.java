package top.blockchain.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@WebListener
public class AppListener implements ServletContextListener, HttpSessionListener {

  private static final Logger log = LoggerFactory.getLogger(AppListener.class);

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    log.debug("程序启动,AppListener.contextInitialized");
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    log.debug("程序销毁,AppListener.contextDestroyed");
  }

  @Override
  public void sessionCreated(HttpSessionEvent se) {
    log.debug("Session创建,AppListener.sessionCreated:" + se.getSession().getId());
  }

  @Override
  public void sessionDestroyed(HttpSessionEvent se) {
    log.debug("Session销毁,AppListener.sessionDestroyed:" + se.getSession().getId());
  }

}
