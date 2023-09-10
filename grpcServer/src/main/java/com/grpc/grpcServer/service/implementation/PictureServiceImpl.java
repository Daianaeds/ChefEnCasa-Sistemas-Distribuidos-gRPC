package com.grpc.grpcServer.service.implementation;

import com.grpc.grpcServer.entities.Picture;
import com.grpc.grpcServer.repositories.PictureRepository;
import com.grpc.grpcServer.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    PictureRepository pictureRepository;

    @Override
    public Picture save(String urlPicture) {
        Picture picture = Picture.builder()
                .urlPicture(urlPicture)
                .build();
        return pictureRepository.save(picture);
    }
}
