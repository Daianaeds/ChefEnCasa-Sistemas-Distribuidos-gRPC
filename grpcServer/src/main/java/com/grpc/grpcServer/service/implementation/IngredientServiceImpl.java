package com.grpc.grpcServer.service.implementation;

import com.grpc.grpcServer.RecipeRequest;
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
    public Ingredient find(String name, String amount) {
        return save(name.toLowerCase(), amount);
    }

    @Override
    public List<Ingredient> findListIngredient(RecipeRequest request) throws Exception {
        //si no envia ingredientes levanta excepcion
        if (request.getIngredientsList().isEmpty()) throw new Exception("Debe ingresar al menos un ingrediente.");
        List<Ingredient> ingredientList = request.getIngredientsList().stream().map(Ingredient -> find(Ingredient.getNombre(),Ingredient.getCantidad())).collect(Collectors.toList());
        return ingredientList;
    }

    private Ingredient save(String name, String amount) {
        Ingredient ingredient = Ingredient.builder()
                .nameIngredient(name)
                .amount(amount)
                .build();

        return ingredientRepository.save(ingredient);
    }
}
