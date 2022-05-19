package guru.bonacci.heroesadmin.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.heroesadmin.domain.UserInfo;
import guru.bonacci.heroesadmin.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService service;

  
  @PostMapping
  public UserInfo create(UserInfo user) {
    return service.createUser(user);
  }

  @GetMapping
  public Optional<UserInfo> retrieve(Long userId) {
    return service.getUser(userId); // @Valid accounts must be null/empty
  }
}
