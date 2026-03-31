package br.com.finup.finance.account.dto;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.finup.finance.account.TypeAccount;

public record ResponseAccount(
    String mensage,
    String name,
    BigDecimal balance,
    TypeAccount typeAccount,
    UUID id) {
}
