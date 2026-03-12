package com.manoelalmorais.alugafacil.dto;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        String plano,
        Boolean ativo
) {
}
