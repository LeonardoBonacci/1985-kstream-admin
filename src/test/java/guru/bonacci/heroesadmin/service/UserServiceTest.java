package guru.bonacci.heroesadmin.service;

import static guru.bonacci.heroesadmin.TestData.fooUser;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DirtiesContext
@Import({UserService.class, AdminService.class, PoolService.class, AccountService.class})
class UserServiceTest {

  @Autowired private TestEntityManager entityManager;

  @Autowired private UserService userService;

  @Test
  void injectedServicesAreNotNull() {
    assertThat(userService).isNotNull();
  }
  
  @Test
  void crud() {
    var user = userService.createUser(fooUser());
    assertThat(user).isNotNull();
    assertThat(user.getId()).isNotNull();
    assertThat(user.getAccounts()).isEmpty();
    
    entityManager.clear();
    user.setDescription("bar2");
    userService.updateUser(user);

    entityManager.clear();
    var user2 = userService.getUser(user.getId());
    assertThat(user2.get().getDescription()).isEqualTo("bar2");
  }
}