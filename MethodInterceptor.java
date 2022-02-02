package com.ford.fbms.foe.uscwers.config;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * A class for allowing only required HTTP methods.
 *
 * @author SNITHY11 on 2/7/2021.
 */
public class MethodInterceptor extends HandlerInterceptorAdapter {
  private static final List<HttpMethod> httpMethodList = List.of(HttpMethod.HEAD,
      HttpMethod.OPTIONS, HttpMethod.TRACE, HttpMethod.PATCH);

  @Override
  public boolean preHandle(
      final HttpServletRequest request, final HttpServletResponse response, final Object handler)
      throws Exception {

    if (httpMethodList.stream().anyMatch(method -> method.matches(request.getMethod()))) {
      response.sendError(HttpStatus.METHOD_NOT_ALLOWED.value());
      return false;
    }
    return true;
  }
}