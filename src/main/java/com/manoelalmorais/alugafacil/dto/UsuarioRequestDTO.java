package com.manoelalmorais.alugafacil.dto;

public record UsuarioRequestDTO(
        String nome,
        String email,
        String senha,
        String telefone
) {}
