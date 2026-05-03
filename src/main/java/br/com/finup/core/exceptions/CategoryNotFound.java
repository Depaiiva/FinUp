package br.com.finup.core.exceptions;

import lombok.Getter;

@Getter
public class CategoryNotFound extends RuntimeException {

  private String detail;

  public CategoryNotFound() {
    super("Category not found");
  }
}
