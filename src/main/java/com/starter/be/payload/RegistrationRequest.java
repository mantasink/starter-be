package com.starter.be.payload;

import com.starter.be.controller.validator.Password;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

  String username;
  String email;
  @Password String password;
}
