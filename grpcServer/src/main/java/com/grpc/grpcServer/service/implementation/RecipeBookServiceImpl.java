package com.grpc.grpcServer.service.implementation;

import com.grpc.grpcServer.entities.RecipeBook;
import com.grpc.grpcServer.entities.User;
import com.grpc.grpcServer.port.in.soap.dtos.RecipeBookRequestCreateDto;
import com.grpc.grpcServer.repositories.RecipeBookRepository;
import com.grpc.grpcServer.service.RecipeBookService;
import com.grpc.grpcServer.service.RecipesService;
import com.grpc.grpcServer.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.grpc.grpcServer.entities.Recipe;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RecipeBookServiceImpl implements RecipeBookService {

    @Autowired
    private RecipeBookRepository recipeBookRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RecipesService recipesService;



    public void saveRecipeBook(RecipeBookRequestCreateDto request) throws Exception {
      User user  = userService.findByUsername(request.getUsername());
      if(user == null || request.getName().isEmpty())throw new Exception("Usuario no registrado");
      RecipeBook recipeBook = RecipeBook.builder()
              .name(request.getName())
              .user(user)
              .build();

        recipeBookRepository.save(recipeBook);
    }


    public void deleteRecipeToBook(int idRecipeBook) throws Exception {

    }


    public void addRecipeToBook(int idRecipeBook, int idRecipe) throws Exception {
        Recipe recipe;
    }


    public void deleteRecipeFromBook(int idRecipeBook, int idRecipe) throws Exception {

    }


    public List<RecipeBook> list(String username) throws Exception {
        return null;
    }


    public RecipeBook findById(String username) throws Exception {
        return null;
    }
}
