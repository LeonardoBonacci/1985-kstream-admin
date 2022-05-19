package guru.bonacci.heroesadmin.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import guru.bonacci.heroesadmin.domain.AccountDetails;
import guru.bonacci.heroesadmin.repository.AccountRepository;
import guru.bonacci.heroesadmin.repository.PoolRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

  private final AccountRepository accountRepo;
  private final PoolRepository poolRepo;
  
  
  public Optional<AccountDetails> getAccount(Long id) {
    return accountRepo.findById(id);
  }

  public Integer getPoolSize(Long id) {
    return getAccountsByPoolId(id).size();
  }

  public List<AccountDetails> getAccountsByPoolId(Long id) {
    return accountRepo.findByPoolId(id);
  }

  public Optional<AccountDetails> createAccount(Long poolId, AccountDetails account) {
    var pool = poolRepo.findById(poolId).get(); //TODO optional
    account.setPool(pool);
    return Optional.of(accountRepo.saveAndFlush(account));
  }

  public List<AccountDetails> searchAccounts(Long poolId, String accountName) {
    return accountRepo.findByPoolIdAndNameLike(poolId, "%"+accountName+"%");
  }

  public void deactivate(Long id) {
    getAccount(id).ifPresent(account -> {
      account.setActive(false);
      accountRepo.saveAndFlush(account);
    });
  }
}
