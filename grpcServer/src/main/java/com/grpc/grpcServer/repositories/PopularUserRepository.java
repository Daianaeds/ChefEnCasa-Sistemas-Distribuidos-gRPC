package com.grpc.grpcServer.repositories;

import com.grpc.grpcServer.entities.PopularUser;
import com.grpc.grpcServer.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface PopularUserRepository extends JpaRepository<PopularUser, Serializable> {

    PopularUser findByUser(User user);

    @Query("SELECT ps FROM PopularUser ps JOIN ps.user u WHERE u.id = :id")
    PopularUser findByIdUser(int id);
}
