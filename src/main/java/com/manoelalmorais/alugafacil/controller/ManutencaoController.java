package com.manoelalmorais.alugafacil.controller;

import com.manoelalmorais.alugafacil.dto.imovel.ManutencaoRequestDTO;
import com.manoelalmorais.alugafacil.dto.imovel.ManutencaoResponseDTO;
import com.manoelalmorais.alugafacil.service.ManutencaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/manutencao")
public class ManutencaoController {

    private final ManutencaoService manutencaoService;

    @GetMapping
    public ResponseEntity<List<ManutencaoResponseDTO>> getAllContratos() {
        return ResponseEntity.status(HttpStatus.OK).body(manutencaoService.getAllManutencoes());
    }

    @PostMapping
    public ResponseEntity<ManutencaoResponseDTO> criarManutencao(
            @Valid @RequestBody ManutencaoRequestDTO manutencaoRequestDTO
    ) {
        return new ResponseEntity<>(manutencaoService.cadastrarManutencao(manutencaoRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManutencaoResponseDTO> getByIdContrato(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(manutencaoService.getManutencaoId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateManutenca(@PathVariable Long id, @Valid @RequestBody ManutencaoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(manutencaoService.updadeManutencao(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteManutencao(@PathVariable Long id) {
        manutencaoService.deleteManutencao(id);
        return ResponseEntity.status(HttpStatus.OK).body("Manutenão deletado com sucesso!");
    }
}
