package guru.bonacci.heroesadmin.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import guru.bonacci.heroesadmin.domain.UserInfo;
import guru.bonacci.heroesadmin.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

  private final UserRepository repo;

  
  public Optional<UserInfo> getUser(Long id) {
    return repo.findById(id);
  }

  public UserInfo createUser(UserInfo user) {
    return repo.saveAndFlush(user);
  }

  public UserInfo updateUser(UserInfo user) { // TODO ignore accounts
    return repo.saveAndFlush(user);
  }
  
  public void deleteUser(Long id) {
    throw new UnsupportedOperationException();
  }
}
