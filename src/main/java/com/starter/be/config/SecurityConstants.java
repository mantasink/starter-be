package com.starter.be.config;

public class SecurityConstants {

  static final String AUTH_LOGIN_URL = "/api/authenticate";

  // JWT token defaults
  static final String TOKEN_HEADER = "Authorization";
  static final String TOKEN_PREFIX = "Bearer ";
  static final String TOKEN_TYPE = "JWT";
  static final String TOKEN_ISSUER = "backend-api";
  static final String TOKEN_AUDIENCE = "backend-app";
  static final int TOKEN_EXPIRATION = 864000000;

  private SecurityConstants() {
    throw new IllegalStateException("Cannot create instance of static util class");
  }
}
