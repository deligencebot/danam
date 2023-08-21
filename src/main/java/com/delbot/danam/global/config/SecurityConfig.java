package com.delbot.danam.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  //
  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //
    return http.csrf().disable()
    .authorizeRequests()
      .antMatchers("/", "/member/join", "/member/login")
      .permitAll()
      .and()
    .formLogin()
      .loginPage("/member/login")
      .defaultSuccessUrl("/")
      .permitAll()
      .and()
    .logout()
      .permitAll()
      .and()
    .build();
  }

  @Bean
	public PasswordEncoder PasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
}
