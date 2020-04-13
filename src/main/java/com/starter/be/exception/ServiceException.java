package com.starter.be.exception;

import java.util.UUID;
import lombok.Data;

@Data
public class ServiceException extends RuntimeException {
 private final String uuid;

  public ServiceException(String message) {
    super(message);
    this.uuid = UUID.randomUUID().toString();
  }

  public ServiceException(String message, Object... params) {
    this(String.format(message, params));
  }
}
