package com.grpc.grpcServer.repositories;

import com.grpc.grpcServer.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Serializable> {
    Category findByNameCategory(String name);
}
