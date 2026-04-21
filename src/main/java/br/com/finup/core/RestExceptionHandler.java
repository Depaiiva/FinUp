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
  private ResponseEntity<String> userNotFound(UserNotFound exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
  }

  @ExceptionHandler(AccountNotFound.class)
  private ResponseEntity<String> accountNotFound(AccountNotFound exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
  }

  @ExceptionHandler(CategoryNotFound.class)
  private ResponseEntity<String> categoryNotFound(CategoryNotFound exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
  }
}
