package guru.bonacci.heroesadmin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import guru.bonacci.heroesadmin.domain.AdminUser;
import guru.bonacci.heroesadmin.domain.Pool;

@Repository
public interface AdminRepository extends JpaRepository<AdminUser, Long> {

  Optional<AdminUser> findByPools(Pool pool);
}
