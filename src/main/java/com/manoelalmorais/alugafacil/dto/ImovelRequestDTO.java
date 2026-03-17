package com.manoelalmorais.alugafacil.dto;

import java.math.BigDecimal;

public record ImovelRequestDTO(
        String titulo,
        String estado,
        String cep,
        String cidade,
        String endereco,
        String bairro,
        String tipo,
        BigDecimal valor,
        Integer quartos,
        Integer banheiro,
        String descricao,
        String status,
        Boolean visivelPublico
) {
}
