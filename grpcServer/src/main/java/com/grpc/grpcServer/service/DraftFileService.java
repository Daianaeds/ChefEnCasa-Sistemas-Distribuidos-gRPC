package com.grpc.grpcServer.service;

import com.grpc.grpcServer.dto.IncompleteRecipeDTO;
import com.grpc.grpcServer.entities.IncompleteRecipe;
import com.grpc.grpcServer.entities.User;
import com.grpc.grpcServer.exception.UserNotFoundException;
import com.grpc.grpcServer.mapper.IncompleteRecipeMapper;
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

    @Autowired
    public DraftFileService(IncompleteRecipeRepository incompleteRecipeRepository,
                            IncompleteRecipeMapper incompleteRecipeMapper, UserService userService) {
        this.incompleteRecipeRepository = incompleteRecipeRepository;
        this.incompleteRecipeMapper = incompleteRecipeMapper;
        this.userService = userService;
    }

    @Transactional
    public void saveFileDraft(List<IncompleteRecipeDTO> incompleteRecipes, String username) throws UserNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("El usuario ingresado no existe");
        }
          List<IncompleteRecipe> recipeList = incompleteRecipeMapper.mapToIncompleteRecipeDTO(incompleteRecipes, user);
        incompleteRecipeRepository.saveAll(recipeList);
    }

    @Transactional
    public List<IncompleteRecipeDTO> getAllIncompleteRecipes(String username) throws Exception {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("El usuario ingresado no existe");
        }
        List<IncompleteRecipe> incompleteRecipes = incompleteRecipeRepository.findAllByUser_username(username);
        return incompleteRecipeMapper.mapToIncompleteRecipe(incompleteRecipes);
    }
}
