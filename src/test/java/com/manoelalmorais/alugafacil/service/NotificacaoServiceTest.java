package com.manoelalmorais.alugafacil.service;

import com.manoelalmorais.alugafacil.domain.*;
import com.manoelalmorais.alugafacil.dto.notificacao.NotificacaoResponseDTO;
import com.manoelalmorais.alugafacil.repository.NotificacaoRepository;
import com.manoelalmorais.alugafacil.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificacaoServiceTest {

    @Mock
    private NotificacaoRepository notificacaoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private NotificacaoService notificacaoService;

    // Objetos reutilizados nos testes
    private Usuario usuario;
    private Contrato contrato;
    private Notificacao notificacao;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Manoel");
        usuario.setEmail("manoel@teste.com");

        contrato = new Contrato();
        contrato.setId(10L);

        notificacao = Notificacao.builder()
                .id(100L)
                .tipo(TipoNotificacao.VENCIMENTO_30_DIAS)
                .mensagem("Contrato vence em 30 dias")
                .lida(false)
                .criadaEm(LocalDateTime.now())
                .usuario(usuario)
                .contrato(contrato)
                .build();
    }

    //criarAlertaContrato
    @Test
    void deveSalvarNotificacaoQuandoNaoExisteDuplicata() {
        when(notificacaoRepository.existsByContratoIdAndTipo(10L, TipoNotificacao.VENCIMENTO_30_DIAS))
                .thenReturn(false);

        notificacaoService.criarAlertaContrato(usuario, contrato, TipoNotificacao.VENCIMENTO_30_DIAS, "msg");

        verify(notificacaoRepository, times(1)).save(any(Notificacao.class));
    }

    @Test
    void naoDeveSalvarNotificacaoQuandoJaExisteDuplicata() {
        when(notificacaoRepository.existsByContratoIdAndTipo(10L, TipoNotificacao.VENCIMENTO_30_DIAS))
                .thenReturn(true);

        notificacaoService.criarAlertaContrato(usuario, contrato, TipoNotificacao.VENCIMENTO_30_DIAS, "msg");

        verify(notificacaoRepository, never()).save(any());
    }

    //listarPorUsuario
    @Test
    void deveRetornarListaDeNotificacoesDoUsuario() {
        when(usuarioRepository.findByEmail("manoel@teste.com"))
                .thenReturn(Optional.of(usuario));
        when(notificacaoRepository.findByUsuarioIdOrderByLidaAscCriadaEmDesc(1L))
                .thenReturn(List.of(notificacao));

        List<NotificacaoResponseDTO> resultado = notificacaoService.listarPorUsuario("manoel@teste.com");

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).tipo()).isEqualTo(TipoNotificacao.VENCIMENTO_30_DIAS);
        assertThat(resultado.get(0).lida()).isFalse();
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontradoAoListar() {
        when(usuarioRepository.findByEmail("naoexiste@teste.com"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> notificacaoService.listarPorUsuario("naoexiste@teste.com"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Usuário não encontrado");
    }

    //contarNaoLidas
    @Test
    void deveRetornarQuantidadeDeNotificacoesNaoLidas() {
        when(usuarioRepository.findByEmail("manoel@teste.com"))
                .thenReturn(Optional.of(usuario));
        when(notificacaoRepository.countByUsuarioIdAndLidaFalse(1L))
                .thenReturn(3L);

        long resultado = notificacaoService.contarNaoLidas("manoel@teste.com");

        assertThat(resultado).isEqualTo(3L);
    }

    //marcarComoLida

    @Test
    void deveMarcarNotificacaoComoLida() {
        when(usuarioRepository.findByEmail("manoel@teste.com"))
                .thenReturn(Optional.of(usuario));
        when(notificacaoRepository.findById(100L))
                .thenReturn(Optional.of(notificacao));

        notificacaoService.marcarComoLida(100L, "manoel@teste.com");

        assertThat(notificacao.isLida()).isTrue();
        assertThat(notificacao.getLidaEm()).isNotNull();
        verify(notificacaoRepository, times(1)).save(notificacao);
    }

    @Test
    void deveLancarExcecaoQuandoNotificacaoNaoEncontrada() {
        when(usuarioRepository.findByEmail("manoel@teste.com"))
                .thenReturn(Optional.of(usuario));
        when(notificacaoRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> notificacaoService.marcarComoLida(999L, "manoel@teste.com"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Notificação não encontrada");
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioTentaMarcarNotificacaoDeOutroUsuario() {
        Usuario outroUsuario = new Usuario();
        outroUsuario.setId(99L);
        outroUsuario.setEmail("outro@teste.com");

        when(usuarioRepository.findByEmail("outro@teste.com"))
                .thenReturn(Optional.of(outroUsuario));
        when(notificacaoRepository.findById(100L))
                .thenReturn(Optional.of(notificacao)); // notificação pertence ao usuario de id 1

        assertThatThrownBy(() -> notificacaoService.marcarComoLida(100L, "outro@teste.com"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Acesso negado");
    }

    //marcarTodasComoLidas
    @Test
    void deveMarcarTodasNotificacoesComoLidas() {
        when(usuarioRepository.findByEmail("manoel@teste.com"))
                .thenReturn(Optional.of(usuario));

        notificacaoService.marcarTodasComoLidas("manoel@teste.com");

        verify(notificacaoRepository, times(1)).marcarTodasComoLidas(1L);
    }
}