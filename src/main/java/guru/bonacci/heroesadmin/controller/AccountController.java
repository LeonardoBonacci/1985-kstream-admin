package guru.bonacci.heroesadmin.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.heroesadmin.domain.AccountDetails;
import guru.bonacci.heroesadmin.service.AccountService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

  private final AccountService service;

  
  @PostMapping
  public AccountDetails create(Long poolId, AccountDetails account) { //@Valid must have user and name
    return service.createAccount(poolId, account); 
  }

  @GetMapping("/{accountId}")
  public Optional<AccountDetails> retrieve(Long accountId) {
    return service.getAccount(accountId); 
  }
  
  @GetMapping("/{poolId}/{accountName}")
  public List<AccountDetails> searchAccounts(Long poolId, String accountName) {
    return service.searchAccounts(poolId, accountName); 
  }

  @PutMapping
  public AccountDetails update(Long accountId, AccountDetails account) { //@Valid ?
    return service.updateAccount(account); 
  }

  @DeleteMapping("/{id}")
  public void delete( @PathVariable(value = "id") Long adminId) {
    service.delete(adminId);
  }
}
