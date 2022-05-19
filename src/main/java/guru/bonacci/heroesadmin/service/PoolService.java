package guru.bonacci.heroesadmin.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import guru.bonacci.heroesadmin.domain.Pool;
import guru.bonacci.heroesadmin.repository.PoolRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PoolService {


  private final PoolRepository repo;

  
  public Pool createPool(Pool pool) {
    return repo.saveAndFlush(pool);
  }
}
