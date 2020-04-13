package com.starter.be.config;

import com.starter.be.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;
  private final UserService userService;
  private final ApplicationProperties applicationProperties;

  JwtAuthenticationFilter(
      AuthenticationManager authenticationManager,
      UserService userService,
      ApplicationProperties applicationProperties) {
    this.authenticationManager = authenticationManager;
    this.userService = userService;
    this.applicationProperties = applicationProperties;

    setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) {
    var username = request.getHeader("username");
    var password = request.getHeader("password");
    var authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

    return authenticationManager.authenticate(authenticationToken);
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filter,
      Authentication authentication) {
    User principal = ((User) authentication.getPrincipal());
    com.starter.be.model.User user = userService.findByUsername(principal.getUsername());

    List<String> roles =
        principal.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

    byte[] signingKey = applicationProperties.getSecurityJwtSecret().getBytes();

    String token =
        Jwts.builder()
            .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
            .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
            .setIssuer(SecurityConstants.TOKEN_ISSUER)
            .setAudience(SecurityConstants.TOKEN_AUDIENCE)
            .setSubject(user.getId().toString())
            .setExpiration(
                new Date(
                    System.currentTimeMillis()
                        + applicationProperties.getSecurityJwtTokenExpiration()))
            .claim("rol", roles)
            .compact();

    response.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, SecurityConstants.TOKEN_HEADER);
    response.addHeader(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + token);
  }
}
