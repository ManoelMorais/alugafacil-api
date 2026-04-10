package com.manoelalmorais.alugafacil.job;

import com.manoelalmorais.alugafacil.domain.Contrato;
import com.manoelalmorais.alugafacil.domain.TipoNotificacao;
import com.manoelalmorais.alugafacil.repository.ContratoRepository;
import com.manoelalmorais.alugafacil.service.EmailService;
import com.manoelalmorais.alugafacil.service.NotificacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlertaVencimentoJob {

    private final ContratoRepository contratoRepository;
    private final NotificacaoService notificacaoService;
    private final EmailService emailService;

    @Scheduled(cron = "0 0 8 * * *")
    public void executar() {
        log.info("Job de alertas iniciado: {}", LocalDate.now());
        processarAlertas(30, TipoNotificacao.VENCIMENTO_30_DIAS);
        processarAlertas(7,  TipoNotificacao.VENCIMENTO_7_DIAS);
        log.info("Job de alertas concluído.");
    }

    private void processarAlertas(int diasRestantes, TipoNotificacao tipo) {
        LocalDate dataAlvo = LocalDate.now().plusDays(diasRestantes);
        List<Contrato> contratos = contratoRepository.findByDataFim(dataAlvo);

        log.info("Contratos vencendo em {} dias: {}", diasRestantes, contratos.size());

        for (Contrato contrato : contratos) {
            try {
                String mensagem = buildMensagem(contrato, diasRestantes);
                String assunto  = buildAssunto(diasRestantes);

                notificacaoService.criarAlertaContrato(
                        contrato.getImovel().getUsuario(),
                        contrato,
                        tipo,
                        mensagem
                );

                emailService.enviar(
                        contrato.getImovel().getUsuario().getEmail(),
                        assunto,
                        mensagem
                );

            } catch (Exception e) {
                log.error("Erro ao processar alerta para contrato {}: {}", contrato.getId(), e.getMessage());
            }
        }
    }

    private String buildMensagem(Contrato contrato, int dias) {
        return String.format(
                "O contrato do imóvel \"%s\" com o inquilino %s vence em %d dias (%s). " +
                        "Valor mensal: R$ %.2f.",
                contrato.getImovel().getTitulo(),
                contrato.getInquilino().getNome(),
                dias,
                contrato.getDataFim(),
                contrato.getValor()
        );
    }

    private String buildAssunto(int dias) {
        return dias == 7
                ? "⚠️ AlugaFácil — Contrato vence em 7 dias"
                : "📅 AlugaFácil — Contrato vence em 30 dias";
    }
}
