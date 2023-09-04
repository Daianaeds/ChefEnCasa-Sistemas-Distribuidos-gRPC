package com.grpc.grpcServer.repositories;

import com.grpc.grpcServer.entities.Recipe;
import com.grpc.grpcServer.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Serializable> {
}/*
    @Query(value = "select * from recipies r where (LOWER(r.title) like LOWER(concat('%', :title, '%')) or :title is null) and (r.categoría =:categoría or :categoría is null) " +
            "and (LOWER(r.ingredientes) LIKE LOWER(concat('%', :ingredientes, '%')) OR :ingredientes IS NULL) " +
            "and (LOWER(r.time) LIKE LOWER(concat('%', :time, '%')) OR :time IS NULL) " +
            countQuery = "select count(*) from recipies r where (LOWER(r.title) like LOWER(concat('%', :title, '%')) or :title is null) and (r.categoría =:categoría or :categoría is null) " +
            "and (LOWER(r.ingredientes) LIKE LOWER(concat('%', :ingredientes, '%')) OR :ingredientes IS NULL) " +
            "and (LOWER(r.time) LIKE LOWER(concat('%', :time, '%')) OR :time IS NULL)" +
                    "and (b.pages = :pages or :pages is null)", nativeQuery = true)
    Page<Recipe> findByTitle(@Param("title")String title,
                                                                           @Param("categoría")String categoría,
                                                                           @Param("ingredientes")String ingredientes,
                                                                           @Param("time")int time,
                                                                           Pageable pageable);

pudiendo filtrar la búsqueda por ninguno, alguno o
todos los criterios: categoría, título o parte del título, ingredientes, rango de tiempo de preparación.
*/

//revisar query