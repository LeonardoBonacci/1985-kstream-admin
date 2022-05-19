package guru.bonacci.heroesadmin.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import guru.bonacci.heroesadmin.domain.Pool;
import guru.bonacci.heroesadmin.query.IdAndName;
import guru.bonacci.heroesadmin.repository.AccountRepository;
import guru.bonacci.heroesadmin.repository.AdminRepository;
import guru.bonacci.heroesadmin.repository.PoolRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PoolService {

  private final PoolRepository poolRepo;
  private final AdminRepository adminRepo;
  private final AccountRepository accountRepo;
 
  
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

  public Optional<Pool> createPool(Long adminId, Pool pool) {
    var admin = adminRepo.findById(adminId)
         .orElseThrow(() -> new EntityNotFoundException("Cannot find admin with id " + adminId));

    pool.setAdmin(admin);
    return Optional.of(poolRepo.saveAndFlush(pool));
  }
  
  public void deactivate(Long id) {
    getPool(id).ifPresent(pool -> {
      pool.setActive(false);
      pool.setAdmin(null);
      poolRepo.saveAndFlush(pool);
    });
  }

}
