package br.com.finup.finance.account.dto;

import br.com.finup.finance.account.TypeAccount;
import jakarta.validation.constraints.NotEmpty;

public record CreateAccount(
    @NotEmpty String name,

    @NotEmpty TypeAccount type) {
}
