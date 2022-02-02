package com.ford.fbms.foe.uscwers.config;


import com.ford.fbms.foe.uscwers.util.LoggerBuilder;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

/**
 * To verify ADFS token's 'aud' property against required endpoint's security permission.
 *
 * @author SNITHY11 on 2/7/2021.
 */
@Slf4j
@Component
public class AdfsPermissionEvaluator implements PermissionEvaluator {

  private final ConfigProperties configProperties;

  /**
   * Initializing ADFSPermissionEvaluator.
   *
   * @param configProperties {@link ConfigProperties}
   */
  @Autowired
  public AdfsPermissionEvaluator(final ConfigProperties configProperties) {
    this.configProperties = configProperties;
  }

  /**
   * Does the given permission matches the expected?.
   *
   * @param auth               Auth token
   * @param targetDomainObject Field from the token to compare
   * @param permission         Given resource value
   * @return TRUE if matches else FALSE
   */
  @Override
  public boolean hasPermission(final Authentication auth, final Object targetDomainObject,
                               final Object permission) {
    return isGivenMatchingExpectedResource(auth, targetDomainObject, permission);
  }

  @Override
  public boolean hasPermission(final Authentication auth, final Serializable targetId,
                               final String targetType, final Object permission) {
    return isGivenMatchingExpectedResource(auth, targetType, permission);
  }

  private boolean isGivenMatchingExpectedResource(final Authentication auth,
                                                  final Object targetDomainObject,
                                                  final Object permission) {
    try {
      final List<String> exptdRsrc = configProperties.getResourcePermissions().get(permission);
      final List<String> givenRsrc = (List<String>) ((JwtAuthenticationToken) auth)
          .getTokenAttributes()
          .get(targetDomainObject);
      return exptdRsrc.contains(givenRsrc.get(0));
    } catch (Exception e) {
      LoggerBuilder.printError(log, logger -> logger
          .message("Error matching expected resource for given resource").exception(e));
      throw new JwtValidationException("Error matching expected resource for given resource",
          Arrays.asList(new OAuth2Error("invalid_aud")));
    }
  }
}