package com.grpc.grpcServer.port.in.deserializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grpc.grpcServer.port.in.kafka.dtos.PopularDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;
@Slf4j
public class PopularDtoDeserializer implements Deserializer<PopularDto> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public PopularDto deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, PopularDto.class);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("PopularDtoDeserializer",e.getMessage());
            return null;
        }
    }
}
