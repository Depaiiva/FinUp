package br.com.finup.auth.dto;

public record LoginResponse(String email, String password, String token) {
}
