package guru.bonacci.heroesadmin;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import guru.bonacci.heroesadmin.domain.Pool;
import guru.bonacci.heroesadmin.domain.UserInfo;
import guru.bonacci.heroesadmin.service.AccountService;
import guru.bonacci.heroesadmin.service.AdminService;
import guru.bonacci.heroesadmin.service.PoolService;
import guru.bonacci.heroesadmin.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableTransactionManagement
public class AppHeroesAdmin {

	public static void main(String[] args) {
		SpringApplication.run(AppHeroesAdmin.class, args);
	}
	
  @Bean
  CommandLineRunner demo(UserService users, AdminService admins, PoolService pools, AccountService accounts) {
    return args -> {
      var user = users.createUser(UserInfo.builder().name("us a").build());
      log.info("in {}", user);
  
      var admin = admins.createAdmin(user.getId(), "12345").get();
      log.info("in {}", admin);
  
      var pool = pools.createPool(admin.getId(), Pool.builder().poolType("sardex").name("coro").admin(admin).build()).get();
      log.info("in {}", pool);

      log.info("get pool {}", pools.getPool(pool.getId()).get());
      log.info("all {}", pools.allNames());

      pools.deactivate(pool.getId());

      log.info("all {}", pools.allNames());

    };
  }


//  @Bean
//  CommandLineRunner demo(UserService users, AdminService admins, PoolService pools, AccountService accounts) {
//    return args -> {
//      var user = users.createUser(UserInfo.builder().name("us a").build());
//      log.info("in {}", user);
//      log.info("get {}", users.getUser(user.getId()).get());
//
//      var user2 = users.createUser(UserInfo.builder().name("us b").build());
//
//      var admin = admins.createAdmin(AdminUser.builder().user(user).build());
//      log.info("in {}", admin);
//      log.info("get {}", admins.getAdmin(admin.getId()).get());
//
//      var pool = pools.createPool(Pool.builder().poolType("coro").admin(admin).build());
//      log.info("in {}", pool);
//      
//      var account = accounts.createAccount(pool.getId(), AccountDetails.builder().name("aaa").user(user).build());
//      log.info("in {}", account);
//      log.info("get {}", accounts.getAccount(account.getId()).get());
//
//      accounts.createAccount(pool.getId(), AccountDetails.builder().pool(pool).name("aab").user(user2).build());
//
//      log.info("get {}", users.getUser(user.getId()).get());
//      log.info("get {}", accounts.getAccountsByPoolId(pool.getId()));
//      log.info("get {}", admins.getAdminByPoolId(pool.getId()));
//      
//      log.info("search accounts {}", accounts.searchAccounts(pool.getId(), "aa"));
//
//    };
//  }
}
