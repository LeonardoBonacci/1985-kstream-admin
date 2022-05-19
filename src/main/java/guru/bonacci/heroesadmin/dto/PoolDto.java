package guru.bonacci.heroesadmin.dto;

import javax.validation.constraints.NotNull;

import lombok.Value;

@Value
public class PoolDto {

  @NotNull
  private String name;

  @NotNull // TODO https://www.baeldung.com/javax-validations-enums
  private String poolType;
}