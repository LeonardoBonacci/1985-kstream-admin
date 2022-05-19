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
class AccountServiceTest {

  @Autowired private TestEntityManager entityManager;

  @Autowired private UserService userService;
  @Autowired private AdminService adminService;
  @Autowired private PoolService poolService;
  @Autowired private AccountService accountService;

  
  @Test
  void injectedServicesAreNotNull() {
    assertThat(userService).isNotNull();
    assertThat(adminService).isNotNull();
    assertThat(poolService).isNotNull();
    assertThat(accountService).isNotNull();
  }

  Long userId;
  Long adminId;
  Long poolId;
  Long accountId;
  
  @BeforeEach
  void init() {
    this.userId = userService.createUser(fooUser()).getId();
    entityManager.clear();
    this.adminId = adminService.createAdmin(userId, "some details").get().getId();
    entityManager.clear();
    this.poolId = poolService.createPool(adminId, TestData.fooPool()).get().getId();
    entityManager.clear();
    this.accountId = accountService.createAccount(poolId, userId, TestData.fooAccount()).get().getId();   
    entityManager.clear();
  }  
  
  @Test
  void otherStuff() {
    assertThat(accountService.getAccount(accountId)).isPresent();
    entityManager.clear();
    assertThat(accountService.getAccountsByPoolId(poolId).size()).isEqualTo(1);
    entityManager.clear();

    assertThat(accountService.searchAccounts(poolId, "abc")).isNotEmpty();
//    assertThat(accountService.searchAccounts(poolId, "sfsdfsfd")).isEmpty();
    entityManager.clear();

    entityManager.clear();
    accountService.deactivate(accountId);
    
    entityManager.clear();
    assertThat(accountService.getAccount(accountId)).isNotPresent();
    entityManager.clear();
    assertThat(accountService.getAccountsByPoolId(poolId)).isEmpty();
    entityManager.clear();
    
//    assertThat(accountService.searchAccounts(poolId, "abc")).isEmpty();

 }
}