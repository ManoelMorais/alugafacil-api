package com.manoelalmorais.alugafacil.dto.imovel;

import java.math.BigDecimal;

public record ImovelResponseDTO(
        Long id,
        String titulo,
        String estado,
        String cep,
        String endereco,
        String bairro,
        String cidade,
        String tipo,
        BigDecimal valor,
        Integer quartos,
        Integer banheiro,
        String descricao,
        String status,
        Boolean visivelPublico
) {
}
