package com.fsa.application;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Component
@Order(2)
@WebFilter(filterName = "LoggingFilter")
public class LoggingFilter extends GenericFilterBean {

  private static final Logger loggingLogger = LoggerFactory.getLogger(LoggingFilter.class);
  private static final String NEW_LINE = System.lineSeparator();

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
    try {
      HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
      HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
      if (httpResponse.getStatus() == HttpServletResponse.SC_UNAUTHORIZED) {
        return;
      }
      ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(httpRequest);
      ContentCachingResponseWrapper responseWrapper =
          new ContentCachingResponseWrapper(httpResponse);
      filterChain.doFilter(requestWrapper, responseWrapper);
      StringBuilder logMessage = new StringBuilder();
      logMessage
          .append(NEW_LINE)
          .append("Endpoint: ")
          .append(httpRequest.getRequestURI())
          .append(NEW_LINE);
      logMessage.append("Method: ").append(httpRequest.getMethod()).append(NEW_LINE);
      logMessage.append("Status: ").append(responseWrapper.getStatus()).append(NEW_LINE);
      logMessage.append("Request Headers: ").append(getHeaderMap(httpRequest)).append(NEW_LINE);
      String requestBody =
          clearEOL(
              new String(
                  requestWrapper.getContentAsByteArray(), requestWrapper.getCharacterEncoding()));
      logMessage.append("Request Body: ").append(requestBody).append(NEW_LINE);
      String responseBody =
          clearEOL(
              new String(
                  responseWrapper.getContentAsByteArray(), responseWrapper.getCharacterEncoding()));
      logMessage.append("Response Body: ").append(responseBody);
      String logMessageString = logMessage.toString();
      loggingLogger.info(logMessageString);
      responseWrapper.copyBodyToResponse();
    } catch (Exception e) {
      String errorMessage = String.format("Error in %s!", LoggingFilter.class);
      loggingLogger.error(errorMessage, e);
    }
  }

  private Map<String, String> getHeaderMap(HttpServletRequest httpRequest) {
    Enumeration<String> headers = httpRequest.getHeaderNames();
    Map<String, String> headersToHeaderValues = new HashMap<>();
    while (headers.hasMoreElements()) {
      String header = headers.nextElement();
      headersToHeaderValues.put(header, httpRequest.getHeader(header));
    }
    return headersToHeaderValues;
  }

  private String clearEOL(String text) {
    return text.replaceAll("[\r\n]+", " ");
  }
}
