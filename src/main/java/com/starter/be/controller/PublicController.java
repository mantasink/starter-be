package com.starter.be.controller;

import com.starter.be.payload.AboutDataResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class PublicController {

  @GetMapping
  public AboutDataResponse getMessage() {
    return new AboutDataResponse("Hello from public API controller");
  }
}
