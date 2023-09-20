package com.grpc.grpcServer.repositories;

import com.grpc.grpcServer.entities.PopularUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface PopularUserRepository extends JpaRepository<PopularUser, Serializable> {
}
