package br.com.finup.account.dto;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.finup.account.TypeAccount;

public record ResponseAccount(
    String name,
    BigDecimal balance,
    TypeAccount typeAccount,
    UUID id) {
}
