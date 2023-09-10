package com.grpc.grpcServer.repositories;

import com.grpc.grpcServer.entities.Recipe;
import com.grpc.grpcServer.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Serializable> {
    User findByUsername(String username);

    Boolean existsByUsernameOrEmail(String username, String email);

    @Query("SELECT fu FROM User u JOIN u.favourites fu WHERE u.username = :username")
    List<User> findByFavouriteUser(String username);

    @Query("SELECT rf FROM User u JOIN u.favoriteRecipes rf WHERE u.username = :username")
    List<Recipe> findByFavouriteRecipes(String username);
}
