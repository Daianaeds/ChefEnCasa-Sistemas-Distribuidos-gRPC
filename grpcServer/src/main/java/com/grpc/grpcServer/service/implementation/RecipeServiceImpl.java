package com.grpc.grpcServer.service.implementation;

import com.grpc.grpServer.RecipeRequest;
import com.grpc.grpServer.RecipeResponse;
import com.grpc.grpcServer.entities.Recipe;
import com.grpc.grpcServer.mapper.RecipeMapper;
import com.grpc.grpcServer.repositories.RecipeRepository;
import com.grpc.grpcServer.service.RecipesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.grpc.grpcServer.service.UserService;
@RequiredArgsConstructor
@Service
public class RecipeServiceImpl implements RecipesService {

    @Autowired
    RecipeRepository recipesRepository;

    @Autowired
    RecipeMapper recipeMapper;

    @Autowired
    UserService userService;


    @Override
    public RecipeResponse newRecipe(RecipeRequest request) throws Exception {
        Recipe recipe = recipeMapper.convertRecipeRequestToRecipes(request);

        Recipe recipeSave = recipesRepository.save(recipe);

        userService.addRecipe(recipeSave, request.getAuth());

        return recipeMapper.convertRecipeToRecipeResponse(recipeSave);
    }





}
