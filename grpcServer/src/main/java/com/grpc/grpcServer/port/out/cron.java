package com.grpc.grpcServer.port.out;

import com.grpc.grpcServer.port.in.dtos.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@EnableScheduling
@Component
public class cron {
//para pruebas
    private final KafkaProducer kafkaProducer;

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
