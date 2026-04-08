package com.manoelalmorais.alugafacil.controller;

import com.manoelalmorais.alugafacil.dto.usuario.InquilinoRequestDTO;
import com.manoelalmorais.alugafacil.dto.usuario.InquilinoResponseDTO;
import com.manoelalmorais.alugafacil.service.InquilinoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/inquilino")
public class InquilinoController {

    private final InquilinoService inquilinoService;

    @GetMapping
    public ResponseEntity<List<InquilinoResponseDTO>> getAllInquilinos(){
        return ResponseEntity.status(HttpStatus.OK).body(inquilinoService.getAllInquilinos());
    }

    @PostMapping
    public ResponseEntity<InquilinoResponseDTO> criarInquilino(
            @Valid @RequestBody InquilinoRequestDTO inquilinoRequestDTO
    ) {
        return new ResponseEntity<>(inquilinoService.cadastrarInquilino(inquilinoRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InquilinoResponseDTO> getByInquilino(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(inquilinoService.getInquilinoBy(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateInquilino(@PathVariable Long id, @Valid @RequestBody InquilinoRequestDTO dto){
        return ResponseEntity.status(HttpStatus.OK).body(inquilinoService.updateInquilino(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteInquilino(@PathVariable Long id){
        inquilinoService.deleteInquilino(id);
        return ResponseEntity.status(HttpStatus.OK).body("Inquilino deletado com sucesso!");
    }
}
