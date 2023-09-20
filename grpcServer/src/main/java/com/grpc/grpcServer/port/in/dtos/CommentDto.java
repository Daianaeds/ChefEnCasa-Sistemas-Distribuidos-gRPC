package com.grpc.grpcServer.port.in.dtos;

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
public class CommentDto {
    private String username;
    private int idRecipe;
    private String comment;
}
