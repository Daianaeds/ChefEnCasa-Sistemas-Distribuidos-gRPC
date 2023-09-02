package com.grpc.grpcServer.mapper;

import com.grpc.grpServer.RecipeRequest;
import com.grpc.grpcServer.entities.Ingredient;
import org.springframework.stereotype.Service;

@Service
public class IngredientMapper {

    com.grpc.grpServer.Ingredient convertIngredientToIngredientG(com.grpc.grpcServer.entities.Ingredient request){
        return com.grpc.grpServer.Ingredient.newBuilder()
                .setNombre(request.getNameIngredient()).build();

    }
}
