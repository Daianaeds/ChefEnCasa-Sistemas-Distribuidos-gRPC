package com.grpc.grpcServer.port.in.soap;

import com.grpc.grpcServer.GrpcServerApplication;
import com.grpc.grpcServer.port.in.soap.dtos.MessageDto;
import com.grpc.grpcServer.port.in.soap.dtos.RecipeBookRequestCreateDto;
import com.grpc.grpcServer.service.RecipeBookService;
import org.springframework.context.ConfigurableApplicationContext;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public class ControllerSoap{
    private ConfigurableApplicationContext context = GrpcServerApplication.getContext();

    private RecipeBookService recipeBookService = context.getBean(RecipeBookService.class);


    //crea un book
    @WebMethod(operationName = "createRecipeBook")
    @WebResult(name = "MessageDto")
    public MessageDto createRecipeBook(@WebParam(name = "request") RecipeBookRequestCreateDto request) {
        MessageDto messageDto = new MessageDto();
        try {
            recipeBookService.saveRecipeBook(request);
            messageDto.setMessage("Book creado exitosamente");
        } catch (Exception e) {
            messageDto.setMessage("Error al crear el book   "+e.getMessage());
        }

        return messageDto;
    }

    //elimina un book
    @WebMethod(operationName = "deleteRecipeBook")
    @WebResult(name = "MessageDto")
    public MessageDto deleteRecipeBook(@WebParam(name = "idRecipeBook") int idRecipeBook) {

        MessageDto messageDto = new MessageDto("eliminado");

        return messageDto;
    }

    //agrega receta de un book
    @WebMethod(operationName = "addRecipe")
    @WebResult(name = "MessageDto")
    public MessageDto addRecipe(@WebParam(name = "idRecipeBook") int idRecipeBook, @WebParam(name = "idRecipe") int idRecipe) {

        MessageDto messageDto = new MessageDto("receta agregada");

        return messageDto;
    }

    //saca receta de un book
    @WebMethod(operationName = "deleteRecipe")
    @WebResult(name = "MessageDto")
    public MessageDto deleteRecipe(@WebParam(name = "idRecipeBook") int idRecipeBook, @WebParam(name = "idRecipe") int idRecipe) {

        MessageDto messageDto = new MessageDto("receta eliminada");

        return messageDto;
    }

    //lista los books de un usuario
    @WebMethod(operationName = "listRecipeBooks")
    @WebResult(name = "MessageDto")
    public MessageDto listRecipeBooks(@WebParam(name = "username") String username) {

        MessageDto messageDto = new MessageDto("lista");

        return messageDto;
    }

    //retorna solo un book
    @WebMethod(operationName = "getRecipeBook")
    @WebResult(name = "MessageDto")
    public MessageDto getRecipeBook(@WebParam(name = "idRecipeBook") int idRecipeBook) {

        MessageDto messageDto = new MessageDto("book");

        return messageDto;
    }
}
