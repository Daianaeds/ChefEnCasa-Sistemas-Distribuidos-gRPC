package com.grpc.grpcServer.port.in.soap.mapper;

import com.grpc.grpcServer.entities.Recipe;
import com.grpc.grpcServer.port.in.soap.dtos.RecipeDto;
import org.springframework.stereotype.Service;

@Service
public class RecipeSoapMapper {

    public RecipeDto recipeToDto(Recipe recipe) {

       return  RecipeDto.builder()
                .id(recipe.getId())
                .title(recipe.getTitle())
                .pictures(recipe.getPictures().get(0).getUrlPicture())
                .build();
    }
}
