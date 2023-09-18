package com.grpc.grpcServer.port.out;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaProducer {

    public boolean send(String topic, String message){
//logica y llamada
        return true;
    }

}
