package guru.bonacci.heroesadmin.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import guru.bonacci.heroesadmin.domain.Pool;
import guru.bonacci.heroesadmin.query.IdAndName;
import guru.bonacci.heroesadmin.repository.AdminRepository;
import guru.bonacci.heroesadmin.repository.PoolRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PoolService {

  private final PoolRepository poolRepo;
  private final AdminRepository adminRepo;
 
  
  public Optional<Pool> getPool(Long id) {
    return poolRepo.findById(id);
  }

  public List<String> allNames() {
    return poolRepo.findAllProjectedBy().stream()
                  .map(IdAndName::getName)
                  .collect(Collectors.toList());
  }

  public Optional<Pool> createPool(Long adminId, Pool pool) {
    var admin = adminRepo.findById(adminId).get(); //TODO optional
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
