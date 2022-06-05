package guru.bonacci.heroesadmin.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import guru.bonacci.heroesadmin.domain.Pool;
import guru.bonacci.heroesadmin.domain.kafka.KafkaPool;
import guru.bonacci.heroesadmin.query.IdAndName;
import guru.bonacci.heroesadmin.repository.AccountRepository;
import guru.bonacci.heroesadmin.repository.AdminRepository;
import guru.bonacci.heroesadmin.repository.PoolRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PoolService {

  private final PoolRepository poolRepo;
  private final AdminRepository adminRepo;
  private final AccountRepository accountRepo;
  private final KafkaTemplate<String, KafkaPool> kafkaTemplate;
  
  
  public Optional<Pool> getPool(Long id) {
    return poolRepo.findById(id);
  }

  public Integer getPoolSize(Long id) {
    return accountRepo.findByPoolId(id).size();
  }

  public List<String> allPoolNames() {
    return poolRepo.findAllProjectedBy().stream()
                  .map(IdAndName::getName)
                  .collect(Collectors.toList());
  }
  
  // https://docs.spring.io/spring-kafka/reference/html/#ex-jdbc-sync
  @Transactional("transactionManager")
  public Pool createPool(Long adminId, Pool pool) {
    var admin = adminRepo.findById(adminId)
         .orElseThrow(() -> new EntityNotFoundException("Cannot find admin with id " + adminId));
    pool.setAdmin(admin);
    
    var kafkaPool = KafkaPool.from(pool); 
    kafkaTemplate.send("pool", kafkaPool.getPoolId(), kafkaPool);
    
    return poolRepo.saveAndFlush(pool);
  }
  
  @Transactional(transactionManager = "transactionManager")
  public void deactivate(Long id) {
    getPool(id).ifPresent(pool -> {
      pool.setActive(false);
      pool.setAdmin(null);
      
      kafkaTemplate.send("pool", pool.getName(), null);
      poolRepo.saveAndFlush(pool);
    });
  }
}
