package org.binaracademy.finalproject.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.binaracademy.finalproject.dto.ResponseData;
import org.binaracademy.finalproject.entity.PesawatEntity;
import org.binaracademy.finalproject.services.PesawatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Pesawat", description = "Operation about Pesawat")
public class PesawatController {
    @Autowired
    PesawatService pesawatService;

    @Operation(summary = "Get all pesawat")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/getAllPesawat")
    public ResponseEntity<ResponseData<List<PesawatEntity>>> getAll(){
        ResponseData<List<PesawatEntity>> response = new ResponseData<>();
        try{
            List<PesawatEntity> data = pesawatService.getAll();
            response.setSuccess(true);
            response.setMessage("Successfully!");
            response.setStatusCode(HttpStatus.OK.value());
            response.setData(data);
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            response.setSuccess(false);
            response.setMessage("Failed!");
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
