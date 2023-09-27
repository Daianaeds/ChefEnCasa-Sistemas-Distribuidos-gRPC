package com.grpc.grpcServer.service;

import com.grpc.grpcServer.FindRecipeRequest;
import com.grpc.grpcServer.RecipeRequest;
import com.grpc.grpcServer.RecipeResponse;
import com.grpc.grpcServer.RecipeResponseBasicList;
import com.grpc.grpcServer.entities.Recipe;

public interface RecipesService {

    RecipeResponse newRecipe(RecipeRequest request) throws Exception;

    String followRecipe(int idRecipe, String username) throws Exception;

    String unfollowRecipe(int idRecipe, String username) throws Exception;

    RecipeResponseBasicList getFavouriteRecipes(String username) ;

    RecipeResponseBasicList getAllRecipes() ;

    RecipeResponseBasicList findRecipeByFilter(FindRecipeRequest findRecipeRequest);

    Recipe findById(int id);
}
