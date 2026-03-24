package com.manoelalmorais.alugafacil.controller;

import com.manoelalmorais.alugafacil.dto.imovel.ImovelRequestDTO;
import com.manoelalmorais.alugafacil.dto.imovel.ImovelResponseDTO;
import com.manoelalmorais.alugafacil.service.ImovelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/imovel")
public class ImovelController {

    private final ImovelService imovelService;

    @GetMapping
    public ResponseEntity<List<ImovelResponseDTO>> getAlImoveis() {
        return ResponseEntity.status(HttpStatus.OK).body(imovelService.getAllImoveis());
    }

    @PostMapping()
    public ResponseEntity<ImovelResponseDTO> criarImovel(
            @RequestBody ImovelRequestDTO imovelRequestDTO
    ) {
        return new ResponseEntity<>(imovelService.cadastrarImovel(imovelRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImovelResponseDTO> getByImoveis(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(imovelService.getImovelByID(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateImovel(@PathVariable Long id, @RequestBody @Valid ImovelRequestDTO dto){
        return ResponseEntity.status(HttpStatus.OK).body(imovelService.updateImovel(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteImovel(@PathVariable Long id) {
        imovelService.deleteImovel(id);
        return ResponseEntity.status(HttpStatus.OK).body("Imovel deletado com sucesso!");
    }
}
