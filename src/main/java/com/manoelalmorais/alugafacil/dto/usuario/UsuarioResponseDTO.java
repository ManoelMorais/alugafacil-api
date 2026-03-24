package com.manoelalmorais.alugafacil.dto.usuario;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        String plano,
        Boolean ativo
) {
}
