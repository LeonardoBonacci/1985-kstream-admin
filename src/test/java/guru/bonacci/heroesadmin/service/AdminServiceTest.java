package guru.bonacci.heroesadmin.service;

import static guru.bonacci.heroesadmin.service.TestData.fooUser;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import guru.bonacci.heroesadmin.domain.Pool;
import guru.bonacci.heroesadmin.domain.UserInfo;
@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import({UserService.class, AdminService.class, PoolService.class, AccountService.class})
class AdminServiceTest {

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

  UserInfo user;
  Long adminId;
  
  @BeforeEach
  void init() {
    this.user = userService.createUser(fooUser());
    entityManager.clear();
    this.adminId = adminService.createAdmin(user.getId(), "some details").get().getId();
    entityManager.clear();
  }
  
  @Test
  void crud() {
     var admin = adminService.getAdmin(adminId).get();
 
    assertThat(admin.getBankDetails()).isEqualTo("some details");
    assertThat(admin.getPools()).isEmpty();
    assertThat(admin.getUser().getName()).isEqualTo(user.getName());
    
    entityManager.clear();
    adminService.delete(adminId);
    
    entityManager.clear();
    assertThat(adminService.getAdmin(adminId)).isEmpty();
    assertThat(userService.getUser(user.getId())).isNotNull();
  }
  
  @Test
  void otherStuff() {
    var pool = poolService.createPool(adminId, Pool.builder().poolType("sardex").name("coro").build()).get();

    entityManager.clear();
    Assertions.assertThrows(IllegalStateException.class,
        () -> adminService.delete(adminId));
    

    assertThat(adminService.getAdminByPoolId(pool.getId())).isPresent();
    
    poolService.deactivate(pool.getId());
    entityManager.clear();
    adminService.delete(adminId);
    entityManager.clear();

    assertThat(adminService.getAdmin(adminId)).isEmpty();
  }
}