package guru.bonacci.heroesadmin.service;

import static guru.bonacci.heroesadmin.TestData.fooUser;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import guru.bonacci.heroesadmin.TestData;
@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import({UserService.class, AdminService.class, PoolService.class, AccountService.class})
class PoolServiceTest {

  @Autowired private TestEntityManager entityManager;

  @Autowired private UserService userService;
  @Autowired private AdminService adminService;
  @Autowired private PoolService poolService;

  
  @Test
  void injectedServicesAreNotNull() {
    assertThat(userService).isNotNull();
    assertThat(adminService).isNotNull();
    assertThat(poolService).isNotNull();
  }

  Long userId;
  Long adminId;
  Long poolId;
  
  @BeforeEach
  void init() {
    this.userId = userService.createUser(fooUser()).getId();
    entityManager.clear();
    this.adminId = adminService.createAdmin(userId, "some details").get().getId();
    entityManager.clear();
    this.poolId = poolService.createPool(adminId, TestData.fooPool()).get().getId();
  }
  
  @Test
  void shouldWork() {
    assertThat(poolService.allPoolNames().get(0)).isEqualTo("coro");
    entityManager.clear();
    
    assertThat(poolService.getPoolSize(poolId)).isEqualTo(0);

    poolService.deactivate(poolId);
    entityManager.clear();
    assertThat(poolService.allPoolNames()).isEmpty();
    entityManager.clear();
    assertThat(poolService.getPool(poolId)).isEmpty();
  }
}