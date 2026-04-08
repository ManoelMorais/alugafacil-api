package com.manoelalmorais.alugafacil.dto.imovel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ImovelRequestDTO(
        @NotBlank(message = "Título é obrigatório") String titulo,
        @NotBlank(message = "Cidade é obrigatória") String cidade,
        @NotBlank(message = "Endereço é obrigatório") String endereco,
        @NotBlank(message = "Tipo é obrigatório") String tipo,
        @NotNull(message = "Valor é obrigatório") @Positive(message = "Valor deve ser maior que zero") BigDecimal valor,
        Integer quartos,
        Integer banheiro,
        String descricao,
        String status,
        Boolean visivelPublico,
        String estado,
        String cep,
        String bairro
) {
}
