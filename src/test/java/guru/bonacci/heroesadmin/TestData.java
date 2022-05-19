package guru.bonacci.heroesadmin;

import guru.bonacci.heroesadmin.domain.AccountDetails;
import guru.bonacci.heroesadmin.domain.Pool;
import guru.bonacci.heroesadmin.domain.UserInfo;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestData {

  public UserInfo fooUser() {
    return UserInfo.builder().name("foo").description("bar").build();
  }
  
  public Pool fooPool() {
    return Pool.builder().type(PoolType.SARDEX).name("coro").build();
  }

  public AccountDetails fooAccount() {
    return AccountDetails.builder().name("abcd").description("bla").build();
  }

}
