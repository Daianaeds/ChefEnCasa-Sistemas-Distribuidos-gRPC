package com.grpc.grpcServer.service;

import com.grpc.grpcServer.port.in.dtos.CommentDto;

import java.util.List;

public interface CommentService {

    void saveList(List<CommentDto> comments);
}
