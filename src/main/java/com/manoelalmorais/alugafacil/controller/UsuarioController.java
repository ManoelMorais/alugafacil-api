package com.manoelalmorais.alugafacil.controller;

import com.manoelalmorais.alugafacil.dto.UsuarioRequestDTO;
import com.manoelalmorais.alugafacil.dto.UsuarioResponseDTO;
import com.manoelalmorais.alugafacil.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping()
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(
            @RequestBody UsuarioRequestDTO usuarioRequestDTO
    ) {
        return new ResponseEntity<>(usuarioService.cadastrar(usuarioRequestDTO), HttpStatus.CREATED);
    }
}
