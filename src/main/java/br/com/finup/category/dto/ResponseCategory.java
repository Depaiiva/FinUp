package br.com.finup.category.dto;

import java.util.UUID;

public record ResponseCategory(String name, String description, UUID id, UUID user) {

}
