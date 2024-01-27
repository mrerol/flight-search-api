package com.fsa.application;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
@Order(1)
@WebFilter(filterName = "BasicAuthFilter")
public class BasicAuthFilter extends GenericFilterBean {

  private static final Logger loggingLogger = LoggerFactory.getLogger(BasicAuthFilter.class);
  private static final ResourceBundle resource = ResourceBundle.getBundle("application");

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
    try {
      HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
      HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
      String authorization = httpRequest.getHeader("Authorization");
      if (authorization == null || !authorization.startsWith("Basic ")) {
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return;
      }
      String[] credentials = decode(authorization);
      String username = credentials[0];
      String password = credentials[1];
      if (!resource.getString(username).equals(username)
          || !resource.getString(password).equals(password)) {
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return;
      }
      filterChain.doFilter(servletRequest, servletResponse);
    } catch (Exception e) {
      String errorMessage = String.format("Error in %s!", BasicAuthFilter.class);
      loggingLogger.error(errorMessage, e);
    }
  }

  private String[] decode(String auth) {
    String base64Credentials = auth.substring("Basic ".length());
    byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
    String credentials = new String(credDecoded, StandardCharsets.UTF_8);
    return credentials.split(":", 2);
  }
}
