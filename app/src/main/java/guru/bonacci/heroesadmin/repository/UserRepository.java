package guru.bonacci.heroesadmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import guru.bonacci.heroesadmin.domain.UserInfo;

@Repository
@Transactional("transactionManager")
public interface UserRepository extends JpaRepository<UserInfo, Long> {
}
