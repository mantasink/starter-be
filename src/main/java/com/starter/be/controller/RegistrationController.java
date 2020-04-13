package com.starter.be.controller;

import com.starter.be.payload.RegistrationRequest;
import com.starter.be.service.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class RegistrationController {

  private UserService userService;

  @Autowired
  public RegistrationController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/registration")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity registerUser(@Valid @RequestBody RegistrationRequest request) {
    userService.registerUser(request);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
