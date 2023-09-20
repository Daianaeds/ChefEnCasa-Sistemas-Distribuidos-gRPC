package com.grpc.grpcServer.service.implementation;

import com.grpc.grpcServer.port.in.dtos.PopularDto;
import com.grpc.grpcServer.service.PopularService;

import java.util.List;

public class PopularServiceImpl implements PopularService {
    @Override
    public void updateUser(List<PopularDto> popularDtoList) {
        //logica para calcular promedio popularidad
    }

    @Override
    public void updateRecipe(List<PopularDto> popularDtoList) {
        //logica para calcular promedio popularidad
    }
}
