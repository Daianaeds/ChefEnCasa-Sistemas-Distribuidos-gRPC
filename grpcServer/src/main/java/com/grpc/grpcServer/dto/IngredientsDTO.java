package com.grpc.grpcServer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class IngredientsDTO {

    private String nombre;

    private String cantidad;
}
