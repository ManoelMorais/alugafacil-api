package com.manoelalmorais.alugafacil.dto;

public record InquilinoResponseDTO(
        Long id,
        String nome,
        String cpf,
        String email,
        String telefone
) {
}
