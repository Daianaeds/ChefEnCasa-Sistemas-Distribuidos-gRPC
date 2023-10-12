package com.grpc.grpcServer.service;

import com.grpc.grpcServer.entities.RecipeBook;
import com.grpc.grpcServer.port.in.soap.dtos.RecipeBookRequestCreateDto;

import java.util.List;


public interface RecipeBookService {

    void saveRecipeBook(RecipeBookRequestCreateDto request) throws Exception;

    void deleteRecipeToBook(int idRecipeBook) throws Exception;

    void addRecipeToBook(int idRecipeBook, int idRecipe) throws Exception;

    void deleteRecipeFromBook(int idRecipeBook, int idRecipe) throws Exception;

    List<RecipeBook> list(String username) throws Exception;

    RecipeBook findById(String username) throws Exception;

}
