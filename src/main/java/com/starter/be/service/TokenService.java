package com.starter.be.service;

import com.starter.be.config.ApplicationProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

  private final ApplicationProperties applicationProperties;

  public TokenService(ApplicationProperties applicationProperties) {
    this.applicationProperties = applicationProperties;
  }

  Long getUserId(String token) {
    byte[] signingKey = applicationProperties.getSecurityJwtSecret().getBytes();
    String bearer = token.replace("Bearer ", "");
    Jws<Claims> claims = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(bearer);
    return Long.parseLong(claims.getBody().getSubject());
  }
}
