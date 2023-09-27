package com.grpc.grpcServer.service;

import com.grpc.grpcServer.entities.Comment;
import com.grpc.grpcServer.port.in.dtos.CommentDto;

import java.util.List;

public interface CommentService {

    void saveList(List<CommentDto> comments);

    List<Comment> findByIdRecipe(int id);
}
