package guru.bonacci.heroesadmin.dto;

import javax.validation.constraints.NotNull;

import lombok.Value;

@Value
public class AccountDto {

  @NotNull
  private String name;

  private String description;
}
