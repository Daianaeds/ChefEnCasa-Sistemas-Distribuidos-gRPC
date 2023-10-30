package com.grpc.grpcServer.port.in.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class InternalMailDto {

    @JsonIgnore
    private int id;

    //emisor
    private String source;

    //asunto
    private String subject;

    //receptor
    private String destination;

    //respuesta
    private String subjectReply;
}
