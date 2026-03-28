package com.manoelalmorais.alugafacil.controller;

import com.manoelalmorais.alugafacil.dto.foto.FotoImovelResponseDTO;
import com.manoelalmorais.alugafacil.service.FotoImovelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/imoveis/{imovelId}/fotos")
@RequiredArgsConstructor
public class FotoImovelController {

    private final FotoImovelService fotoService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<List<FotoImovelResponseDTO>> upload(
            @PathVariable Long imovelId,
            @RequestParam("arquivos") List<MultipartFile> arquivos) {

        return ResponseEntity.ok(fotoService.uploadFotos(imovelId, arquivos));
    }

    @GetMapping
    public ResponseEntity<List<FotoImovelResponseDTO>> listar(@PathVariable Long imovelId) {
        return ResponseEntity.ok(fotoService.listarPorImovel(imovelId));
    }

    @DeleteMapping("/{fotoId}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long imovelId,
            @PathVariable Long fotoId) {

        fotoService.deletarFoto(fotoId);
        return ResponseEntity.noContent().build();
    }
}
