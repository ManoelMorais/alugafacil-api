package com.manoelalmorais.alugafacil.dto.usuario;

public record InquilinoResponseDTO(
        Long id,
        String nome,
        String cpf,
        String email,
        String telefone
) {
}
