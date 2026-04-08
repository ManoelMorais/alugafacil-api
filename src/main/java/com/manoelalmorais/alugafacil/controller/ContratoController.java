package com.manoelalmorais.alugafacil.controller;

import com.manoelalmorais.alugafacil.dto.contrato.ContratoRequestDTO;
import com.manoelalmorais.alugafacil.dto.contrato.ContratoResponseDTO;
import com.manoelalmorais.alugafacil.service.ContratoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/contrato")
public class ContratoController {

    private final ContratoService contratoService;

    @GetMapping
    public ResponseEntity<List<ContratoResponseDTO>> getAllContratos() {
        return ResponseEntity.status(HttpStatus.OK).body(contratoService.getAllContrato());
    }

    @PostMapping
    public ResponseEntity<ContratoResponseDTO> criarContrato(
            @Valid @RequestBody ContratoRequestDTO contratoRequestDTO
        ) {
        return new ResponseEntity<>(contratoService.cadastrarContrato(contratoRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContratoResponseDTO> getByIdContrato(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(contratoService.getContratoById(id));
    }

    @PutMapping("/{id}")
     public ResponseEntity<Object> updateContrato(@PathVariable Long id, @Valid @RequestBody ContratoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(contratoService.updateContrato(id, dto));
     }

     @DeleteMapping("/{id}")
     public ResponseEntity<Object> deleteContrato(@PathVariable Long id) {
        contratoService.deleteContrato(id);
        return ResponseEntity.status(HttpStatus.OK).body("Contrato deletado com sucesso!");
     }
}
