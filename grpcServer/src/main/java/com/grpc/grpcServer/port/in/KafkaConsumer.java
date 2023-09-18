package com.grpc.grpcServer.port.in;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class KafkaConsumer {
    private Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = {"novedades"}, groupId = "group")
    public void listener(String message){
        //logica
        LOGGER.info("Received message: " + message);
    }
}
