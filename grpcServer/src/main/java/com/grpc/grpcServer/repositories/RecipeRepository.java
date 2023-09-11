package com.grpc.grpcServer.repositories;

import com.grpc.grpcServer.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Serializable> {

    @Query("select r from Recipe r " +
            " inner join r.category c " +
            " inner join r.ingredients i " +
            " where (r.title LIKE LOWER(concat('%',:title,'%'))  OR :title IS NULL)" +
            " and (r.timeMinutes = :timeMinutes OR :timeMinutes = 0) " +
            " and  (c.nameCategory LIKE LOWER(concat('%',:nameCategory,'%')) OR :nameCategory IS NULL) " +
            " and (i.nameIngredient LIKE LOWER(concat('%',:nameIngredient,'%')) OR :nameIngredient IS NULL) GROUP BY r.id")
    List<Recipe> findByFilter(
            @Param("title") String title,
            @Param("nameCategory") String nameCategory,
            @Param("nameIngredient") String nameIngredient,
            @Param("timeMinutes") int timeMinutes
    );
}


