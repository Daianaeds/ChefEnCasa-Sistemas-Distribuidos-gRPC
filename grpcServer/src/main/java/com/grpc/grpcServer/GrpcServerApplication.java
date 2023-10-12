package com.grpc.grpcServer;

import com.grpc.grpcServer.port.in.soap.ControllerSoap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.ws.Endpoint;

@Slf4j
@SpringBootApplication
public class GrpcServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrpcServerApplication.class, args);
        Endpoint.publish("http://localhost:8087/",new ControllerSoap());
    }
}
