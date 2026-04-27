package br.com.finup.core;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.finup.exceptions.AccountNotFound;
import br.com.finup.exceptions.CategoryNotFound;
import br.com.finup.exceptions.UserNotFound;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(UserNotFound.class)
  private ResponseEntity<RestErroMessage> userNotFound(UserNotFound exception) {
    RestErroMessage erroResponse = new RestErroMessage(exception.getMessage(), HttpStatus.NOT_FOUND,
        exception.getDetail());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroResponse);
  }

  @ExceptionHandler(AccountNotFound.class)
  private ResponseEntity<RestErroMessage> accountNotFound(AccountNotFound exception) {
    RestErroMessage erroResponse = new RestErroMessage(exception.getMessage(), HttpStatus.NOT_FOUND,
        exception.getDetail());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroResponse);
  }

  @ExceptionHandler(CategoryNotFound.class)
  private ResponseEntity<RestErroMessage> categoryNotFound(CategoryNotFound exception) {
    RestErroMessage erroResponse = new RestErroMessage(exception.getMessage(), HttpStatus.NOT_FOUND,
        exception.getDetail());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroResponse);
  }

}
