package guru.bonacci.heroesadmin.service;

import java.util.List;
import java.util.Optional;

import org.springframework.context.ApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import guru.bonacci.heroesadmin.accountinit.PoolTypeBasedInitializer;
import guru.bonacci.heroesadmin.domain.AccountDetails;
import guru.bonacci.heroesadmin.domain.kafka.KafkaAccount;
import guru.bonacci.heroesadmin.repository.AccountRepository;
import guru.bonacci.heroesadmin.repository.PoolRepository;
import guru.bonacci.heroesadmin.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

  private final AccountRepository accountRepo;
  private final PoolRepository poolRepo;
  private final UserRepository userRepo;
  private final ApplicationContext appContext;
  private final KafkaTemplate<String, KafkaAccount> kafkaTemplate;
  
  
  public Optional<AccountDetails> getAccount(Long id) {
    return accountRepo.findById(id);
  }

  public List<AccountDetails> getAccountsByPoolId(Long id) {
    return accountRepo.findByPoolId(id);
  }

  @Transactional(transactionManager = "transactionManager")
  public Optional<AccountDetails> createAccount(Long poolId, Long userId, AccountDetails account) {
    var pool = poolRepo.findById(poolId)
        .orElseThrow(() -> new EntityNotFoundException("Cannot find pool with id " + poolId));
    account.setPool(pool);
    account.setPoolName(pool.getName()); 
    account.setPoolAccountId(pool.getName() + "." + account.getName()); // identifier in ledger

    var user = userRepo.findById(userId)
      .orElseThrow(() -> new EntityNotFoundException("Cannot find user with id " + userId));
    account.setUser(user);

    var poolType = pool.getType().toString().toLowerCase(); // maps uppercase enum on lowercase bean name
    var initializer = appContext.getBean(poolType, PoolTypeBasedInitializer.class);
    account.setStartAmount(initializer.determineStartAmount(pool));
    
    var kafkaAccount = KafkaAccount.from(account); 
    kafkaTemplate.send("account", kafkaAccount.identifier(), kafkaAccount);
    
    return Optional.of(accountRepo.saveAndFlush(account));
  }

  public List<AccountDetails> searchAccounts(Long poolId, String accountName) {
    return accountRepo.findByPoolIdAndNameLike(poolId, "%"+accountName+"%");
  }

  @Transactional(transactionManager = "transactionManager")
  public void deactivate(Long id) {
    getAccount(id).ifPresent(account -> {
      account.setActive(false);
      
      kafkaTemplate.send("account", account.getPoolAccountId(), null);
      accountRepo.saveAndFlush(account);
    });
  }
}
