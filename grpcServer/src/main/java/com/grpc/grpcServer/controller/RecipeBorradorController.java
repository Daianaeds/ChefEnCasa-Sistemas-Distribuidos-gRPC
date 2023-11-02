package com.grpc.grpcServer.controller;

import com.grpc.grpcServer.dto.CompleteRecipeDTO;
import com.grpc.grpcServer.dto.IncompleteRecipeDTO;
import com.grpc.grpcServer.service.DraftFileService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RecipeBorradorController {

    @Autowired
    private DraftFileService draftFileService;

    @PostMapping("/uploadFile/{username}")
    public ResponseEntity<String> saveDraft(@RequestParam("borrador") MultipartFile file, @PathVariable String username) throws Exception {
        List<IncompleteRecipeDTO> incompleteRecipes;
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<IncompleteRecipeDTO> csvToBean = new CsvToBeanBuilder<IncompleteRecipeDTO>(reader)
                    .withType(IncompleteRecipeDTO.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            incompleteRecipes = csvToBean.parse();
            draftFileService.saveFileDraft(incompleteRecipes, username);
        } catch (IOException e) {
            throw new IOException("No se logro leer el archivo csv");
        }
        return ResponseEntity.ok("Se guardo el archivo borrador");
    }

    @GetMapping("/incompleteRecipes/{username}")
    public ResponseEntity<?> getAllIncompleteRecipe(@PathVariable String username) throws Exception {
        try {
            return ResponseEntity.ok(draftFileService.getAllIncompleteRecipes(username));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/incompleteRecipe/{idIncompleteRecipe}")
    public ResponseEntity<?> deleteIncompleteRecipe(@PathVariable int idIncompleteRecipe) {
        try {
            draftFileService.deleteIncompleteRecipes(idIncompleteRecipe);
            return ResponseEntity.ok("Se eliminó la receta");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/incompleteRecipe/{idIncompleteRecipe}")
    public ResponseEntity<?> getIncompleteRecipe(@PathVariable int idIncompleteRecipe) {
        try {
            IncompleteRecipeDTO incompleteRecipeDTO = draftFileService.getIncompleteRecipe(idIncompleteRecipe);
            return ResponseEntity.ok(incompleteRecipeDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/saveRecipe")
    public ResponseEntity<?> saveRecipe(@RequestBody CompleteRecipeDTO completeRecipe) {
        try {
            return ResponseEntity.ok(draftFileService.saveRecipe(completeRecipe));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
