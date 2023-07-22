package com.cherniak.security.config;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@EnableWebSecurity
@Profile("jdbc")
// классы User Role не нужны В БД должны быть сформированы таблицы точно сотвествующие спецификации
// юзеры и роли (авторитис) туда добавляются либо стандртным sql
// (см C:\newprojects\my-security-training\src\main\resources\db\jdbc)
// либо так как ниже через @Bean JdbcUserDetailsManager
public class JdbcSecurityConfig extends WebSecurityConfigurerAdapter {

  private Logger logger = LoggerFactory.getLogger(JdbcSecurityConfig.class);

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    logger.info(
        "JdbcSecurityConfig ########################################################################");

    http.authorizeRequests()
        .antMatchers("/auth/**").authenticated()
        .antMatchers("/admin/**").hasAnyRole("ADMIN")
        .antMatchers("/h2-console/**").permitAll()
        .anyRequest().permitAll()
        .and()
        .csrf().disable()
        .formLogin();
  }

  @Bean
  public JdbcUserDetailsManager users(DataSource dataSource) {
    UserDetails user = User.builder()
        .username("user2")
        .password("{noop}102")
        .roles("USER")
        .build();
    UserDetails admin = User.builder()
        .username("admin2")
        .password("{bcrypt}$2a$12$1j3D1WhE7Me.Z2nCQOjrP.aN0x6N2qe7mkxcL214WMJQUlBqjJQ9C")
        .authorities("ROLE_ADMIN")
        .build();
    JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
    if (!users.userExists(user.getUsername())){
      users.createUser(user);
    }
    if (!users.userExists(admin.getUsername())){
      users.createUser(admin);
    }
   // return new JdbcUserDetailsManager(dataSource);
    return users;
  }
}
