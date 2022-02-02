package com.ford.fbms.foe.uscwers.config;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

/**
 * A class for encapsulating external properties.
 *
 * @author SNITHY11 on 2/7/2021.
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "config-properties")
@Getter
@Setter
public class ConfigProperties {

  private List<String> excludedCountryCodes;
  private String vehicleIncentiveEndDate;

  private Map<String, List<String>> resourcePermissions;

  @NestedConfigurationProperty
  private TaskExecutorConfig taskExecuter;

  @NestedConfigurationProperty
  private RestServiceConfig restServiceConfig;

  private List<Long> testingFINs;
}
