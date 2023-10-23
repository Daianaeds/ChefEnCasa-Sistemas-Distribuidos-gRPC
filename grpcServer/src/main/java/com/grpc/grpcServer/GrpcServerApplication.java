package com.grpc.grpcServer;

import com.grpc.grpcServer.port.in.soap.ControllerSoap;
import com.grpc.grpcServer.port.in.soap.DenunciationControllerSoap;
import com.grpc.grpcServer.service.DenunciationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import javax.xml.ws.Endpoint;


@SpringBootApplication
public class GrpcServerApplication {

    public static void main(String[] args) {
        context = SpringApplication.run(GrpcServerApplication.class, args);

        Endpoint.publish("http://localhost:8087/",new ControllerSoap());

        Endpoint.publish("http://localhost:8088/",new DenunciationControllerSoap());
    }

    private static ConfigurableApplicationContext context;

    public static ConfigurableApplicationContext getContext() {
        return context;
    }
}
