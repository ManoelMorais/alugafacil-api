package com.manoelalmorais.alugafacil.dto.usuario;

public record InquilinoRequestDTO(
        String nome,
        String cpf,
        String email,
        String telefone
) {
}
