package com.grpc.grpcServer.mapper;

import com.grpc.grpcServer.RecipeRequest;
import com.grpc.grpcServer.RecipeResponse;
import com.grpc.grpcServer.RecipeResponseBasic;
import com.grpc.grpcServer.RecipeResponseBasicList;
import com.grpc.grpcServer.dto.CompleteRecipeDTO;
import com.grpc.grpcServer.dto.PictureDTO;
import com.grpc.grpcServer.entities.*;
import com.grpc.grpcServer.repositories.PopularRecipeRepository;
import com.grpc.grpcServer.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    CommentService commentService;

    @Autowired
    PopularRecipeRepository popularRecipeRepository;

    public Recipe convertRecipeRequestToRecipes(RecipeRequest request) throws Exception {

        List<Ingredient> ingredientList = ingredientService.findListIngredient(request);

        Category category = categoryService.find(request.getNameCategory());

        User user = userService.find(request.getAuth());

        Recipe recipe = Recipe.builder()
                .ingredients(ingredientList)
                .category(category)
                .author(user)
                .description(request.getDescription())
                .title(request.getTitle())
                .steps(request.getSteps())
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
                .addAllPictures(request.getPictures().stream().map(pictureE -> pictureMapper.convertPictureToPictureG(pictureE)).collect(Collectors.toList()))
                .setTimeMinutes(request.getTimeMinutes())
                .setId(request.getId())
                .build();

        return response;
    }

    private Integer scoreRecipeById(int id) throws Exception {
        PopularRecipe popularRecipe = popularRecipeRepository.findByIdRecipe(id);
        if (popularRecipe == null) {
            return 0;
        }
        //saca prmedio entre el score y la cantidad de peticiones
        int score = popularRecipe.getScore() / popularRecipe.getAmount();
        return score;
    }

    @Transactional
    public RecipeResponseBasicList convertRecipetoRecipeResponseBasicList(List<Recipe> recipes) {
        RecipeResponseBasicList.Builder responseBuilder = RecipeResponseBasicList.newBuilder();
        int score = 0;
        for (Recipe userRecipe : recipes) {

            try {
                score = scoreRecipeById(userRecipe.getId());

            } catch (Exception e) {
                log.error("Error en obtener la receta en RecipeMapper : ", e.getMessage());
            }
            List<com.grpc.grpcServer.entities.Comment> comments = commentService.findByIdRecipe(userRecipe.getId());
            List<Picture> pictures = pictureService.findByIdRecipe(userRecipe.getId());

            RecipeResponseBasic response = RecipeResponseBasic.newBuilder()
                    .addAllIngredients(userRecipe.getIngredients().stream().map(ingredientE -> ingredientMapper.convertIngredientToIngredientG(ingredientE)).collect(Collectors.toList()))
                    .setNameCategory(userRecipe.getCategory().getNameCategory())
                    .setDescription(userRecipe.getDescription())
                    .setTitle(userRecipe.getTitle())
                    .setSteps(userRecipe.getSteps())
                    .addAllPictures(pictures.stream().map(pictureE -> pictureMapper.convertPictureToPictureG(pictureE)).collect(Collectors.toList()))
                    .setTimeMinutes(userRecipe.getTimeMinutes())
                    .setScore(score)
                    .setUsername(userRecipe.getAuthor().getUsername())
                    .addAllComments(comments.stream().map(commentE -> commentMapper.convertCommentToCommentG(commentE)).collect(Collectors.toList()))
                    .setId(userRecipe.getId())
                    .build();

            responseBuilder.addRecipe(response);
            score = 0;
        }
        return OrderByScore(responseBuilder);
    }

    private RecipeResponseBasicList OrderByScore(RecipeResponseBasicList.Builder responseBuilder) {
        // Ordenar la lista de RecipeResponseBasic de mayor a menor score
        List<RecipeResponseBasic> sortedList = responseBuilder.getRecipeList()
                .stream()
                .sorted(Comparator.comparingInt(RecipeResponseBasic::getScore).reversed())
                .collect(Collectors.toList());

        // Construir un nuevo objeto RecipeResponseBasicList con la lista ordenada
        RecipeResponseBasicList sortedRecipeList = RecipeResponseBasicList.newBuilder()
                .addAllRecipe(sortedList)
                .build();

        return sortedRecipeList;
    }

    public RecipeResponseBasic convertRecipeToRecipeResponseBasic(Recipe request) throws Exception {
        List<com.grpc.grpcServer.entities.Comment> comments = commentService.findByIdRecipe(request.getId());
        int score = scoreRecipeById(request.getId());

        RecipeResponseBasic response = RecipeResponseBasic.newBuilder()
                .setId(request.getId())
                .setTitle(request.getTitle())
                .setDescription(request.getDescription())
                .setSteps(request.getSteps())
                .setTimeMinutes(request.getTimeMinutes())
                .setNameCategory(request.getCategory().getNameCategory())
                .addAllIngredients(request.getIngredients().stream().map(ingredientE -> ingredientMapper.convertIngredientToIngredientG(ingredientE)).collect(Collectors.toList()))
                .addAllPictures(request.getPictures().stream().map(pictureE -> pictureMapper.convertPictureToPictureG(pictureE)).collect(Collectors.toList()))
                .addAllComments(comments.stream().map(commentE -> commentMapper.convertCommentToCommentG(commentE)).collect(Collectors.toList()))
                .setUsername(request.getAuthor().getUsername())
                .setScore(score)
                .build();

        return response;
    }

    public Recipe mapToRecipe(CompleteRecipeDTO completeRecipe) throws Exception {
        List<Ingredient> ingredientList = ingredientService.findListIngredient(completeRecipe);
        Category category = categoryService.find(completeRecipe.getCategory());
        User user = userService.findByUsername(completeRecipe.getAuth().getUsername());
        List<Picture> pictures = mapToPictures(completeRecipe.getPictures());

        Recipe recipe = Recipe.builder()
                .ingredients(ingredientList)
                .category(category)
                .author(user)
                .description(completeRecipe.getDescription())
                .title(completeRecipe.getTitle())
                .steps(completeRecipe.getSteps())
                .timeMinutes(completeRecipe.getTimeMinutes())
                .pictures(pictures)
                .build();

        return recipe;
    }

    private List<Picture> mapToPictures(List<PictureDTO> picturesDTO) {
        List<Picture> pictures = new ArrayList<>();
        for (PictureDTO p : picturesDTO) {
            Picture picture = Picture.builder()
                    .urlPicture(p.getUrl()).build();
            pictures.add(picture);
        }
        return pictures;
    }
}
