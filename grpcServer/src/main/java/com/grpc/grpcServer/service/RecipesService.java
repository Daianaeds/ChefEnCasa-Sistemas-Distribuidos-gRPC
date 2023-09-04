package com.grpc.grpcServer.service;

import com.grpc.grpcServer.RecipeRequest;
import com.grpc.grpcServer.RecipeResponse;
import com.grpc.grpcServer.RecipeResponseBasicList;

public interface RecipesService {

    RecipeResponse newRecipe(RecipeRequest request) throws Exception;

    String followRecipe(int idRecipe, String username) throws Exception;

    String unfollowRecipe(int idRecipe, String username) throws Exception;

    RecipeResponseBasicList getFavouriteRecipes(String username);
}
