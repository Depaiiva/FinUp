package br.com.finup.exceptions;

import lombok.Getter;

@Getter
public class AccountNotFound extends RuntimeException {

  private String detail;

  public AccountNotFound() {
    super("Account not found");
  }
}
