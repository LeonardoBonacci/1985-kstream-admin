package guru.bonacci.heroesadmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import guru.bonacci.heroesadmin.domain.UserInfo;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {

}
