package com.grpc.grpcServer.service.implementation;

import com.grpc.grpcServer.entities.Picture;
import com.grpc.grpcServer.entities.Recipe;
import com.grpc.grpcServer.repositories.PictureRepository;
import com.grpc.grpcServer.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    PictureRepository pictureRepository;

    @Override
    public Picture save(String urlPicture, Recipe recipe) {
        Picture picture = Picture.builder()
                .urlPicture(urlPicture)
                .recipe(recipe)
                .build();
        return pictureRepository.save(picture);
    }

    @Override
    public List<Picture> findByIdRecipe(int id) {
        return pictureRepository.findByIdRecipe(id);
    }
}
