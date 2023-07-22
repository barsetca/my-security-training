package com.cherniak.security.config;

import java.beans.BeanProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@Profile("inmemory")
// юзеры создаются через @Bean UserDetailsService(InMemoryUserDetailsManager) и хранятся в памяти
public class InMemorySecurityConfig extends WebSecurityConfigurerAdapter {

  private final Logger logger = LoggerFactory.getLogger(InMemorySecurityConfig.class);

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    logger.info("InMemorySecurityConfig /////////////////////////////////////////////////////////");
    http.authorizeRequests()
        .antMatchers("/auth/**").authenticated()
        .antMatchers("/admin/**").hasAnyRole("ADMIN", "SUPERADMIN")
        .anyRequest().permitAll()
        .and()
        .formLogin();
  }

  @Bean
  public UserDetailsService users() {
    UserDetails user = User.builder()
        .username("user")
        .password("{noop}100")
        .roles("USER")
        .build();
    UserDetails admin = User.builder()
        .username("admin")
        .password("{bcrypt}$2a$12$1j3D1WhE7Me.Z2nCQOjrP.aN0x6N2qe7mkxcL214WMJQUlBqjJQ9C")
        .roles("ADMIN", "USER")
        .build();
    return new InMemoryUserDetailsManager(user, admin);
  }

}
