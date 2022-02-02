package com.ford.fbms.foe.uscwers.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * A class for task executor's child threads configurations.
 *
 * @author SNITHY11 on 2/7/2021.
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "config-properties.task-executer")
public class TaskExecutorConfig {

  private int corePoolSize;
}
