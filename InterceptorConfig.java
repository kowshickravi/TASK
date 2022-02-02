package com.ford.fbms.foe.uscwers.config;

import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * A Class for adding interceptor configuration details.
 *
 * @author SNITHY11 on 2/7/2021.
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

  /**
   * Allowed firewall http methods.
   *
   * @return {@link StrictHttpFirewall}
   */
  @Bean
  public StrictHttpFirewall httpFirewall() {
    final StrictHttpFirewall firewall = new StrictHttpFirewall();
    firewall.setAllowedHttpMethods(
        Arrays.asList("TRACE", "HEAD", "DELETE", "POST", "GET", "OPTIONS", "PATCH", "PUT"));
    return firewall;
  }

  @Override
  public void addInterceptors(final InterceptorRegistry registry) {
    registry.addInterceptor(new MethodInterceptor()).addPathPatterns("/**");
  }
}

