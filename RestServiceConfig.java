package com.ford.fbms.foe.uscwers.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * A class for rest-service configuration properties.
 *
 * @author SNITHY11 on 2/7/2021.
 */

//@Configuration
//@EnableConfigurationProperties
@ConfigurationProperties(prefix = "config-properties.rest-service-config")
@Getter
@Setter
public class RestServiceConfig {

  private int connectTimeout;
  private int readTimeout;
  private int backoffLow;
  private int backoffHigh;
  private int backoffMaxInterval;
  private int backoffAttemptNum;
  private String cpaLetterProposalDealInternalUrl;
  private String cpaLetterProposalDealExternalUrl;
  private String cpaLetterBuyerDealInternalUrl;
  private String cpaLetterBuyerDealExternalUrl;
  private String addVehicleLineInternalUrl;
  private String addVehicleLineExternalUrl;
  private String getVehicleLineInternalUrl;
  private String getVehicleLineExternalUrl;
  private String notesServiceInternal;
  private String notesServiceExternal;
  private String copyProposalInternalUrl;
  private String copyProposalExternalUrl;
  private String pdMarketInternalUrl;
  private String pdMarketExternalUrl;
  private String deleteProposalInternalUrl;
  private String deleteProposalExternalUrl;
  private String connectBuyerTemplateInternalUrl;
  private String connectBuyerTemplateExternalUrl;
}
