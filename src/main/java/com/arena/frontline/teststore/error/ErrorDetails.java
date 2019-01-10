package com.arena.frontline.teststore.error;

import java.time.LocalDateTime;

public abstract class ErrorDetails {

  private LocalDateTime timestamp;
  private int httpStatus;
  private String statusName;
  private String exception;
  private String path;

  public ErrorDetails() {
  }

  ErrorDetails(LocalDateTime timestamp, int httpStatus, String statusName,
      String exception, String path) {
    this.timestamp = timestamp;
    this.httpStatus = httpStatus;
    this.statusName = statusName;
    this.exception = exception;
    this.path = path;
  }

  ErrorDetails(int httpStatus, String statusName,
      String exception, String path) {
    this.timestamp = LocalDateTime.now();
    this.httpStatus = httpStatus;
    this.statusName = statusName;
    this.exception = exception;
    this.path = path;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public int getHttpStatus() {
    return httpStatus;
  }

  public void setHttpStatus(int httpStatus) {
    this.httpStatus = httpStatus;
  }

  public String getStatusName() {
    return statusName;
  }

  public void setStatusName(String statusName) {
    this.statusName = statusName;
  }

  public String getException() {
    return exception;
  }

  public void setException(String exception) {
    this.exception = exception;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }
}
