package com.grpc.grpcServer.mapper;

import com.grpc.grpcServer.entities.Comment;
import com.grpc.grpcServer.port.in.kafka.dtos.CommentDto;
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
                .usuario(UserService.findByUsername(request.getUsername()))
                .receta(recipesService.findById(request.getIdRecipe()))
                .build();

    }

    com.grpc.grpcServer.Comment convertCommentToCommentG(com.grpc.grpcServer.entities.Comment request) {
        return com.grpc.grpcServer.Comment.newBuilder()
                .setComment(request.getComment())
                .setUsername(request.getUsuario().getUsername()).build();

    }

}
