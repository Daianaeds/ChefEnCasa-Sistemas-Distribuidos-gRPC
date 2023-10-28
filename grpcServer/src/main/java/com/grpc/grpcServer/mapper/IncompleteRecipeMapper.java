package com.grpc.grpcServer.mapper;

import com.grpc.grpcServer.dto.IncompleteRecipeDTO;
import com.grpc.grpcServer.entities.IncompleteRecipe;
import com.grpc.grpcServer.entities.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class IncompleteRecipeMapper {

    @Transactional
    public List<IncompleteRecipe> mapToIncompleteRecipeDTO(List<IncompleteRecipeDTO> recipes, User user) {
        List<IncompleteRecipe> listIncompleteRecipe = new ArrayList<>();
        for (IncompleteRecipeDTO incompleteRecipeDTO : recipes) {
            IncompleteRecipe incompleteRecipe = IncompleteRecipe.builder()
                    .title(incompleteRecipeDTO.getTitle())
                    .description(incompleteRecipeDTO.getDescription())
                    .category(incompleteRecipeDTO.getCategory())
                    .time(incompleteRecipeDTO.getTime())
                    .user(user)
                    .build();

            listIncompleteRecipe.add(incompleteRecipe);
        }
        return listIncompleteRecipe;
    }

    @Transactional
    public List<IncompleteRecipeDTO> mapToIncompleteRecipe(List<IncompleteRecipe> recipes) {
        List<IncompleteRecipeDTO> listIncompleteRecipe = new ArrayList<>();
        for (IncompleteRecipe incompleteRecipe : recipes) {
            IncompleteRecipeDTO incompleteRecipeDTO = IncompleteRecipeDTO.builder()
                    .title(incompleteRecipe.getTitle())
                    .description(incompleteRecipe.getDescription())
                    .category(incompleteRecipe.getCategory())
                    .time(incompleteRecipe.getTime())
                    .username(incompleteRecipe.getUser().getUsername())
                    .build();

            listIncompleteRecipe.add(incompleteRecipeDTO);
        }
        return listIncompleteRecipe;
    }

}
