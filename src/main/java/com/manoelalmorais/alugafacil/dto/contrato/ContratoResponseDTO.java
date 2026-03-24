package com.manoelalmorais.alugafacil.dto.contrato;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContratoResponseDTO(
        Long id,
        BigDecimal valor,
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataInicio,
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataFim,
        Integer diaVencimento,
        String status,
        String observacoes
) {
}
