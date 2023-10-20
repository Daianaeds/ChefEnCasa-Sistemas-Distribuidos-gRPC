package com.grpc.grpcServer.service.implementation;

import com.grpc.grpcServer.entities.RecipeBook;
import com.grpc.grpcServer.entities.User;
import com.grpc.grpcServer.port.in.soap.dtos.RecipeBookDto;
import com.grpc.grpcServer.port.in.soap.dtos.RecipeBookRequestCreateDto;
import com.grpc.grpcServer.port.in.soap.mapper.RecipeBookMapper;
import com.grpc.grpcServer.repositories.RecipeBookRepository;
import com.grpc.grpcServer.service.RecipeBookService;
import com.grpc.grpcServer.service.RecipesService;
import com.grpc.grpcServer.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.grpc.grpcServer.entities.Recipe;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeBookServiceImpl implements RecipeBookService {

    @Autowired
    private RecipeBookRepository recipeBookRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RecipesService recipesService;
    @Autowired
    private RecipeBookMapper recipeBookMapper;

    @Transactional
    public void saveRecipeBook(RecipeBookRequestCreateDto request) throws Exception {
      User user  = userService.findByUsername(request.getUsername());
      if(user == null || request.getName().isEmpty())throw new Exception(" Usuario no registrado");
      RecipeBook recipeBook = RecipeBook.builder()
              .name(request.getName())
              .user(user)
              .build();

        recipeBookRepository.save(recipeBook);
    }

    @Transactional
    public void deleteRecipeToBook(int idRecipeBook) throws Exception {
        RecipeBook recipeBook = recipeBookRepository.findById(idRecipeBook).orElseThrow(() -> new Exception(" Libro de recetas no encontrado"));
        recipeBookRepository.delete(recipeBook);
    }

    //Se recibe el flag para reutilizar el codigo y ver si elimina(cualquier numero) o agrega(1)
    @Transactional
    public void addOrRemoveRecipeToBook(int idRecipeBook, int idRecipe, int flag) throws Exception {
        RecipeBook recipeBook = recipeBookRepository.findById(idRecipeBook).orElseThrow(() -> new Exception(" Libro de recetas no encontrado"));
        Recipe recipe = recipesService.findById(idRecipe);

        if(recipe == null)throw new Exception(" Receta no registrada");

        if(flag == 1){
            Set<Recipe> recipeSet = recipeBook.getRecipes();
            recipeSet.add(recipe);
            recipeBook.setRecipes(recipeSet);

            recipeBookRepository.save(recipeBook);
        }else{
            Set<Recipe> recipeSet = recipeBook.getRecipes();
            recipeSet.remove(recipe);

            recipeBook.setRecipes(recipeSet);

            recipeBookRepository.save(recipeBook);
        }

    }

    public List<RecipeBookDto> list(String username){
        List<RecipeBook> recipeBookList = recipeBookRepository.findByUser(username);

        List<RecipeBookDto> recipeBookresponse = recipeBookMapper.recipeBookListToDto(recipeBookList);


        return recipeBookresponse;
    }


    public RecipeBookDto findById(int idRecipeBook) throws Exception {

        RecipeBook recipeBook = recipeBookRepository.findById(idRecipeBook).orElseThrow(() -> new Exception(" Libro de recetas no encontrado"));

        return recipeBookMapper.recipeBookToDto(recipeBook);
    }
}
