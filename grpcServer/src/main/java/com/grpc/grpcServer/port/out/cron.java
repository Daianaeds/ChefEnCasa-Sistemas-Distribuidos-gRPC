package com.grpc.grpcServer.port.out;

import com.grpc.grpcServer.port.in.dtos.CommentDto;
import com.grpc.grpcServer.port.in.dtos.PopularDto;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

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

    @Scheduled(cron = "*/5 * * * * *")// 5seg
    public void sendProducerPopularUser(){
        PopularDto popularDto =  PopularDto.builder()
                .identifier("admin")
                .score(1)
                .build();
        kafkaProducer.sendPopularUser(popularDto);
    }

    @Scheduled(cron = "*/5 * * * * *")// 5seg
    public void sendProducerPopularRecipe(){
        PopularDto popularDto =  PopularDto.builder()
                .identifier("1")
                .score(3)
                .build();
        kafkaProducer.sendPopularRecipe(popularDto);
    }
}
