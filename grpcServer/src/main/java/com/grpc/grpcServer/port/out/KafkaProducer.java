package com.grpc.grpcServer.port.out;

import com.grpc.grpcServer.entities.Comment;
import com.grpc.grpcServer.port.in.dtos.CommentDto;
import com.grpc.grpcServer.port.in.dtos.PopularDto;
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
    private String topicComentarios;

    @Value("${popularidad.usuario.topic}")
    private String topicPopularidadUsuario;

    @Value("${popularidad.receta.topic}")
    private String topicPopularidadReceta;

    //ejemplo de productor
    public void sendComment(CommentDto comment){

        try{
            kafkaProducerConfig.kafkaTemplate().send(topicComentarios, comment);
        }catch(Exception e){
            log.error("Comment TOPIC : {}", e.getMessage());
        }

    }

    public void sendPopularUser(PopularDto popularDto){

        try{
            kafkaProducerConfig.kafkaTemplate().send(topicPopularidadUsuario, popularDto);
        }catch(Exception e){
            log.error("Popularidad usuario : {}", e.getMessage());
        }

    }

    public void sendPopularRecipe(PopularDto popularDto){

        try{
            kafkaProducerConfig.kafkaTemplate().send(topicPopularidadReceta, popularDto);
        }catch(Exception e){
            log.error("Popularidad receta TOPIC : {}", e.getMessage());
        }

    }


}
