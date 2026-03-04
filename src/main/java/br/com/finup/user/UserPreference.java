package br.com.finup.user;

import lombok.Data;

@Data
public class UserPreference {

  private String theme;
  private String currency;
  private boolean notificationEnabled;
}
