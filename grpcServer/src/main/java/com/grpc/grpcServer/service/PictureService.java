package com.grpc.grpcServer.service;

import com.grpc.grpcServer.entities.Picture;
import com.grpc.grpcServer.entities.Recipe;


import java.util.List;

public interface PictureService {
   Picture save(String urlPicture, Recipe recipe);

   List<Picture> findByIdRecipe(int id);

}
