package com.starter.be.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

@Log
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

  private final ApplicationProperties applicationProperties;

  JwtAuthorizationFilter(
      AuthenticationManager authenticationManager, ApplicationProperties applicationProperties) {
    super(authenticationManager);
    this.applicationProperties = applicationProperties;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {
    var authentication = getAuthentication(request);
    var header = request.getHeader(SecurityConstants.TOKEN_HEADER);

    if (StringUtils.isEmpty(header) || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
      filterChain.doFilter(request, response);
      return;
    }

    SecurityContextHolder.getContext().setAuthentication(authentication);
    filterChain.doFilter(request, response);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
    var token = request.getHeader(SecurityConstants.TOKEN_HEADER);
    if (StringUtils.isEmpty(token)) {
      return null;
    }

    try {
      var signingKey = applicationProperties.getSecurityJwtSecret().getBytes();

      var parsedToken =
          Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token.replace("Bearer ", ""));

      var username = parsedToken.getBody().getSubject();

      var authorities =
          ((List<?>) parsedToken.getBody().get("rol"))
              .stream()
                  .map(authority -> new SimpleGrantedAuthority((String) authority))
                  .collect(Collectors.toList());

      if (!StringUtils.isEmpty(username)) {
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
      }
    } catch (ExpiredJwtException exception) {
      log.warning(
          String.format(
              "Request to parse expired JWT: %s failed : %s", token, exception.getMessage()));
    } catch (UnsupportedJwtException exception) {
      log.warning(
          String.format(
              "Request to parse unsupported JWT: %s failed: %s", token, exception.getMessage()));
    } catch (MalformedJwtException exception) {
      log.warning(
          String.format(
              "Request to parse invalid JWT: %s failed: %s", token, exception.getMessage()));
    } catch (IllegalArgumentException exception) {
      log.warning(
          String.format(
              "Request to parse empty or null JWT: %s failed: %s", token, exception.getMessage()));
    }

    return null;
  }
}
