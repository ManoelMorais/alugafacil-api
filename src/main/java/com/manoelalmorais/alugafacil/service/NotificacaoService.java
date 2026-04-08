package com.manoelalmorais.alugafacil.service;

import com.manoelalmorais.alugafacil.domain.*;
import com.manoelalmorais.alugafacil.dto.notificacao.NotificacaoResponseDTO;
import com.manoelalmorais.alugafacil.repository.NotificacaoRepository;
import com.manoelalmorais.alugafacil.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;
    private final UsuarioRepository     usuarioRepository;

    @Transactional
    public void criarAlertaContrato(Usuario usuario, Contrato contrato, TipoNotificacao tipo, String mensagem) {
        if (notificacaoRepository.existsByContratoIdAndTipo(contrato.getId(), tipo)) return;

        Notificacao notificacao = Notificacao.builder()
                .tipo(tipo)
                .mensagem(mensagem)
                .lida(false)
                .usuario(usuario)
                .contrato(contrato)
                .build();

        notificacaoRepository.save(notificacao);
    }

    public List<NotificacaoResponseDTO> listarPorUsuario(String email) {
        Usuario usuario = buscarPorEmail(email);
        return notificacaoRepository
                .findByUsuarioIdOrderByLidaAscCriadaEmDesc(usuario.getId())
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public long contarNaoLidas(String email) {
        Usuario usuario = buscarPorEmail(email);
        return notificacaoRepository.countByUsuarioIdAndLidaFalse(usuario.getId());
    }

    @Transactional
    public void marcarComoLida(Long notificacaoId, String email) {
        Usuario usuario = buscarPorEmail(email);

        Notificacao n = notificacaoRepository.findById(notificacaoId)
                .orElseThrow(() -> new EntityNotFoundException("Notificação não encontrada: " + notificacaoId));

        if (!n.getUsuario().getId().equals(usuario.getId())) {
            throw new IllegalStateException("Acesso negado");
        }

        n.setLida(true);
        n.setLidaEm(LocalDateTime.now());
        notificacaoRepository.save(n);
    }

    @Transactional
    public void marcarTodasComoLidas(String email) {
        Usuario usuario = buscarPorEmail(email);
        notificacaoRepository.marcarTodasComoLidas(usuario.getId());
    }

    // Utilitário interno
    private Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado: " + email));
    }

    private NotificacaoResponseDTO toDTO(Notificacao n) {
        return new NotificacaoResponseDTO(
                n.getId(),
                n.getTipo(),
                n.getMensagem(),
                n.isLida(),
                n.getCriadaEm(),
                n.getContrato() != null ? n.getContrato().getId() : null
        );
    }
}