package br.com.finup.finance.account;

import java.util.UUID;

import br.com.finup.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "accounts")
@Data
public class Account {

  @Id
  private UUID id;

  @Column(name = "user_id", nullable = false)
  private User UserId;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String type;

  private String currency;

  @Column(name = "external_id")
  private String externalId;
}
