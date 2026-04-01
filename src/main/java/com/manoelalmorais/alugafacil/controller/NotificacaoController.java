package com.manoelalmorais.alugafacil.controller;

import com.manoelalmorais.alugafacil.dto.notificacao.NotificacaoResponseDTO;
import com.manoelalmorais.alugafacil.service.NotificacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notificacoes")
@RequiredArgsConstructor
public class NotificacaoController {

    private final NotificacaoService notificacaoService;

    @GetMapping
    public ResponseEntity<List<NotificacaoResponseDTO>> listar(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(notificacaoService.listarPorUsuario(userDetails.getUsername()));
    }

    @GetMapping("/contador")
    public ResponseEntity<Map<String, Long>> contador(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(Map.of("naoLidas", notificacaoService.contarNaoLidas(userDetails.getUsername())));
    }

    @PatchMapping("/{id}/lida")
    public ResponseEntity<Void> marcarComoLida(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        notificacaoService.marcarComoLida(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/lidas")
    public ResponseEntity<Void> marcarTodasComoLidas(
            @AuthenticationPrincipal UserDetails userDetails) {
        notificacaoService.marcarTodasComoLidas(userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}