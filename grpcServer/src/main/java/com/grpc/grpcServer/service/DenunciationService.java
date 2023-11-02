package com.grpc.grpcServer.service;

import com.grpc.grpcServer.RecipeRequest;
import com.grpc.grpcServer.entities.Denunciation;
import com.grpc.grpcServer.entities.Ingredient;
import com.grpc.grpcServer.port.in.soap.dtos.DenunciationDto;
import org.apache.kafka.common.protocol.types.Field;

import java.util.List;

public interface DenunciationService {

    void save(String username, int motive, int idRecipe)throws Exception;
    
    List<DenunciationDto> findList();

    void ignore(int idDenunciation) throws Exception;

    void delete(int idRecipe) throws Exception;
}
