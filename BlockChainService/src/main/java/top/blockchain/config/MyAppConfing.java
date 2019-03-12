package top.blockchain.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import top.blockchain.filter.AppInterceptor;

@Configuration
public class MyAppConfing implements WebMvcConfigurer {

  private static final Logger LOG = LoggerFactory.getLogger(MyAppConfing.class);

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    LOG.debug("in MyAppConfing.addInterceptors:" + registry);
    registry.addInterceptor(new AppInterceptor()).addPathPatterns("/**");
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    WebMvcConfigurer.super.addCorsMappings(registry);
    registry.addMapping("/**").allowedMethods("*").allowedOrigins("*").allowedHeaders("*").allowCredentials(false);
  }
}
