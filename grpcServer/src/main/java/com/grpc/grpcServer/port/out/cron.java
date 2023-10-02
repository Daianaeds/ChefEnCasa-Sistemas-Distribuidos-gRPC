package com.grpc.grpcServer.port.out;

import com.grpc.grpcServer.port.in.dtos.CommentDto;
import com.grpc.grpcServer.port.in.dtos.PopularDto;
import com.grpc.grpcServer.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@EnableScheduling
@Component
public class cron {

    //para pruebas
    private final KafkaProducer kafkaProducer;

    @Autowired
    CommentService commentService;

   // @Scheduled(cron = "*/5 * * * * *")// 5seg
    public void sendProducer(){
        CommentDto comment =  CommentDto.builder()
                                .username("admin")
                                .comment("Hola")
                                .idRecipe(1)
                                .build();
        kafkaProducer.sendComment(comment);
    }

   // @Scheduled(cron = "*/5 * * * * *")// 5seg
    public void sendProducerPopularUser(){
        PopularDto popularDto =  PopularDto.builder()
                .identifier("admin")
                .score(1)
                .build();
        kafkaProducer.sendPopularUser(popularDto);
    }

  //  @Scheduled(cron = "*/5 * * * * *")// 5seg
    public void sendProducerPopularRecipe(){
        PopularDto popularDto =  PopularDto.builder()
                .identifier("1")
                .score(3)
                .build();
        kafkaProducer.sendPopularRecipe(popularDto);
    }
}
