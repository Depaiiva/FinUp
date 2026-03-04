package br.com.finup.user;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

  @Id
  private UUID id;

  private String name;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(name = "password_hash")
  private String password;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  private UserPreference preferences;
}
