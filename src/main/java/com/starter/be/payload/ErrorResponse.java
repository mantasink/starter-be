package com.starter.be.payload;

import lombok.Data;

@Data
public class ErrorResponse {
  private String uuid;
  private String message;

  public ErrorResponse(String uuid, String message) {
    this.uuid = uuid;
    this.message = message;
  }

}
