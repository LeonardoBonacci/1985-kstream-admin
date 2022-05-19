package guru.bonacci.heroesadmin.service;

import guru.bonacci.heroesadmin.domain.UserInfo;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestData {

  UserInfo fooUser() {
    return UserInfo.builder().name("foo").description("bar").build();
  }
}
