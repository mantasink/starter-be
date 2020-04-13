package com.starter.be.controller;

import com.starter.be.payload.AboutDataResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/private")
public class PrivateController {

  @GetMapping
  public AboutDataResponse getMessage() {
    return new AboutDataResponse("Hello from private API controller");
  }
}
