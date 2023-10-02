package com.grpc.grpcServer.service.implementation;

import com.grpc.grpcServer.entities.Comment;
import com.grpc.grpcServer.mapper.CommentMapper;
import com.grpc.grpcServer.port.in.dtos.CommentDto;
import com.grpc.grpcServer.repositories.CommentRepository;
import com.grpc.grpcServer.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CommitServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    CommentRepository commentRepository;

    @Override
    public void saveList(List<CommentDto> comments) {

        List<Comment> commentList = comments.stream()
                .map(comment -> commentMapper.convertCommentKafkaToCommentEntity(comment))
                .collect(Collectors.toList());
        commentList.forEach(comment -> commentRepository.save(comment));

        commentList.forEach(comment -> log.info(comment.toString()));

    }

    @Override
    public List<Comment> findByIdRecipe(int id) {
        return commentRepository.findByIdRecipe(id);
    }
}
