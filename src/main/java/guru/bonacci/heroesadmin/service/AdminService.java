package guru.bonacci.heroesadmin.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import guru.bonacci.heroesadmin.domain.AdminUser;
import guru.bonacci.heroesadmin.domain.Pool;
import guru.bonacci.heroesadmin.repository.AdminRepository;
import guru.bonacci.heroesadmin.repository.PoolRepository;
import guru.bonacci.heroesadmin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

  private final AdminRepository adminRepo;
  private final UserRepository userRepo;
  private final PoolRepository poolRepo;


  public Optional<AdminUser> getAdmin(Long adminId) {
    return adminRepo.findById(adminId);
  }

  public Optional<AdminUser> createAdmin(Long userId, String bankDetails) {
    var user = userRepo.findById(userId).get(); //TODO optinoal
    return Optional.of(doCreateAdmin(AdminUser.builder().user(user).bankDetails(bankDetails).build()));
  }

  private AdminUser doCreateAdmin(AdminUser admin) {
    return adminRepo.saveAndFlush(admin);
  }

  public Optional<AdminUser> getAdminByPoolId(Long poolId) {
    return adminRepo.findByPools(Pool.builder().id(poolId).build());
  }

  public void delete(Long id) { 
    log.info("about to delete admin {}", id);
    var admin = adminRepo.findById(id).get(); //TODO optional
    
    if (!admin.getPools().isEmpty()) {
      throw new IllegalStateException("Cannot delete while linked to pool");
    }

    log.info("deleting admin {}", id);
    adminRepo.deleteById(id);
    adminRepo.flush();
  }
}
