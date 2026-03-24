package com.manoelalmorais.alugafacil.controller;

import com.manoelalmorais.alugafacil.dto.LoginRequestDTO;
import com.manoelalmorais.alugafacil.dto.LoginResponseDTO;
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
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO loginRequestDTO
    ){
        return new ResponseEntity<>(usuarioService.login(loginRequestDTO), HttpStatus.OK);
    }
}
