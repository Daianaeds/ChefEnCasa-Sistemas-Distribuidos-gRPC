package com.grpc.grpcServer.repositories;

import com.grpc.grpcServer.entities.InternalMail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository
public interface InternalMailRepository extends JpaRepository<InternalMail, Serializable> {

    @Query("SELECT im FROM InternalMail im JOIN im.destination d WHERE d.username = :destination")
    List<InternalMail> findByDestination(@Param("destination")String destination);
    @Query("SELECT im FROM InternalMail im JOIN im.source d WHERE d.username = :source")
    List<InternalMail> findBySource(@Param("source")String source);
    @Query("SELECT im FROM InternalMail im " +
            "INNER JOIN im.destination d " +
            "INNER JOIN im.source s " +
            "WHERE s.username = :source and d.username = :destination " +
            "and im.subject = :subject")
    Optional<InternalMail> findByMail(@Param("destination") String destination, @Param("source") String source, @Param("subject") String subject);
}
