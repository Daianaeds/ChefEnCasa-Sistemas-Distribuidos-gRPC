package com.grpc.grpcServer.port.in.kafka;

import com.grpc.grpcServer.port.in.deserializers.CommentDeserializer;
import com.grpc.grpcServer.port.in.kafka.dtos.CommentDto;
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
public class CommentKafkaConsumer {

    @Autowired
    CommentService commentService;

    @Value("${spring.kafka.bootstrapServers}")
    private String bootstrapServers;

    @Value("${comment.topic}")
    private String topicComments;

    //@Scheduled(cron = "*/15 * * * * *") // Ejecutar cada 20 segundos
    public void consumeAndSaveMessages() {

        //Se envia el topico y el deserializador. Falta revisar si viene vacio, para evita null
        List<CommentDto> comments = consumeMessages(topicComments, new CommentDeserializer());

        //envia la lista para persistirla
        commentService.saveList(comments);
    }

    /**
     * Se le envia el topico y el deserializador(estamos usando custom)
     */
    public List<CommentDto> consumeMessages(String topic, Deserializer<CommentDto> valueDeserializer) {
        //carga los datos para configurar el consumidor
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", bootstrapServers);
        properties.setProperty("group.id", "group");
        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer", valueDeserializer.getClass().getName());

        //se crea el consumidor con las configuraciones realizadas antes
        KafkaConsumer<String, CommentDto> consumer = new KafkaConsumer<>(properties);

        // no se pueden agregar ni eliminar temas después de la suscripción Collections.singletonList() evita que se modifique
        consumer.subscribe(Collections.singletonList(topic));

        //lista para recibir los mensajes del topico
        List<CommentDto> commentDtoList = new ArrayList<>();

        try {
            //ConsumerRecords es para mantener el orden de como se obtiene en el topico, es inmutable y solo carga
            // con la instancia KafkaConsumer.Espera un máximo de 100 milisegundos para recibir registros.
            ConsumerRecords<String, CommentDto> records = consumer.poll(Duration.ofMillis(100));

            //se recorre la lista y la carga en la lista que se va a utilizar luego
            for (ConsumerRecord<String, CommentDto> record : records) {
                commentDtoList.add(record.value());
            }

        } finally {
            //por ultimo cierra la escucha
            consumer.close();
        }

        return commentDtoList;
    }


}
