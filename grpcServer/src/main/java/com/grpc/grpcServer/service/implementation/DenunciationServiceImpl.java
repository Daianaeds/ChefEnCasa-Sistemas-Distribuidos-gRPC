package com.grpc.grpcServer.service.implementation;

import com.grpc.grpcServer.entities.Denunciation;
import com.grpc.grpcServer.entities.User;
import com.grpc.grpcServer.entities.Recipe;
import com.grpc.grpcServer.port.in.soap.dtos.DenunciationDto;
import com.grpc.grpcServer.port.in.soap.mapper.DenunciationMapper;
import com.grpc.grpcServer.repositories.DenunciationRepository;
import com.grpc.grpcServer.service.DenunciationService;
import com.grpc.grpcServer.service.RecipesService;
import com.grpc.grpcServer.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Slf4j
@Service
public class DenunciationServiceImpl implements DenunciationService {

    @Autowired
    DenunciationRepository denunciationRepository;

    @Autowired
    RecipesService recipesService;

    @Autowired
    UserService userService;

    @Autowired
    DenunciationMapper denunciationMapper;

    @Override
    public void save(String username, int motive, int idRecipe) throws Exception {
        Recipe recipe = recipesService.findById(idRecipe);
        User user = userService.findByUsername(username);

        if(recipe == null || user == null)  throw new Exception("Los datos ingresados no son correctos. Intente nuevamente");

        denunciationRepository.save(Denunciation.builder()
                .motive(motive)
                .recipe(recipe)
                .user(user)
                .build());
    }

    @Transactional
    @Override
    public List<DenunciationDto> findList() {
        List<Denunciation> denunciationList = denunciationRepository.findAll();
        log.error(denunciationList.toString());
        log.info(String.valueOf(denunciationList.get(0).getUser()));

        List<DenunciationDto> denunciationDtoList =  denunciationMapper.denunciationListToDto( denunciationList );

        return denunciationDtoList;
    }

    @Override
    public void ignore(int idDenunciation) throws Exception {

    }

    @Override
    public void delete(int idDenunciation) throws Exception {

    }
}
