package com.grpc.grpcServer.mapper;

import org.springframework.stereotype.Service;

@Service
public class PictureMapper {

    com.grpc.grpcServer.Picture convertIngredientToIngredientG(com.grpc.grpcServer.entities.Picture request){
        return com.grpc.grpcServer.Picture.newBuilder()
                .setUrl(request.getUrlPicture()).build();

    }

}
