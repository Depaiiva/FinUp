package br.com.finup.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {

  @Id
  private UUID id;

  @Column(name = "account_id", nullable = false)
  private UUID AccountId;

  @Column(name = "category_id")
  private UUID categoryId;

  @Column(nullable = false)
  private BigDecimal amount;

  @Column(name = "posted_at", nullable = false)
  private LocalDateTime postedAt;

  @Column(nullable = false)
  private String type;

  private String description;

  @Column(name = "external_id", unique = true)
  private String externalId;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updateAt;
}
