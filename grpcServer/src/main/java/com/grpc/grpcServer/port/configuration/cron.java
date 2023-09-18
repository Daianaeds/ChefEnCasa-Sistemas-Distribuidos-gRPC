package com.grpc.grpcServer.port.configuration;

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

    @Scheduled(cron ="0 * * ? * *")
    public void sendProducer(){
        kafkaProducer.send("novedades", "HOLIIIIISSSS");
    }
}
