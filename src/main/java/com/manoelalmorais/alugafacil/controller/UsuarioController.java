package com.manoelalmorais.alugafacil.controller;

import com.manoelalmorais.alugafacil.dto.usuario.UsuarioRequestDTO;
import com.manoelalmorais.alugafacil.dto.usuario.UsuarioResponseDTO;
import com.manoelalmorais.alugafacil.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("Token validado com sucesso");
    }

    @PostMapping()
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(
            @RequestBody UsuarioRequestDTO usuarioRequestDTO
    ) {
        return new ResponseEntity<>(usuarioService.cadastrar(usuarioRequestDTO), HttpStatus.CREATED);
    }
}
