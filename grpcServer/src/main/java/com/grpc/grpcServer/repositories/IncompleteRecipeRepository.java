package com.grpc.grpcServer.repositories;

import com.grpc.grpcServer.entities.IncompleteRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncompleteRecipeRepository extends JpaRepository<IncompleteRecipe, Integer> {

    List<IncompleteRecipe> findAllByUser_username(String username);
}
