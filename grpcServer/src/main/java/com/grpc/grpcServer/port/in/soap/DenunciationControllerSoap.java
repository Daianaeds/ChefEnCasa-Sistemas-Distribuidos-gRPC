package com.grpc.grpcServer.port.in.soap;

import com.grpc.grpcServer.GrpcServerApplication;
import com.grpc.grpcServer.port.in.soap.dtos.DenunciationDto;
import com.grpc.grpcServer.port.in.soap.dtos.MessageDto;
import com.grpc.grpcServer.port.in.soap.dtos.RecipeBookDto;
import com.grpc.grpcServer.port.in.soap.dtos.RecipeBookRequestCreateDto;
import com.grpc.grpcServer.service.DenunciationService;
import com.grpc.grpcServer.service.RecipeBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@WebService
public class DenunciationControllerSoap {

    private ConfigurableApplicationContext context = GrpcServerApplication.getContext();
    private DenunciationService denunciationService = context.getBean(DenunciationService.class);

    @WebMethod(operationName = "createDenunciation")
    @WebResult(name = "MessageDto")
    public MessageDto createDenunciation(@WebParam(name = "username") String username, @WebParam(name = "motive") int motive, @WebParam(name = "idRecipe") int idRecipe) {
        MessageDto messageDto = new MessageDto();
        try {
            denunciationService.save(username, motive, idRecipe);
            messageDto.setMessage("Denuncia realizada");
        } catch (Exception e) {
            messageDto.setMessage("Error al guardar la denuncia   " + e.getMessage());
        }

        return messageDto;
    }

    @WebMethod(operationName = "denunciationList")
    @WebResult(name = "denunciationDtoList")
    public List<DenunciationDto> denunciationList() {
        List<DenunciationDto> denunciationDtoList = new ArrayList<>();
    try {
        denunciationDtoList = denunciationService.findList();
    }catch (Exception e){
        log.error(" Error    " + e.getMessage());
    }

        return denunciationDtoList;
    }

    @WebMethod(operationName = "ignoreDenunciation")
    @WebResult(name = "MessageDto")
    public MessageDto ignoreDenunciation(@WebParam(name = "idDenunciation") int idDenunciation) {
        MessageDto messageDto = new MessageDto();
        try {
            denunciationService.ignore(idDenunciation);
            messageDto.setMessage("Denuncia ignorada");
        }catch (Exception e){
            messageDto.setMessage("Error al ignorar la denuncia   " + e.getMessage());
        }

        return messageDto;
    }

}
