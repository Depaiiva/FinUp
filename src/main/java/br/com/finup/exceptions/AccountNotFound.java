package br.com.finup.exceptions;

public class AccountNotFound extends RuntimeException {

  public AccountNotFound() {
    super("Account not found");
  }
}
