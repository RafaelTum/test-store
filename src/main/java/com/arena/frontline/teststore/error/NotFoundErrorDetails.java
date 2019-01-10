package com.arena.frontline.teststore.error;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;

public class NotFoundErrorDetails extends ErrorDetails {

  public NotFoundErrorDetails() {
  }

  public NotFoundErrorDetails(LocalDateTime timestamp, String exception, String path) {
    super(timestamp, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(), exception, path);
  }

  public NotFoundErrorDetails(String exception, String path) {
    super(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(), exception, path);
  }
}
