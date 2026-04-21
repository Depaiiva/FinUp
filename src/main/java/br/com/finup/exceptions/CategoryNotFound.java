package br.com.finup.exceptions;

public class CategoryNotFound extends RuntimeException {

  public CategoryNotFound() {
    super("Category not found");
  }
}
