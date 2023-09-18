package com.grpc.grpcServer.port.out;
import org.springframework.kafka.core.KafkaTemplate;
import com.grpc.grpcServer.port.configuration.KafkaProducerConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class KafkaProducer{

   private final KafkaProducerConfig kafkaProducerConfig;

    KafkaTemplate<String, String> kafkaTemplate;

    public boolean send(String topic, String message){
        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(kafkaProducerConfig.producerFactory());
        kafkaTemplate.send(topic, message);
        return true;
    }



}
