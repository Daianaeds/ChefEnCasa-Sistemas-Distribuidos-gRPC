package com.grpc.grpcServer.service;

import com.grpc.grpcServer.dto.CompleteRecipeDTO;
import com.grpc.grpcServer.dto.IncompleteRecipeDTO;
import com.grpc.grpcServer.entities.IncompleteRecipe;
import com.grpc.grpcServer.entities.Recipe;
import com.grpc.grpcServer.entities.User;
import com.grpc.grpcServer.exception.IncompleteRecipeNotFoundException;
import com.grpc.grpcServer.exception.UserNotFoundException;
import com.grpc.grpcServer.mapper.IncompleteRecipeMapper;
import com.grpc.grpcServer.mapper.RecipeMapper;
import com.grpc.grpcServer.repositories.IncompleteRecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DraftFileService {

    private IncompleteRecipeRepository incompleteRecipeRepository;
    private IncompleteRecipeMapper incompleteRecipeMapper;
    private UserService userService;
    private RecipeMapper recipeMapper;
    private RecipesService recipesService;

    @Autowired
    public DraftFileService(IncompleteRecipeRepository incompleteRecipeRepository,
                            IncompleteRecipeMapper incompleteRecipeMapper,
                            UserService userService,
                            RecipeMapper recipeMapper,
                            RecipesService recipesService) {
        this.incompleteRecipeRepository = incompleteRecipeRepository;
        this.incompleteRecipeMapper = incompleteRecipeMapper;
        this.userService = userService;
        this.recipeMapper = recipeMapper;
        this.recipesService = recipesService;
    }

    @Transactional
    public void saveFileDraft(List<IncompleteRecipeDTO> incompleteRecipes, String username) throws UserNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("El usuario ingresado no existe");
        }
        List<IncompleteRecipe> recipeList = incompleteRecipeMapper.mapToIncompleteRecipes(incompleteRecipes, user);
        incompleteRecipeRepository.saveAll(recipeList);
    }

    @Transactional
    public List<IncompleteRecipeDTO> getAllIncompleteRecipes(String username) throws Exception {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("El usuario ingresado no existe");
        }
        List<IncompleteRecipe> incompleteRecipes = incompleteRecipeRepository.findAllByUser_username(username);
        return incompleteRecipeMapper.mapToIncompleteRecipesDTO(incompleteRecipes);
    }

    @Transactional
    public void deleteIncompleteRecipes(int id) {
        incompleteRecipeRepository.deleteById(id);
    }

    @Transactional
    public IncompleteRecipeDTO getIncompleteRecipe(int id) {
        IncompleteRecipe incompleteRecipe = incompleteRecipeRepository.getReferenceById(id);
        return incompleteRecipeMapper.mapToIncompleteRecipes(incompleteRecipe);
    }

    @Transactional
    public String saveRecipe(CompleteRecipeDTO completeRecipe) throws Exception {
        IncompleteRecipe incompleteRecipe = incompleteRecipeRepository.findById(completeRecipe.getIdIncompleteRecipe())
                .orElseThrow(() -> new IncompleteRecipeNotFoundException(String.format("El id de borrador %d no existe", completeRecipe.getIdIncompleteRecipe())));

        User user = userService.findByUsername(completeRecipe.getAuth().getUsername());
        if (user == null) {
            throw new UserNotFoundException("El usuario ingresado no existe");
        }

        Recipe recipe = recipeMapper.mapToRecipe(completeRecipe);
        recipesService.saveRecipe(recipe);
        incompleteRecipeRepository.deleteById(completeRecipe.getIdIncompleteRecipe());
        return "Se guardó la receta OK";
    }
}
