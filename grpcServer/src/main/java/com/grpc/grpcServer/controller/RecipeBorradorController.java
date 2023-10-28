package com.grpc.grpcServer.controller;

import com.grpc.grpcServer.dto.IncompleteRecipeDTO;
import com.grpc.grpcServer.entities.IncompleteRecipe;
import com.grpc.grpcServer.service.DraftFileService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RecipeBorradorController {

    @Autowired
    private DraftFileService draftFileService;

    @PostMapping("/uploadFile/{username}")
    public ResponseEntity<String> saveDraft(@RequestParam("borrador") MultipartFile file , @PathVariable String username) throws Exception {
        List<IncompleteRecipeDTO> incompleteRecipes;
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<IncompleteRecipeDTO> csvToBean = new CsvToBeanBuilder<IncompleteRecipeDTO>(reader)
                    .withType(IncompleteRecipeDTO.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            incompleteRecipes = csvToBean.parse();
            draftFileService.saveFileDraft(incompleteRecipes, username);
        } catch (Exception e) {
            throw new Exception("No se logro leer el archivo csv");
        }
        return ResponseEntity.ok("Se guardo el archivo borrador");
    }

    @GetMapping("/incompleteRecipes/{username}")
    public ResponseEntity<List<IncompleteRecipeDTO>> getAllIncompleteRecipe(@PathVariable String username) throws Exception {
        return ResponseEntity.ok(draftFileService.getAllIncompleteRecipes(username));
    }
}
