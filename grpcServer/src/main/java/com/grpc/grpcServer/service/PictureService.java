package com.grpc.grpcServer.service;

import com.grpc.grpcServer.entities.Picture;

import java.util.List;

public interface PictureService {

   Picture save(String urlPicture);
}
