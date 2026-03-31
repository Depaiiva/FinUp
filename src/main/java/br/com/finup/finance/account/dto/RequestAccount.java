package br.com.finup.finance.account.dto;

import java.math.BigDecimal;

import br.com.finup.finance.account.TypeAccount;

public record RequestAccount(
    String name,
    TypeAccount type,
    BigDecimal balance) {
}
