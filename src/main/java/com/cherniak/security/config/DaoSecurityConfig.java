package com.cherniak.security.config;

import com.cherniak.security.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Profile("dao")
// собственные таблицы в бд Требуются @Entity User Roles, UserService should be implements UserDetailsService
// и нужно сделать мапинг нашего юзера в юзера спринга и ролей в авторитес, при этом если это роли
//то должен быть префикс ROLE_, а если это привелегия то просто
//{noop} и {bcrypt} теперь не работают
public class DaoSecurityConfig extends WebSecurityConfigurerAdapter {

  private final Logger logger = LoggerFactory.getLogger(DaoSecurityConfig.class);

  private UserService userDetailsService;

  @Autowired
  public void setUserDetailsService(UserService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Bean
  public BCryptPasswordEncoder getEncoder() {
    return new BCryptPasswordEncoder();
  }

//  @Bean
//  public ThreadLocal<String> threadLocal(){
//    return new ThreadLocal<>();
//  }

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setPasswordEncoder(getEncoder());
    daoAuthenticationProvider.setUserDetailsService(userDetailsService);
    return daoAuthenticationProvider;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    logger.info("DaoSecurityConfig configure ||||||||||||||||||||||||||||||||||||||||||||||||||||");

    http.authorizeRequests()
        .antMatchers("/auth/**").authenticated()
        .antMatchers("/dao/**").authenticated()
        .antMatchers("/admin/**").hasAnyRole("ADMIN")
        .antMatchers("/h2-console/**").permitAll()
        .anyRequest().permitAll()
        .and()
        .csrf().disable()
        .formLogin()
        .defaultSuccessUrl("/auth");
  }
}
