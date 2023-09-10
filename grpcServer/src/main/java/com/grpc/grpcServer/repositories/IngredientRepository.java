package com.grpc.grpcServer.repositories;

import com.grpc.grpcServer.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Serializable> {

    Ingredient findByNameIngredient(String name);
}
