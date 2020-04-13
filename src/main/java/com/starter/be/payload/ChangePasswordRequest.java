package com.starter.be.payload;

import com.starter.be.controller.validator.Password;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {
  String currentPassword;
  @Password String password;
}
