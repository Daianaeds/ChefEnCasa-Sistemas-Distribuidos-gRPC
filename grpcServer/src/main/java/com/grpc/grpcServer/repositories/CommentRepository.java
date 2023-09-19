package com.grpc.grpcServer.repositories;

import com.grpc.grpcServer.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
@Repository
public interface CommentRepository extends JpaRepository<Comment, Serializable> {
}
