package com.starter.be.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class ApplicationProperties {

  @Value("${security.jwt.secret}")
  private String securityJwtSecret;

  @Value("${security.jwt.token.expiration}")
  private Long securityJwtTokenExpiration;
}
