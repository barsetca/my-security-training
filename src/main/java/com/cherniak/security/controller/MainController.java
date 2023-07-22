package com.cherniak.security.controller;

import com.cherniak.security.UsernameThreadLocal;
import com.cherniak.security.config.DaoSecurityConfig;
import com.cherniak.security.entity.Role;
import com.cherniak.security.entity.User;
import com.cherniak.security.service.UserService;
import java.security.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

  private UserService userService;
  private final Logger logger = LoggerFactory.getLogger(MainController.class);

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/")
  public String homePage() {return "home";}

  @GetMapping("/admin")
  public String adminPage(Principal principal) {
    logger.info("adminPage /////////////////////////////// " + Thread.currentThread().getName());
    UsernameThreadLocal.setUsername(principal.getName());
    User user = userService.findByUsernameFromThreadLocal().orElse(new User());
    UsernameThreadLocal.clear();
    return "adminName = " + user.getUsername();
  }

  @GetMapping("/unsec")
  public String unsecPage() {
    return "anonimus";
  }

  @GetMapping("/auth")
  public String authPage(Principal principal) {
    logger.info("authPage /////////////////////////////// " + Thread.currentThread().getName());
    User user = userService.findByUsername(principal.getName())
        .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", principal.getName())));
    return String.format("Auth user имя: %s, email: %s, роль: %s",
        user.getUsername(),
        user.getEmail(),
        user.getRoles().stream().map(Role::getName).findFirst().orElse("no roles")
    );
  }

}
