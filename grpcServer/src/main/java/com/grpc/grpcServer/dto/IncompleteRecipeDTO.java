package com.grpc.grpcServer.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IncompleteRecipeDTO {

    private String title;
    private String description;
    private String category;
    private int time;
    private String username;
}
