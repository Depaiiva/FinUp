package br.com.finup.category.dto;

import jakarta.validation.constraints.NotBlank;

public record RequestCategory(
    @NotBlank(message = "Category name cannot be blank") String name,
    String description) {
}
