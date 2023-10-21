package com.grpc.grpcServer.repositories;

import com.grpc.grpcServer.entities.RecipeBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface RecipeBookRepository extends JpaRepository<RecipeBook, Serializable> {

    @Query("select rb from RecipeBook rb " +
            " inner join rb.user u " +
            " where u.username = :username")
    List<RecipeBook> findByUser(String username);
}
