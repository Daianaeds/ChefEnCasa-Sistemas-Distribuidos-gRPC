package com.grpc.grpcServer.port.out;

import com.grpc.grpcServer.entities.Comment;
import com.grpc.grpcServer.port.in.dtos.CommentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import com.grpc.grpcServer.port.configuration.KafkaProducerConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaProducer{

   private final KafkaProducerConfig kafkaProducerConfig;
    @Value("${comment.topic}")
    private String topicComentario;

    //ejemplo de productor
    public void sendComment(CommentDto comment){

        try{
            kafkaProducerConfig.kafkaTemplate().send(topicComentario, comment);
        }catch(Exception e){
            log.error("Comment TOPIC : {}", e.getMessage());
        }

    }



}
