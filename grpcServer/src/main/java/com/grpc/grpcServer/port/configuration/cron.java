package com.grpc.grpcServer.port.configuration;

import com.grpc.grpcServer.entities.Comment;
import com.grpc.grpcServer.entities.User;
import com.grpc.grpcServer.port.out.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@RequiredArgsConstructor
@EnableScheduling
@Component
public class cron {

    private final KafkaProducer kafkaProducer;

    //@Scheduled(cron ="0 * * ? * *") 1min
    @Scheduled(cron = "*/5 * * * * *")// 5seg
    public void sendProducer(){
        Comment comment =  Comment.builder()
                .usuario(new User())
                .comment("Buena receta bro")
                .build();
        kafkaProducer.sendComment(comment);
    }
}
