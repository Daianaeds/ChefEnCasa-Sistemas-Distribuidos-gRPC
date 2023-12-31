package com.grpc.grpcServer.service.implementation;

import com.grpc.grpcServer.*;
import com.grpc.grpcServer.entities.Picture;
import com.grpc.grpcServer.entities.Recipe;
import com.grpc.grpcServer.entities.User;
import com.grpc.grpcServer.mapper.RecipeMapper;
import com.grpc.grpcServer.repositories.RecipeRepository;
import com.grpc.grpcServer.service.PictureService;
import com.grpc.grpcServer.service.RecipesService;
import com.grpc.grpcServer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class RecipeServiceImpl implements RecipesService {

    @Autowired
    RecipeRepository recipesRepository;

    @Autowired
    RecipeMapper recipeMapper;

    @Autowired
    PictureService pictureService;

    @Autowired
    UserService userService;

    @Transactional
    @Override
    public RecipeResponse newRecipe(RecipeRequest request) throws Exception {
        Recipe recipe = recipeMapper.convertRecipeRequestToRecipes(request);

        Recipe recipeSave = recipesRepository.save(recipe);

        List<Picture> pictures = request.getPicturesList().stream().map(picture -> pictureService.save(picture.getUrl(), recipeSave)).collect(Collectors.toList());
        recipeSave.setPictures(pictures);

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
        List<Recipe> recipes = recipesRepository.findByIsDeleteFalse();

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

    @Transactional
    @Override
    public RecipeResponseBasic findRecipeById(int recipeId) throws Exception {
        Recipe recipe = findById(recipeId);
        return recipeMapper.convertRecipeToRecipeResponseBasic(recipe);
    }

    @Transactional
    @Override
    public Recipe saveRecipe(Recipe recipe) {
        recipe.getAuthor().getRecipes().add(recipe);
        recipe.getPictures().stream().map(picture -> pictureService.save(picture.getUrlPicture(), recipe)).collect(Collectors.toList());
        return recipesRepository.save(recipe);
    }
}
