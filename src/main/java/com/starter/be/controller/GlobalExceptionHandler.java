package com.starter.be.controller;

import com.starter.be.exception.ServiceException;
import com.starter.be.payload.ErrorResponse;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ServiceException.class)
  public ResponseEntity<ErrorResponse> handleException(ServiceException exception) {
    log.info(
        String.format("Service exception: %s %s", exception.getUuid(), exception.getMessage()));
    return new ResponseEntity<>(
        new ErrorResponse(exception.getUuid(), exception.getMessage()),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
