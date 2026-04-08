package com.manoelalmorais.alugafacil.dto.contrato;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContratoRequestDTO(
        @NotNull(message = "Imóvel é obrigatório") Long imovelId,
        @NotNull(message = "Inquilino é obrigatório") Long inquilinoId,
        @NotNull(message = "Valor é obrigatório") @Positive(message = "Valor deve ser maior que zero") BigDecimal valor,
        @JsonFormat(pattern = "dd/MM/yyyy")
        @NotNull(message = "Data de início é obrigatória") LocalDate dataInicio,
        @JsonFormat(pattern = "dd/MM/yyyy")
        @NotNull(message = "Data de fim é obrigatória") LocalDate dataFim,
        @NotNull(message = "Dia de vencimento é obrigatório") @Min(value = 1, message = "Dia mínimo: 1") @Max(value = 28, message = "Dia máximo: 28") Integer diaVencimento,
        String status,
        String observacoes
) {
}
