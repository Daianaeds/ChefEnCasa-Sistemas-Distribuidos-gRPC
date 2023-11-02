package com.grpc.grpcServer.repositories;

import com.grpc.grpcServer.entities.Denunciation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface DenunciationRepository extends JpaRepository<Denunciation, Serializable> {

    List<Denunciation> findAllByRecipe_id(int id);
}
