package com.grpc.grpcServer.mapper;

import org.springframework.stereotype.Service;

@Service
public class IngredientMapper {

    com.grpc.grpcServer.Ingredient convertIngredientToIngredientG(com.grpc.grpcServer.entities.Ingredient request) {
        return com.grpc.grpcServer.Ingredient.newBuilder()
                .setNombre(request.getNameIngredient())
                .setCantidad(request.getAmount()).build();

    }
}
