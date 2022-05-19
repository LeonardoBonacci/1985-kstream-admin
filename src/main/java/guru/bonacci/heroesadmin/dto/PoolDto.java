package guru.bonacci.heroesadmin.dto;

import javax.validation.constraints.NotNull;

import guru.bonacci.heroesadmin.PoolType;
import lombok.Value;

@Value
public class PoolDto {

  @NotNull
  private String name;

  @NotNull
  private PoolType type;
}