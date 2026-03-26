package com.manoelalmorais.alugafacil.dto.imovel;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ManutencaoRequestDTO(
        Long imovelId,
        String descricao,
        BigDecimal custo,
        LocalDate data,
        String responsavel,
        String status
) {
}
