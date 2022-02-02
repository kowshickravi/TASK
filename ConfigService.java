package com.ford.fbms.foe.uscwers.config;

import com.ford.fbms.foe.uscwers.datastore.RequestScopeDataStore;
import com.ford.fbms.foe.uscwers.executor.RequestScopeAwareExecutor;

import java.time.Duration;
import java.util.concurrent.Executor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

/**
 * A class for initializing configuration properties on startup.
 *
 * @author SNITHY11 on 2/7/2021.
 */
@Configuration
@EnableAsync
public class ConfigService {

  private final ConfigProperties configProperties;

  /**
   * To initialize ConfigService.
   *
   * @param configProperties {@link ConfigProperties}
   */
  @Autowired
  public ConfigService(final ConfigProperties configProperties) {
    this.configProperties = configProperties;
  }

  /**
   * Generating rest template with custom configurations.
   *
   * @return Configured RestTemplate
   */
  @Bean
  public RestTemplate configureRestTemplate() {
    return new RestTemplateBuilder()
            .setConnectTimeout(
                    Duration.ofMillis(configProperties.getRestServiceConfig().getConnectTimeout()))
            .setReadTimeout(Duration.ofMillis(configProperties.getRestServiceConfig().getReadTimeout()))
            .build();
  }

  /**
   * Configuring an executor to be used with async annotation, in order to provide the child threads
   * with scope attributes.
   *
   * @return executor of the type RequestScopeAwareExecutor
   */
  @Bean(name = "taskExecutor")
  public Executor getAsyncScopeAwareExecutor() {
    final RequestScopeAwareExecutor executor = new RequestScopeAwareExecutor();
    executor.setCorePoolSize(configProperties.getTaskExecuter().getCorePoolSize());
    executor.initialize();
    return executor;
  }

  /**
   * Configuring data store bean and set its scope to be request scope.
   *
   * @return data store bean which its type is RequestScopeDataStore
   */
  @Bean
  @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
  public RequestScopeDataStore requestScopedCapDataStore() {
    return new RequestScopeDataStore();
  }

}
