package com.grpc.grpcServer.exception.handler;

import com.grpc.grpcServer.exception.IncompleteRecipeNotFoundException;
import com.grpc.grpcServer.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NotFoundHandler {

    @ExceptionHandler({UserNotFoundException.class,
            IncompleteRecipeNotFoundException.class})
    ResponseEntity handleUserNotFoundHandler(Exception e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }
}
