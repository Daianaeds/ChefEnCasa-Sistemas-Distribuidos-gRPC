package com.grpc.grpcServer.port.in;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
@Slf4j
@Configuration
public class KafkaConsumer {


    @KafkaListener(topics = {"${comment.topic}"}, groupId = "group")
    public void listener(String message){
        //logica
        log.info("Received message: " + message);
    }
}
