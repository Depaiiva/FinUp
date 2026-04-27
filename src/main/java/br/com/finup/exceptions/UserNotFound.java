package br.com.finup.exceptions;

import lombok.Getter;

@Getter
public class UserNotFound extends RuntimeException {

  private String detail;

  public UserNotFound() {
    super("User not found");
  }
}
