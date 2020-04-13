package com.starter.be.controller;

import com.starter.be.payload.ChangePasswordRequest;
import com.starter.be.service.UserService;
import javax.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Log
@RestController
@RequestMapping("/api/user")
public class UserController {

  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/change-password")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity changePassword(
      @RequestHeader("Authorization") String token,
      @Valid @RequestBody ChangePasswordRequest request) {
    userService.changePassword(token, request);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
