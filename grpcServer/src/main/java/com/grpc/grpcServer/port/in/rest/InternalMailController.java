package com.grpc.grpcServer.port.in.rest;

import com.grpc.grpcServer.port.in.rest.dto.InternalMailDto;
import com.grpc.grpcServer.port.in.rest.dto.Reply;
import com.grpc.grpcServer.service.InternalMailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/InternalMail")
public class InternalMailController {

    @Autowired
    InternalMailService internalMailService;

    @Operation(summary = "Crea un mail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Se crea exitosamente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InternalMailDto.class)) }),
            @ApiResponse(responseCode = "404", description = "No se encontraron los datos", content = @Content)})
    @PostMapping("/create")
    public ResponseEntity<Object> createInternalMail(@RequestBody InternalMailDto internalMailDto){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(internalMailService.newInternalMail(internalMailDto));
        } catch (Exception e) {
            log.error("Metodo: createInternalMail - Ocurrio un error al crear el correo interno: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Envia los mail recibidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Si tiene mensajes se envia una lista",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InternalMailDto.class)) }),
            @ApiResponse(responseCode = "404", description = "No se encontraron los datos", content = @Content)})
    @GetMapping("/inbox/{destination}")
    public ResponseEntity<Object> inbox(@PathVariable("destination") String destination){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(internalMailService.findByDestination(destination));
        } catch (Exception e) {
            log.error("Metodo: createInternalMail - Ocurrio un error al acceder al correo interno: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Envia los mail enviados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Si tiene mensajes se envia una lista",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InternalMailDto.class)) }),
            @ApiResponse(responseCode = "404", description = "No se encontraron los datos", content = @Content)})
    @GetMapping("/sent/{source}")
    public ResponseEntity<Object> sent(@PathVariable("source") String source){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(internalMailService.findBySource(source));
        } catch (Exception e) {
            log.error("Metodo: createInternalMail - Ocurrio un error al acceder al correo interno: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Envia un mail por el id recibido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna el mail encontrado",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InternalMailDto.class)) }),
            @ApiResponse(responseCode = "404", description = "No se encontraron los datos", content = @Content)})
    @GetMapping("/{mail_id}")
    public ResponseEntity<Object> mailFindById(@PathVariable("mail_id") int mailId){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(internalMailService.findById(mailId));
        } catch (Exception e) {
            log.error("Metodo: createInternalMail - Ocurrio un error al acceder al correo interno: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Guarda la respuesta al mail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se guarda la respuesta exitosamente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Reply.class)) }),
            @ApiResponse(responseCode = "404", description = "No se encontraron los datos", content = @Content)})
    @PostMapping("/reply")
    public ResponseEntity<Object> mailFindById(@RequestBody Reply reply){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(internalMailService.respondMessage(reply));
        } catch (Exception e) {
            log.error("Metodo: createInternalMail - Ocurrio un error al acceder al correo interno: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
