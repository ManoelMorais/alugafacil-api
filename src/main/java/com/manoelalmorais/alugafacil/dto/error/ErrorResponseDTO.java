package com.manoelalmorais.alugafacil.dto.error;

import java.time.LocalDateTime;

public record ErrorResponseDTO(
        int status,
        String message,
        LocalDateTime timestamp
) {
}