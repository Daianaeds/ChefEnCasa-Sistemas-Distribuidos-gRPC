package com.grpc.grpcServer.port.in.soap.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RecipeBookRequestCreateDto {

    private String name;
    private String username;

}
