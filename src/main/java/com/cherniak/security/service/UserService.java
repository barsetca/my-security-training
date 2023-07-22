package com.cherniak.security.service;

import com.cherniak.security.UsernameThreadLocal;
import com.cherniak.security.entity.Role;
import com.cherniak.security.entity.User;
import com.cherniak.security.repository.UserRepository;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {

  private UserRepository userRepository;

  private final Logger logger = LoggerFactory.getLogger(UserService.class);

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Optional<User> findByUsernameFromThreadLocal() {
    String username  = UsernameThreadLocal.getUsername();
    logger.info("findByUsername() = " + username +"  /////////////////////////////// " + Thread.currentThread().getName());
        return userRepository.findByUsername(username);
  }

  public Optional<User> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = findByUsername(username).orElseThrow(
        () -> new UsernameNotFoundException(String.format("User %s not found", username)));
    logger.info("loadUserByUsername /////////////////////////////// " + Thread.currentThread().getName());
    return new org.springframework.security.core.userdetails.User(
        user.getUsername(),
        user.getPassword(),
        mapRolesToAuthorities(user.getRoles())
    );
  }

  private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
    return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName()))
        .collect(Collectors.toList());
  }
}
