package com.restonov.task.config;

import com.restonov.task.service.impl.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableConfigurationProperties
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final EmployeeService employeeService;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .authorizeRequests().anyRequest().authenticated()
        .and()
        .httpBasic();
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .userDetailsService(employeeService)
        .passwordEncoder(passwordEncoder());
  }
}
