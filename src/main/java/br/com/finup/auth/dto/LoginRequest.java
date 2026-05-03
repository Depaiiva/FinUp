package br.com.finup.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
    @Email @NotBlank(message = "The email field cannot be empty.") String email,

    @Size(min = 8, message = "Password must be at least 8 characters long") String password) {
}
