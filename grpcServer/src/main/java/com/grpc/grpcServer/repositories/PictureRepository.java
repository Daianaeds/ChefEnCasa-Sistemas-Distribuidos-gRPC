package com.grpc.grpcServer.repositories;

import com.grpc.grpcServer.entities.Comment;
import com.grpc.grpcServer.entities.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Serializable> {

    @Query("SELECT p FROM Picture p JOIN p.recipe r WHERE r.id = :id")
    List<Picture> findByIdRecipe(int id);
}
