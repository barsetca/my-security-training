package com.cherniak.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Profile("simple")
// только один стандартный юзер (username = user)
//пароль генерится пи запуске приложения - по сути балванка для будущей безопасности
public class SimpleSecurityConfig extends WebSecurityConfigurerAdapter {

  private final Logger logger = LoggerFactory.getLogger(SimpleSecurityConfig.class);

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    logger.info("SimpleSecurityConfig configure ---------------------------------------------------------------");

    http.authorizeRequests()
        .antMatchers("/auth/**").authenticated()
        .antMatchers("/admin/**").hasAnyRole("ADMIN", "SUPERADMIN") // ROLE_ADMIN, ROLE_SUPERADMIN
        .anyRequest().permitAll()
        .and()
        .formLogin();
  }
}
