package com.grpc.grpcServer.service;

import com.grpc.grpServer.RecipeRequest;
import com.grpc.grpServer.RecipeResponse;
import com.grpc.grpcServer.entities.Recipe;

public interface RecipesService {

    RecipeResponse newRecipe(RecipeRequest request) throws Exception;
}
