package guru.bonacci.heroesadmin.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.heroesadmin.domain.UserInfo;
import guru.bonacci.heroesadmin.dto.Mappers;
import guru.bonacci.heroesadmin.dto.UserDto;
import guru.bonacci.heroesadmin.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService service;


  @PostMapping
  public UserInfo create(@Valid @RequestBody UserDto dto) {
    var user = Mappers.mapUser(dto);
    return service.createUser(user);
  }
  
  @GetMapping("/{userId}")
  public Optional<UserInfo> retrieve(@PathVariable(value = "userId") Long userId) {
    return service.getUser(userId); 
  }
  
  @PutMapping("/{userId}")
  public UserInfo update( @PathVariable(value = "userId") Long userId,
                          @Valid @RequestBody UserDto dto) {
    var user = Mappers.mapUser(dto);
    user.setId(userId);
    return service.updateUser(user);
  }
}
