package com.manoelalmorais.alugafacil.dto;

public record InquilinoRequestDTO(
        String nome,
        String cpf,
        String email,
        String telefone
) {
}
