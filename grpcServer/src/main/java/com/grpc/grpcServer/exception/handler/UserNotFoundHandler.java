package com.grpc.grpcServer.exception.handler;

import com.grpc.grpcServer.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserNotFoundHandler {
    @ExceptionHandler({UserNotFoundException.class})
    ResponseEntity handleUserNotFoundHandler(Exception e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }
}
