package guru.bonacci.heroesadmin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import guru.bonacci.heroesadmin.domain.Pool;
import guru.bonacci.heroesadmin.query.IdAndName;

@Repository
public interface PoolRepository extends JpaRepository<Pool, Long> {

  List<IdAndName> findAllProjectedBy();
}
