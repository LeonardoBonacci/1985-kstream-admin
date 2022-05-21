package guru.bonacci.heroesadmin;

import guru.bonacci.heroesadmin.domain.AccountDetails;
import guru.bonacci.heroesadmin.domain.Pool;
import guru.bonacci.heroesadmin.domain.UserInfo;

public class TestData {

  public static UserInfo fooUser() {
    return UserInfo.builder().name("foo").description("bar").build();
  }
  
  public static Pool fooPool() {
    return Pool.builder().type(PoolType.SARDEX).name("coro").build();
  }

  public static AccountDetails fooAccount() {
    return AccountDetails.builder().name("abcd").description("bla").build();
  }
}
