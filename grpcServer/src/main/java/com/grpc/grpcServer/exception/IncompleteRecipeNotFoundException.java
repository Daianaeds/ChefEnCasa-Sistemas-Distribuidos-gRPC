package com.grpc.grpcServer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IncompleteRecipeNotFoundException extends Exception {

    public IncompleteRecipeNotFoundException(String message) {
        super(message);
    }
}
