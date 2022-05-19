package guru.bonacci.heroesadmin.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import guru.bonacci.heroesadmin.domain.AdminUser;
import guru.bonacci.heroesadmin.domain.Pool;
import guru.bonacci.heroesadmin.repository.AdminRepository;
import guru.bonacci.heroesadmin.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

  private final AdminRepository adminRepo;
  private final UserRepository userRepo;


  public Optional<AdminUser> getAdmin(Long id) {
    return adminRepo.findById(id);
  }

  public AdminUser createAdmin(Long userId) {
    var user = userRepo.findById(userId).get();
    return createAdmin(AdminUser.builder().user(user).build());
  }

  public AdminUser createAdmin(AdminUser admin) {
    return adminRepo.saveAndFlush(admin);
  }

  public Optional<AdminUser> getAdminByPoolId(Long poolId) {
    return adminRepo.findByPools(Pool.builder().id(poolId).build());
  }

  public void delete(Long id) { 
    if (!adminRepo.findById(id).get().getPools().isEmpty()) {
      // throw error
    }
    adminRepo.deleteById(id); // should not cascade user
  }
}
