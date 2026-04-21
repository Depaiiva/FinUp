package br.com.finup.exceptions;

public class UserNotFound extends RuntimeException {

  public UserNotFound() {
    super("User not found");
  }
}
