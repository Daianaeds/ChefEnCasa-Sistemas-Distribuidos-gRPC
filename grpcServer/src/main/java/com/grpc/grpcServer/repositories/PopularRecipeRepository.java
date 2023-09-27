package com.grpc.grpcServer.repositories;

import com.grpc.grpcServer.entities.PopularRecipe;
import com.grpc.grpcServer.entities.PopularUser;
import com.grpc.grpcServer.entities.Recipe;
import com.grpc.grpcServer.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface PopularRecipeRepository extends JpaRepository<PopularRecipe, Serializable> {
    PopularRecipe findByRecipe(Recipe recipe);

    @Query("SELECT ps FROM PopularRecipe ps JOIN ps.recipe r WHERE r.id = :id")
    PopularRecipe findByIdRecipe(int id);
}
