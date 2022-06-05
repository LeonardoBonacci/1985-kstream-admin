package guru.bonacci.heroesadmin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import guru.bonacci.heroesadmin.domain.AdminUser;
import guru.bonacci.heroesadmin.domain.Pool;

@Repository
@Transactional("transactionManager")
public interface AdminRepository extends JpaRepository<AdminUser, Long> {

  Optional<AdminUser> findByPools(Pool pool);
}
