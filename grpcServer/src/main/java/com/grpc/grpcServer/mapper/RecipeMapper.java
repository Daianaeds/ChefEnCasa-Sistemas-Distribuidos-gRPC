package com.grpc.grpcServer.mapper;

import com.grpc.grpcServer.*;
import com.grpc.grpcServer.entities.*;
import com.grpc.grpcServer.entities.Ingredient;
import com.grpc.grpcServer.entities.Picture;
import com.grpc.grpcServer.service.CategoryService;
import com.grpc.grpcServer.service.IngredientService;
import com.grpc.grpcServer.service.PictureService;
import com.grpc.grpcServer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeMapper {

    @Autowired
    IngredientService ingredientService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    @Autowired
    PictureService pictureService;

    @Autowired
    IngredientMapper ingredientMapper;
    @Autowired
    PictureMapper pictureMapper;
    @Autowired
    UserMapper userMapper;

    public Recipe convertRecipeRequestToRecipes(RecipeRequest request) throws Exception {

        List<Ingredient> ingredientList = ingredientService.findListIngredient(request);

        Category category = categoryService.find(request.getNameCategory());

        List<Picture> pictureList = request.getPicturesList().stream().map(picture -> pictureService.save(picture.getUrl())).collect(Collectors.toList());

        User user = userService.find(request.getAuth());

        Recipe recipe = Recipe.builder()
                .ingredients(ingredientList)
                .category(category)
                .author(user)
                .description(request.getDescription())
                .title(request.getTitle())
                .steps(request.getSteps())
                .pictures(pictureList)
                .timeMinutes(request.getTimeMinutes())
                .build();

        return recipe;
    }

    private void validationsRequest(RecipeRequest request) throws Exception {
        validatorString(request.getDescription(), "Description");
        validatorString(request.getTitle(), "Title");
        validatorString(request.getSteps(), "Steps");
        int timeMin = 0;
        if (request.getTimeMinutes() <= timeMin)
            throw new Exception(" El tiempo aproximado no puede ser menor de 5 minutos.");
    }

    private void validatorString(String value, String key) throws Exception {
        if (value.isEmpty()) throw new Exception(" {} no puede estar vacio" + key);
    }

    public RecipeResponse convertRecipeToRecipeResponse(Recipe request) throws Exception {
        RecipeResponse response = RecipeResponse.newBuilder()
                .addAllIngredients(request.getIngredients().stream().map(ingredientE -> ingredientMapper.convertIngredientToIngredientG(ingredientE)).collect(Collectors.toList()))
                .setNameCategory(request.getCategory().getNameCategory())
                .setAuth(userMapper.convertUsertoUserAuth(request.getAuthor()))
                .setDescription(request.getDescription())
                .setTitle(request.getTitle())
                .setSteps(request.getSteps())
                .addAllPictures(request.getPictures().stream().map(pictureE -> pictureMapper.convertIngredientToIngredientG(pictureE)).collect(Collectors.toList()))
                .setTimeMinutes(request.getTimeMinutes())
                .setId(request.getId())
                .build();

        return response;
    }

    public RecipeResponseBasicList convertRecipetoRecipeResponseBasicList(List<Recipe> recipes) {
        RecipeResponseBasicList.Builder responseBuilder = RecipeResponseBasicList.newBuilder();

        for (Recipe userRecipe : recipes) {
            RecipeResponseBasic response = RecipeResponseBasic.newBuilder()
                    .addAllIngredients(userRecipe.getIngredients().stream().map(ingredientE -> ingredientMapper.convertIngredientToIngredientG(ingredientE)).collect(Collectors.toList()))
                    .setNameCategory(userRecipe.getCategory().getNameCategory())
                    .setDescription(userRecipe.getDescription())
                    .setTitle(userRecipe.getTitle())
                    .setSteps(userRecipe.getSteps())
                    .addAllPictures(userRecipe.getPictures().stream().map(pictureE -> pictureMapper.convertIngredientToIngredientG(pictureE)).collect(Collectors.toList()))
                    .setTimeMinutes(userRecipe.getTimeMinutes())
                    .setId(userRecipe.getId())
            //        .setUserResponse(convertUsertoUserResponse(userRecipe.getAuthor()))
                    .build();
            responseBuilder.addRecipe(response);
        }
        return responseBuilder.build();
    }

    public UserResponse convertUsertoUserResponse(User user) {

        UserResponse userResponse = UserResponse.newBuilder()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .build();
        return userResponse;
    }
}
