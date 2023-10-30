package com.grpc.grpcServer.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.grpc.grpcServer.entities.Ingredient;
import com.grpc.grpcServer.entities.Picture;
import com.grpc.grpcServer.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@Getter
@Setter
public class CompleteRecipeDTO {

    private String title;

    private String description;

    private String steps;

    @JsonProperty("time_minutes")
    private int timeMinutes;

    @JsonProperty("name_category")
    private String category;

    private UserDTO auth;

    private List<IngredientsDTO> ingredients = new ArrayList<>();

    private List<PictureDTO> pictures = new ArrayList<>();
    private int idIncompleteRecipe;
    public CompleteRecipeDTO() {
    }
}
