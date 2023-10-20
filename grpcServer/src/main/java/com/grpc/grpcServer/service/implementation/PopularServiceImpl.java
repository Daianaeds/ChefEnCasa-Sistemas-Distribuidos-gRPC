package com.grpc.grpcServer.service.implementation;

import com.grpc.grpcServer.entities.PopularRecipe;
import com.grpc.grpcServer.entities.PopularUser;
import com.grpc.grpcServer.entities.Recipe;
import com.grpc.grpcServer.entities.User;
import com.grpc.grpcServer.port.in.kafka.dtos.PopularDto;
import com.grpc.grpcServer.repositories.PopularRecipeRepository;
import com.grpc.grpcServer.repositories.PopularUserRepository;
import com.grpc.grpcServer.service.PopularService;
import com.grpc.grpcServer.service.RecipesService;
import com.grpc.grpcServer.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Slf4j
@Service
public class PopularServiceImpl implements PopularService {

   @Autowired
    PopularRecipeRepository popularRecipeRepository;

    @Autowired
    PopularUserRepository popularUserRepository;

    @Autowired
    RecipesService recipeService;

    @Autowired
    UserService userService;

    @Transactional
    @Override
    public void updateUser(List<PopularDto> popularDtoList){
        //recorre la lista
        for (PopularDto popularDto : popularDtoList) {

            //busca el usuario asociado
            User user = userService.findByUsername(popularDto.getIdentifier());

            //si el usuario no existe levanta excepcion
            if(user != null) {

                //busca si existe en la tabla la popularidad para obtener los datos ya existentes
                PopularUser popularUser = popularUserRepository.findByUser(user);

                //si existe suma 1 la cantidad y suma o resta el puntaje de lo que se envia
                if (popularUser != null) {
                    int score = popularUser.getScore() + popularDto.getScore();
                    popularUser.setScore(score);
                    int amount = popularUser.getAmount() + 1;
                    popularUser.setAmount(amount);

                    //actualiza
                    popularUserRepository.save(popularUser);
                    log.info("Usuario: " + popularUser.toString());

                } else {
                    //si no existe crea un registro con ese usuario y con datos enviados
                    PopularUser popularUserNew = PopularUser.builder()
                            .amount(1)
                            .user(user)
                            .score(popularDto.getScore())
                            .build();
                    popularUserRepository.save(popularUserNew);
                    log.info("Usuario nuevo: " + popularUserNew.toString());
                }
            }else {
                log.error("Usuario: error de guardado, usuario no registrado");
            }
        }
    }
    @Transactional
    @Override
    public void updateRecipe(List<PopularDto> popularDtoList) {

        for (PopularDto popularDto : popularDtoList) {

            Recipe recipe = recipeService.findById(Integer.parseInt(popularDto.getIdentifier()));

            if(recipe != null) {

                PopularRecipe popularRecipe = popularRecipeRepository.findByRecipe(recipe);


                if (popularRecipe != null) {

                    int score = popularRecipe.getScore() + popularDto.getScore();
                    popularRecipe.setScore(score);
                    int amount = popularRecipe.getAmount() + 1;
                    popularRecipe.setAmount(amount);

                    popularRecipeRepository.save(popularRecipe);
                    log.info("Recipe : " + popularRecipe.toString());

                } else {
                    PopularRecipe popularRecipeNew = PopularRecipe.builder()
                            .amount(1)
                            .recipe(recipe)
                            .score(popularDto.getScore())
                            .build();
                    popularRecipeRepository.save(popularRecipeNew);
                    log.info("Receta nueva: " + popularRecipeNew.toString());
                }
            }else {
                log.error("Receta: error de guardado, receta no registrada");
            }
        }
    }


}
