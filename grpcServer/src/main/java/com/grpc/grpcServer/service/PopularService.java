package com.grpc.grpcServer.service;

import com.grpc.grpcServer.port.in.kafka.dtos.PopularDto;

import java.util.List;

public interface PopularService {
    void updateUser(List<PopularDto> popularDtoList);

    void updateRecipe(List<PopularDto> popularDtoList);

}
