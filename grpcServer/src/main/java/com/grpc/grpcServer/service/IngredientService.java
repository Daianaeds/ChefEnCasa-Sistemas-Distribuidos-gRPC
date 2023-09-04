package com.grpc.grpcServer.service;


import com.grpc.grpcServer.RecipeRequest;
import com.grpc.grpcServer.entities.Ingredient;

import java.util.List;

public interface IngredientService {

    Ingredient find(String name);

    List<Ingredient> findListIngredient(RecipeRequest request) throws Exception;
}
