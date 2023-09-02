package com.grpc.grpcServer.service.implementation;

import com.grpc.grpServer.RecipeRequest;
import com.grpc.grpcServer.entities.Ingredient;
import com.grpc.grpcServer.repositories.IngredientRepository;
import com.grpc.grpcServer.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    IngredientRepository ingredientRepository;

    @Override
    public Ingredient find(String name) {

        Ingredient ingredient = ingredientRepository.findByNameIngredient(name.toLowerCase());
        //si no existe crearlo
        if(ingredient == null){
            ingredient = save(name.toLowerCase());
        }

        return ingredient;
    }

    @Override
    public List<Ingredient> findListIngredient(RecipeRequest request) throws Exception {
        //si no envia ingredientes levanta excepcion
        if(request.getIngredientsList() == null) throw new Exception("Debe ingresar al menos un ingrediente.");
        List<Ingredient> ingredientList = request.getIngredientsList().stream().map(Ingredient ->  find(Ingredient.getNombre())).collect(Collectors.toList());
        return ingredientList;
    }

    private Ingredient save(String name){
        Ingredient ingredient = Ingredient.builder()
                                    .nameIngredient(name)
                                    .build();

        return ingredientRepository.save(ingredient);
    }
}
