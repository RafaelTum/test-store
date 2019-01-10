package com.arena.frontline.teststore.error.exception;

import com.arena.frontline.teststore.error.ErrorDetails;

public class FrontLineApiErrorDetailsException extends RuntimeException {

  private ErrorDetails errorDetails;

  public FrontLineApiErrorDetailsException(ErrorDetails errorDetails) {
    super(errorDetails.getException());
    this.errorDetails=errorDetails;
  }

  public ErrorDetails getErrorDetails() {
    return errorDetails;
  }

  public void setErrorDetails(ErrorDetails errorDetails) {
    this.errorDetails = errorDetails;
  }
}
