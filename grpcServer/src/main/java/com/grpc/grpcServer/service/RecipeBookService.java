package com.grpc.grpcServer.service;

import com.grpc.grpcServer.entities.RecipeBook;
import com.grpc.grpcServer.port.in.soap.dtos.RecipeBookDto;
import com.grpc.grpcServer.port.in.soap.dtos.RecipeBookRequestCreateDto;

import java.util.List;


public interface RecipeBookService {

    void saveRecipeBook(RecipeBookRequestCreateDto request) throws Exception;

    void deleteRecipeToBook(int idRecipeBook) throws Exception;

    void addOrRemoveRecipeToBook(int idRecipeBook, int idRecipe, int flag) throws Exception;

    List<RecipeBookDto> list(String username);

    RecipeBookDto findById(int idRecipeBook) throws Exception;

}
