package com.starter.be.config;

import com.starter.be.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final UserDetailsService userDetailsService;
  private final UserService userService;
  private final ApplicationProperties applicationProperties;

  @Autowired
  public SecurityConfiguration(
      BCryptPasswordEncoder bCryptPasswordEncoder,
      UserDetailsService userDetailsService,
      UserService userService,
      ApplicationProperties applicationProperties) {
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.userDetailsService = userDetailsService;
    this.userService = userService;
    this.applicationProperties = applicationProperties;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .csrf()
        .disable()
        .authorizeRequests()
        .antMatchers("/api/public/**")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .addFilter(
            new JwtAuthenticationFilter(
                authenticationManager(), userService, applicationProperties))
        .addFilter(new JwtAuthorizationFilter(authenticationManager(), applicationProperties))
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
  }

  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.addAllowedOrigin("http://localhost:8080");
    config.addAllowedMethod("*");
    config.setAllowedHeaders(
        List.of(SecurityConstants.TOKEN_HEADER, "username", "password", "content-type"));
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }
}
