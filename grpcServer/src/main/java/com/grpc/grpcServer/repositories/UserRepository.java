package com.grpc.grpcServer.repositories;

import com.grpc.grpcServer.entities.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.io.Serializable;

@Repository
public interface UserRepository extends JpaRepository<User, Serializable>{
    User findByUsername(String username);
    Boolean  existsByUsernameOrEmail(String username, String email);
}
