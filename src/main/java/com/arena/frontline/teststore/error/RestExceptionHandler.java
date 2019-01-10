package com.arena.frontline.teststore.error;

import com.arena.frontline.teststore.error.exception.FrontLineApiErrorDetailsException;
import com.arena.frontline.teststore.error.exception.notfound.ItemNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ItemNotFoundException.class)
  public ResponseEntity<NotFoundErrorDetails> handleTestNotFoundException(
      final ItemNotFoundException e, WebRequest request) {
    NotFoundErrorDetails errorDetails = new NotFoundErrorDetails(e.getMessage(),
        request.getDescription(false));
    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(FrontLineApiErrorDetailsException.class)
  public ResponseEntity<ErrorDetails> handleQuestionsCountNotEnoughException(
      final FrontLineApiErrorDetailsException e) {

    return new ResponseEntity<>(e.getErrorDetails(), HttpStatus.NOT_FOUND);
  }

}
