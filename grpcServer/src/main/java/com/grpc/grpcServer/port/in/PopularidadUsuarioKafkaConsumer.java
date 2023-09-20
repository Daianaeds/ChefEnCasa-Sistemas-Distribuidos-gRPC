package com.grpc.grpcServer.port.in;

import com.grpc.grpcServer.port.in.dtos.CommentDto;
import com.grpc.grpcServer.service.CommentService;
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
public class PopularidadUsuarioKafkaConsumer {
    @Autowired
    CommentService commentService;

    @Value("${spring.kafka.bootstrapServers}")
    private static String bootstrapServers;

    @Scheduled(cron = "*/20 * * * * *")
    public void consumeAndSaveMessages() {

        List<CommentDto> comments = consumeMessages("opularidadUsuario", new CommentDeserializer());

        commentService.saveList(comments);
    }

    public List<CommentDto> consumeMessages(String topic, Deserializer<CommentDto> valueDeserializer) {

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("group.id", "group");
        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer", valueDeserializer.getClass().getName());


        KafkaConsumer<String, CommentDto> consumer = new KafkaConsumer<>(properties);

        consumer.subscribe(Collections.singletonList(topic));


        List<CommentDto> commentDtoList = new ArrayList<>();

        try {

            ConsumerRecords<String, CommentDto> records = consumer.poll(Duration.ofMillis(100));

            for (ConsumerRecord<String, CommentDto> record : records) {
                commentDtoList.add(record.value());
            }

        } finally {

            consumer.close();
        }

        return commentDtoList;
    }


}
