package com.grpc.grpcServer.port.out;

import com.grpc.grpcServer.entities.Comment;
import com.grpc.grpcServer.entities.User;
import com.grpc.grpcServer.port.in.CustomKafkaConsumer;
import com.grpc.grpcServer.port.in.dtos.CommentDto;
import com.grpc.grpcServer.port.out.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

@RequiredArgsConstructor
@EnableScheduling
@Component
public class cron {

    private final KafkaProducer kafkaProducer;


    //@Scheduled(cron ="0 * * ? * *") 1min
    @Scheduled(cron = "*/5 * * * * *")// 5seg
    public void sendProducer(){
        CommentDto comment =  CommentDto.builder()
                                .username("admin")
                                .comment("Hola")
                                .idRecipe(1)
                                .build();
        kafkaProducer.sendComment(comment);
    }

}
