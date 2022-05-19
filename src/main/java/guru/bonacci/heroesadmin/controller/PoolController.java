package guru.bonacci.heroesadmin.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.heroesadmin.domain.AccountDetails;
import guru.bonacci.heroesadmin.domain.AdminUser;
import guru.bonacci.heroesadmin.domain.Pool;
import guru.bonacci.heroesadmin.service.AccountService;
import guru.bonacci.heroesadmin.service.AdminService;
import guru.bonacci.heroesadmin.service.PoolService;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/pools")
@RequiredArgsConstructor
public class PoolController {
  
  private final PoolService poolService;
  private final AdminService adminService;
  private final AccountService accountService;

  
  @PostMapping
  public Pool create(Pool pool) { //@Valid must have adminuser but no accounts
    return poolService.createPool(pool); 
  }
  
  @GetMapping("/{poolId}/admin")
  public Optional<AdminUser> retrieveAdmin(Long poolId) {
    return adminService.getAdminByPoolId(poolId); 
  }
  
  @GetMapping("/{poolId}/accounts")
  public List<AccountDetails> retrieveAccounts(Long poolId) {
    return accountService.getAccountsByPoolId(poolId); 
  }
  

}
