package com.grpc.grpcServer.service;

import com.grpc.grpcServer.port.in.dtos.PopularDto;

import java.util.List;

public interface PopularService {
    void updateUser(List<PopularDto> popularDtoList);

    void updateRecipe(List<PopularDto> popularDtoList);

    int scoreUserById(int id) throws Exception;
}
