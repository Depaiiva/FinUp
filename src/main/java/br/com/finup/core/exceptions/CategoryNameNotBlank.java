package br.com.finup.core.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CategoryNameNotBlank extends RuntimeException {

  private final HttpStatus status = HttpStatus.BAD_REQUEST;

  public CategoryNameNotBlank() {
    super("Name can't not blank");
  }
}
