package com.grpc.grpcServer.port.configuration;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig <T>{
    @Value("${spring.kafka.bootstrapServers}")
    private String bootstrapServers;

    private KafkaTemplate<String, T> kafkaTemplate;
    @Bean
    public ProducerFactory<String, T> producerFactory(){
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        //Si una transacción no se completa dentro del tiempo límite especificado, Kafka la abortará automáticamente. 30000 (30 seg)
        properties.put(ProducerConfig.TRANSACTION_TIMEOUT_CONFIG, 5000);
        //Cuantnto tiempo esperará el productor una respuesta del servidor antes de considerar que la solicitud ha fallado.
        properties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 5000);
        return new DefaultKafkaProducerFactory<>(properties);
    }

    @Bean
    public KafkaTemplate<String, T> kafkaTemplate(){
        if(this.kafkaTemplate == null){
            this.kafkaTemplate = new KafkaTemplate<>(producerFactory());
        }
        return this.kafkaTemplate;
    }


}
