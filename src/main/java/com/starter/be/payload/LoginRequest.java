package com.starter.be.payload;

import lombok.Data;

@Data
public class LoginRequest {
  String username;
  String password;
}
