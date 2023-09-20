package com.grpc.grpcServer.port.in;

import com.grpc.grpcServer.port.in.dtos.PopularDto;
import com.grpc.grpcServer.service.PopularService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@EnableScheduling
@EnableKafka
@Configuration
@Slf4j
public class PopularKafkaConsumer {
    @Autowired
    PopularService popularService;

    @Value("${spring.kafka.bootstrapServers}")
    private static String bootstrapServers;

    @Scheduled(cron = "*/20 * * * * *")
    public void consumeAndSaveMessagesUser() {

        List<PopularDto> popularDtoList = consumeMessages("popularidadUsuario", new PopularDtoDeserializer());

        popularService.updateUser(popularDtoList);
    }

    @Scheduled(cron = "*/20 * * * * *")
    public void consumeAndSaveMessagesRecipe() {

        List<PopularDto> popularDtoList = consumeMessages("popularidadReceta", new PopularDtoDeserializer());

        popularService.updateRecipe(popularDtoList);
    }
    public List<PopularDto> consumeMessages(String topic, Deserializer<PopularDto> valueDeserializer) {

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("group.id", "group");
        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer", valueDeserializer.getClass().getName());


        KafkaConsumer<String, PopularDto> consumer = new KafkaConsumer<>(properties);

        consumer.subscribe(Collections.singletonList(topic));


        List<PopularDto> popularDtoList = new ArrayList<>();

        try {

            ConsumerRecords<String, PopularDto> records = consumer.poll(Duration.ofMillis(100));

            for (ConsumerRecord<String, PopularDto> record : records) {
                popularDtoList.add(record.value());
            }

        } finally {

            consumer.close();
        }

        return popularDtoList;
    }


}
