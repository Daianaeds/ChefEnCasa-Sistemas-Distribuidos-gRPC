package com.grpc.grpcServer.service.implementation;

import com.grpc.grpcServer.entities.PopularUser;
import com.grpc.grpcServer.entities.User;
import com.grpc.grpcServer.port.in.dtos.PopularDto;
import com.grpc.grpcServer.repositories.PopularRecipeRepository;
import com.grpc.grpcServer.repositories.PopularUserRepository;
import com.grpc.grpcServer.service.PopularService;
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

    @Override
    public void updateRecipe(List<PopularDto> popularDtoList) {
        //logica para calcular promedio popularidad
        popularDtoList.forEach(popular -> log.info("Receta:  " + popular.toString()));
    }

    @Override
    public int scoreUserById(int id) throws Exception {
        PopularUser popularUser =  popularUserRepository.findByIdUser(id);
        if(popularUser == null) throw new Exception("El usuario aun no tiene popularidad");
        return popularUser.getScore();
    }


}
