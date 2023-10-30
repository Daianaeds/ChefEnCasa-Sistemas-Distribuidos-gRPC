package com.grpc.grpcServer.service;


import com.grpc.grpcServer.RecipeRequest;
import com.grpc.grpcServer.dto.CompleteRecipeDTO;
import com.grpc.grpcServer.dto.IngredientsDTO;
import com.grpc.grpcServer.entities.Ingredient;

import java.util.List;

public interface IngredientService {

    Ingredient find(String name, String amount);

    List<Ingredient> findListIngredient(RecipeRequest request) throws Exception;

    List<Ingredient> findListIngredient(CompleteRecipeDTO completeRecipeDTO) throws Exception;
}
