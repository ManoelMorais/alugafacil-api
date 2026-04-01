package com.manoelalmorais.alugafacil.dto.notificacao;

import com.manoelalmorais.alugafacil.domain.TipoNotificacao;

import java.time.LocalDateTime;

public record NotificacaoResponseDTO(
        Long id,
        TipoNotificacao tipo,
        String mensagem,
        boolean lida,
        LocalDateTime criadaEm,
        Long contratoId
) {}