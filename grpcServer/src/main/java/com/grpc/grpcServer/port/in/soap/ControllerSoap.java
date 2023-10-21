package com.grpc.grpcServer.port.in.soap;

import com.grpc.grpcServer.GrpcServerApplication;
import com.grpc.grpcServer.port.in.soap.dtos.MessageDto;
import com.grpc.grpcServer.port.in.soap.dtos.RecipeBookDto;
import com.grpc.grpcServer.port.in.soap.dtos.RecipeBookRequestCreateDto;
import com.grpc.grpcServer.service.RecipeBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.List;

@Slf4j
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
            messageDto.setMessage("Libro creado exitosamente");
        } catch (Exception e) {
            messageDto.setMessage("Error al crear el libro   "+e.getMessage());
        }

        return messageDto;
    }

    //elimina un book
    @WebMethod(operationName = "deleteRecipeBook")
    @WebResult(name = "MessageDto")
    public MessageDto deleteRecipeBook(@WebParam(name = "idRecipeBook") int idRecipeBook) {

        MessageDto messageDto = new MessageDto();

        try {
            recipeBookService.deleteRecipeToBook(idRecipeBook);
            messageDto.setMessage("libro eliminado exitosamente");
        } catch (Exception e) {
            messageDto.setMessage("Error al eliminar el libro   "+e.getMessage());
        }

        return messageDto;
    }

    //agrega receta de un book
    @WebMethod(operationName = "addRecipe")
    @WebResult(name = "MessageDto")
    public MessageDto addRecipe(@WebParam(name = "idRecipeBook") int idRecipeBook, @WebParam(name = "idRecipe") int idRecipe) {

        MessageDto messageDto = new MessageDto();

        int flag = 1;

        try {
            recipeBookService.addOrRemoveRecipeToBook(idRecipeBook, idRecipe, flag);
            messageDto.setMessage("Receta agregada exitosamente");
        } catch (Exception e) {
            messageDto.setMessage("Error al agregar la receta"+e.getMessage());
        }

        return messageDto;
    }

    //saca receta de un book
    @WebMethod(operationName = "deleteRecipe")
    @WebResult(name = "MessageDto")
    public MessageDto deleteRecipe(@WebParam(name = "idRecipeBook") int idRecipeBook, @WebParam(name = "idRecipe") int idRecipe) {

        MessageDto messageDto = new MessageDto();

        int flag = 0;

        try {

            recipeBookService.addOrRemoveRecipeToBook(idRecipeBook, idRecipe, flag);
            messageDto.setMessage("Receta eliminada exitosamente");
        } catch (Exception e) {
            messageDto.setMessage("Error al eliminar la receta"+e.getMessage());
        }

        return messageDto;
    }

    //lista los books de un usuario
    @WebMethod(operationName = "listRecipeBooks")
    @WebResult(name = "recipeBookList")
    public List<RecipeBookDto> listRecipeBooks(@WebParam(name = "username") String username) {

        List<RecipeBookDto> recipeBookList = recipeBookService.list(username);


        return recipeBookList;
    }

    //retorna solo un book
    @WebMethod(operationName = "getRecipeBook")
    @WebResult(name = "recipeBook")
    public RecipeBookDto getRecipeBook(@WebParam(name = "idRecipeBook") int idRecipeBook){

        RecipeBookDto recipeBook = new RecipeBookDto();

        try {
            recipeBook = recipeBookService.findById(idRecipeBook);
        }catch(Exception e){
            log.error(e.getMessage());
        }

        return recipeBook;
    }
}
