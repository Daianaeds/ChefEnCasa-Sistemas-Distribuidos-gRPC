package com.grpc.grpcServer.repositories;

import com.grpc.grpcServer.entities.Comment;
import com.grpc.grpcServer.entities.PopularRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Serializable> {

    @Query("SELECT c FROM Comment c JOIN c.receta r WHERE r.id = :id")
    List<Comment> findByIdRecipe(int id);
}
