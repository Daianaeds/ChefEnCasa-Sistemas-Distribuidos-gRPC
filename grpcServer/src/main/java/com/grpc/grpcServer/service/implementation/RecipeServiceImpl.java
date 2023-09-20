package com.grpc.grpcServer.service.implementation;

import com.grpc.grpcServer.FindRecipeRequest;
import com.grpc.grpcServer.RecipeRequest;
import com.grpc.grpcServer.RecipeResponse;
import com.grpc.grpcServer.RecipeResponseBasicList;
import com.grpc.grpcServer.entities.Recipe;
import com.grpc.grpcServer.entities.User;
import com.grpc.grpcServer.mapper.IngredientMapper;
import com.grpc.grpcServer.mapper.PictureMapper;
import com.grpc.grpcServer.mapper.RecipeMapper;
import com.grpc.grpcServer.repositories.RecipeRepository;
import com.grpc.grpcServer.service.RecipesService;
import com.grpc.grpcServer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

import java.util.List;



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

    @Transactional
    @Override
    public String followRecipe(int idRecipe, String username) throws Exception {
        Recipe recipe = recipesRepository.findById(idRecipe).get();
        User user = userService.findByUsername(username);

        user.getFavoriteRecipes().add(recipe);
        userService.saveUser(user);
        return ("Se agregó la receta  " + recipe.getTitle());
    }

    @Transactional
    @Override
    public String unfollowRecipe(int idRecipe, String username) throws Exception {
        Recipe recipe = recipesRepository.findById(idRecipe).get();
        User user = userService.findByUsername(username);

        user.getFavoriteRecipes().remove(recipe);
        userService.saveUser(user);
        return ("Se eliminó la receta  " + recipe.getTitle());
    }

    @Transactional
    @Override
    public RecipeResponseBasicList getFavouriteRecipes(String username) {
        List<Recipe> favouriteRecipes = userService.getFavouriteRecipes(username);
        return recipeMapper.convertRecipetoRecipeResponseBasicList(favouriteRecipes);
    }

    @Transactional
    @Override
    public RecipeResponseBasicList getAllRecipes() {
        List<Recipe> recipes = recipesRepository.findAll();

        return recipeMapper.convertRecipetoRecipeResponseBasicList(recipes);
    }

    @Transactional
    @Override
    public RecipeResponseBasicList findRecipeByFilter(FindRecipeRequest findRecipeRequest) {
          List<Recipe> recipes = recipesRepository.findByFilter(
                    findRecipeRequest.getTitle(),
                    findRecipeRequest.getNameCategory(),
                    findRecipeRequest.getNameIngredient(),
                    findRecipeRequest.getTimeMinutes());

           return recipeMapper.convertRecipetoRecipeResponseBasicList(recipes);

    }

    @Override
    public Recipe findById(int id) {
        return recipesRepository.findById(id).get();
    }
}
