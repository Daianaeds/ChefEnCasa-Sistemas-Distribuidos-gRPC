package com.grpc.grpcServer.port.in.soap.mapper;

import com.grpc.grpcServer.entities.RecipeBook;
import com.grpc.grpcServer.port.in.soap.dtos.RecipeBookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class RecipeBookMapper {

    @Autowired
    RecipeSoapMapper recipeSoapMapper;
    public List<RecipeBookDto> recipeBookListToDto(List<RecipeBook> recipeBookList) {
        List<RecipeBookDto> recipeBookDtoList = new ArrayList<>() ;

        for (RecipeBook recipeBook : recipeBookList) {
            RecipeBookDto recipeBookDto = recipeBookToDto(recipeBook);
            recipeBookDtoList.add(recipeBookDto);
        }

        return recipeBookDtoList;
    }

    public RecipeBookDto recipeBookToDto(RecipeBook recipeBook) {

       return  RecipeBookDto.builder()
                .id(recipeBook.getId())
                .nameBook(recipeBook.getName())
                .recipeList(recipeBook.getRecipes().stream().map(recipe ->  recipeSoapMapper.recipeToDto(recipe)).collect(Collectors.toList()))
                .build();
    }
}
