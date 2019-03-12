package top.blockchain.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;

//@Component
//@WebFilter(urlPatterns = "/*", filterName = "AppFilter")
public class AppFilter implements Filter {
  private static final Logger log = LoggerFactory.getLogger(AppFilter.class);

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    log.debug("AppFilter.init");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    log.debug("AppFilter.doFilter path:" + req.getRequestURI());
    log.debug("AppFilter.doFilter remote address:" + req.getRemoteAddr());
    log.debug("AppFilter.doFilter session:" + req.getSession().getId());
    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {
    log.debug("AppFilter.destroy");
  }

}
