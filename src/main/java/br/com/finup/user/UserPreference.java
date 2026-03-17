package br.com.finup.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class UserPreference {

  @Column(name = "pref_theme")
  private String theme = "light";

  @Column(name = "pref_currency")
  private String currency = "BRL";

  @Column(name = "pref_notification_enabled")
  private boolean notificationEnabled = false;
}
