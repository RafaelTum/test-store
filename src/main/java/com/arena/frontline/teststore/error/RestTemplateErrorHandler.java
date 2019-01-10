package com.arena.frontline.teststore.error;

import com.arena.frontline.teststore.error.exception.FrontLineApiErrorDetailsException;
import java.io.IOException;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class RestTemplateErrorHandler extends DefaultResponseErrorHandler {

  private HttpMessageConverter messageConverter;
  private static Logger logger = LoggerFactory.getLogger(RestTemplateErrorHandler.class);

  @Override
  public void handleError(ClientHttpResponse response) throws IOException {
    NotFoundErrorDetails error =
        (NotFoundErrorDetails) messageConverter.read(NotFoundErrorDetails.class, response);
    logger.debug(error.getException());
    throw new FrontLineApiErrorDetailsException(error);
  }

  @Autowired
  void setConverters(HttpMessageConverter<?>[] converters) {
    this.messageConverter = Arrays.stream(converters)
        .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
        .findAny()
        .orElse(null);
  }
}
