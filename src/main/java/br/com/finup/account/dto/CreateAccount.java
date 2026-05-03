package br.com.finup.account.dto;

import br.com.finup.account.TypeAccount;
import jakarta.validation.constraints.NotEmpty;

public record CreateAccount(
    @NotEmpty String name,

    @NotEmpty TypeAccount type) {
}
