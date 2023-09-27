package com.grpc.grpcServer.port.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grpc.grpcServer.port.in.dtos.CommentDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;
@Slf4j
public class CommentDeserializer implements Deserializer<CommentDto> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public CommentDto deserialize(String topic, byte[] data) {
        try {
            // Deserializar de formato JSON a un objeto
            return objectMapper.readValue(data, CommentDto.class);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("CommentDeserializer",e.getMessage());
            return null;
        }
    }
}
