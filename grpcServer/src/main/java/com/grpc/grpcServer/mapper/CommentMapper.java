package com.grpc.grpcServer.mapper;

import com.grpc.grpcServer.entities.Comment;
import com.grpc.grpcServer.port.in.dtos.CommentDto;
import com.grpc.grpcServer.service.RecipesService;
import com.grpc.grpcServer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentMapper {

    @Autowired
    UserService UserService;
    @Autowired
    RecipesService recipesService;

        public Comment convertCommentKafkaToCommentEntity(CommentDto request) {
        return Comment.builder()
                .comment(request.getComment())
                .usuario(UserService.findByUsername(request.getUsername()))//falta verificar que exista
                .receta(recipesService.findById(request.getIdRecipe()))//falta verificar que exista
                .build();

    }

}
