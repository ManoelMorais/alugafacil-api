package com.manoelalmorais.alugafacil.repository;

import com.manoelalmorais.alugafacil.domain.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import com.manoelalmorais.alugafacil.domain.TipoNotificacao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {

    List<Notificacao> findByUsuarioIdOrderByLidaAscCriadaEmDesc(Long usuarioId);

    List<Notificacao> findByUsuarioIdAndLidaFalse(Long usuarioId);

    long countByUsuarioIdAndLidaFalse(Long usuarioId);

    boolean existsByContratoIdAndTipo(Long contratoId, TipoNotificacao tipo);

    @Modifying
    @Query("UPDATE Notificacao n SET n.lida = true, n.lidaEm = CURRENT_TIMESTAMP WHERE n.usuario.id = :usuarioId AND n.lida = false")
    void marcarTodasComoLidas(Long usuarioId);
}
