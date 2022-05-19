package guru.bonacci.heroesadmin.dto;

import guru.bonacci.heroesadmin.domain.AccountDetails;
import guru.bonacci.heroesadmin.domain.Pool;
import guru.bonacci.heroesadmin.domain.UserInfo;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Mappers {

  public AccountDetails mapAccount(AccountDto dto) {
    return AccountDetails.builder().name(dto.getName()).description(dto.getDescription()).build();
  }
  
  public UserInfo mapUser(UserDto dto) {
    return UserInfo.builder().name(dto.getName()).description(dto.getDescription()).build();
  }

  public Pool mapPool(PoolDto dto) {
    return Pool.builder().name(dto.getName()).poolType(dto.getPoolType()).build();
  }
}
