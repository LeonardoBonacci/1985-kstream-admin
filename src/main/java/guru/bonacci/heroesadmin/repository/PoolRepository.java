package guru.bonacci.heroesadmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import guru.bonacci.heroesadmin.domain.Pool;

@Repository
public interface PoolRepository extends JpaRepository<Pool, Long> {

}
