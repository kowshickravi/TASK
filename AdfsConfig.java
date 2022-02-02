package com.ford.fbms.foe.uscwers.config;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.cors.CorsConfiguration;

/**
 * ADFS security configurations.
 *
 * @author PSENTHIK on 3/2/2021.
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class AdfsConfig extends GlobalMethodSecurityConfiguration {

  private final AdfsPermissionEvaluator adfsPermissionEvaluator;

  /**
   * Initialize ADFS config with appropriate evaluator.
   *
   * @param adfsPermissionEvaluator {@link AdfsPermissionEvaluator}
   */
  @Autowired
  public AdfsConfig(final AdfsPermissionEvaluator adfsPermissionEvaluator) {
    super();
    this.adfsPermissionEvaluator = adfsPermissionEvaluator;
  }

  /*****************************************************************************************
   * Resource Server (handle incoming Bearer tokens).
   ****************************************************************************************/

  @Override
  protected MethodSecurityExpressionHandler createExpressionHandler() {
    final DefaultMethodSecurityExpressionHandler expressionHandler =
        new DefaultMethodSecurityExpressionHandler();
    expressionHandler.setPermissionEvaluator(adfsPermissionEvaluator);
    return expressionHandler;
  }

  /**
   * Resource server configs and jwtdecoder configs.
   */
  @Configuration
  @Order(10)
  public static class ResourceServerSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtDecoder jwtDecoder;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
      http
          .cors().configurationSource(request -> corsConfiguration())
          .and()
          .antMatcher("/usctowers/**")
          .authorizeRequests()
          .anyRequest().authenticated()
          .and()
          .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          .and()
          .oauth2ResourceServer()
          .jwt()
          .decoder(jwtDecoder);
    }
  }

  static CorsConfiguration corsConfiguration() {
    CorsConfiguration cors = new CorsConfiguration();
    cors.setAllowedOrigins(Arrays.asList("*"));
    cors.setAllowedMethods(Arrays.asList("*"));
    cors.setAllowedHeaders(Arrays.asList("*"));
    return cors;
  }
}