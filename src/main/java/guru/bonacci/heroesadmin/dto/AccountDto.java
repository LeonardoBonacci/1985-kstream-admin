package guru.bonacci.heroesadmin.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Value;

@Value
public class AccountDto {

  @NotNull
  @Size(min = 4, max = 25)
  private String name;

  private String description;
}
