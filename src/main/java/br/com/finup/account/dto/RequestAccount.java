package br.com.finup.account.dto;

import java.math.BigDecimal;

import br.com.finup.account.TypeAccount;

public record RequestAccount(
    String name,
    TypeAccount type,
    BigDecimal balance) {
}
