package guru.bonacci.heroesadmin.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.heroesadmin.domain.AdminUser;
import guru.bonacci.heroesadmin.service.AdminService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminController {

  private final AdminService service;

  
  @PostMapping
  public AdminUser create(Long userId) {
    return service.createAdmin(userId); 
  }
  
  @DeleteMapping("/{id}")
  public void delete( @PathVariable(value = "id") Long adminId) {
    service.delete(adminId);
  }
}
