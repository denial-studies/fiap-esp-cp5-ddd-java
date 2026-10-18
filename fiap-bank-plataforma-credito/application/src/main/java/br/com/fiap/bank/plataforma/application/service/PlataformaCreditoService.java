package br.com.fiap.bank.plataforma.application.service;

import br.com.fiap.bank.plataforma.application.dto.*;
import br.com.fiap.bank.plataforma.application.exception.RecursoNaoEncontradoException;
import br.com.fiap.bank.plataforma.domain.model.SolicitacaoCredito;
import br.com.fiap.bank.plataforma.domain.model.StatusSolicitacao;
import br.com.fiap.bank.plataforma.domain.repository.SolicitacaoCreditoRepository;
import br.com.fiap.bank.plataforma.domain.valueobject.Cpf;
import br.com.fiap.bank.plataforma.infrastructure.client.BureauCreditoService;
import br.com.fiap.bank.plataforma.infrastructure.client.CoreBancarioClientService;
import br.com.fiap.bank.plataforma.infrastructure.client.NotificacaoService;
import br.com.fiap.bank.plataforma.infrastructure.client.ScrService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PlataformaCreditoService {

    private final SolicitacaoCreditoRepository repository;
    private final ScrService scrService;
    private final BureauCreditoService bureauService;
    private final NotificacaoService notificacaoService;
    private final CoreBancarioClientService coreBancarioClientService;

    public PlataformaCreditoService(SolicitacaoCreditoRepository repository,
                                    ScrService scrService,
                                    BureauCreditoService bureauService,
                                    NotificacaoService notificacaoService,
                                    CoreBancarioClientService coreBancarioClientService) {
        this.repository = repository;
        this.scrService = scrService;
        this.bureauService = bureauService;
        this.notificacaoService = notificacaoService;
        this.coreBancarioClientService = coreBancarioClientService;
    }

    @Transactional
    public SolicitacaoCreditoResponseDTO solicitarCredito(SolicitarCreditoRequest request) {
        Cpf cpf = new Cpf(request.cpfCliente());
        String cpfLimpo = cpf.getValor();

        // 1. Consulta SCR
        boolean temRestricaoScr = scrService.consultarRestricoesScr(cpfLimpo);

        // 2. Consulta Bureau de Credito
        int scoreBureau = bureauService.consultarScoreBureau(cpfLimpo);

        // 3. Consulta Core Bancario
        Map<String, Object> contaCore = coreBancarioClientService.consultarContaCliente(cpfLimpo);

        // 4. Motor de Regras de Credito
        StatusSolicitacao status;
        String parecer;
        int scoreFinal = scoreBureau;

        BigDecimal rendaMensal = (request.rendaMensal() != null) ? request.rendaMensal() : BigDecimal.ZERO;
        String nomeCliente = (request.nomeCliente() != null && !request.nomeCliente().isBlank())
                ? request.nomeCliente()
                : (contaCore != null && contaCore.get("titular") != null ? contaCore.get("titular").toString() : "Cliente FIAP");

        if (temRestricaoScr) {
            status = StatusSolicitacao.REPROVADO;
            parecer = "Solicitacao reprovada automaticamente devido a restricoes ativas no SCR (Banco Central).";
        } else if (scoreBureau < 500) {
            status = StatusSolicitacao.REPROVADO;
            parecer = "Solicitacao reprovada automaticamente. Score no Bureau de Credito insuficiente (" + scoreBureau + ").";
        } else {
            BigDecimal valor = (request.valorSolicitado() != null) ? request.valorSolicitado() : BigDecimal.ZERO;
            int parcelas = (request.quantidadeParcelas() != null && request.quantidadeParcelas() > 0) ? request.quantidadeParcelas() : 1;
            BigDecimal parcelaEstimada = valor.divide(BigDecimal.valueOf(parcelas), 2, RoundingMode.HALF_UP);
            BigDecimal trintaPercentoRenda = rendaMensal.multiply(new BigDecimal("0.30"));

            if (parcelaEstimada.compareTo(trintaPercentoRenda) > 0) {
                status = StatusSolicitacao.EM_ANALISE;
                parecer = "Encaminhado para a Mesa de Operacoes de Credito. Parcela estimada (R$ " + parcelaEstimada + ") compromete mais de 30% da renda informada.";
            } else {
                status = StatusSolicitacao.APROVADO;
                parecer = "Solicitacao aprovada automaticamente pelo Motor de Credito FIAP Bank!";
            }
        }

        SolicitacaoCredito solicitacao = new SolicitacaoCredito(
                null,
                cpf,
                nomeCliente,
                request.tipoCredito(),
                request.valorSolicitado(),
                request.quantidadeParcelas(),
                rendaMensal,
                scoreFinal,
                status,
                parecer,
                temRestricaoScr,
                scoreBureau,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        SolicitacaoCredito salva = repository.save(solicitacao);

        // 5. Envia Notificacao Externa
        notificacaoService.enviarNotificacao(cpfLimpo, "Sua solicitacao de credito #" + salva.getId() + " esta no status: " + status);

        return SolicitacaoCreditoResponseDTO.fromEntity(salva);
    }

    @Transactional(readOnly = true)
    public List<SolicitacaoCreditoResponseDTO> consultarStatusPorCpf(String cpf) {
        String cpfLimpo = (cpf != null) ? new Cpf(cpf).getValor() : "";
        return repository.findByCpfClienteOrderByDataSolicitacaoDesc(cpfLimpo).stream()
                .map(SolicitacaoCreditoResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SolicitacaoCreditoResponseDTO> consultarStatusPorCpf(ConsultarStatusRequest request) {
        return consultarStatusPorCpf(request.cpfCliente());
    }

    @Transactional(readOnly = true)
    public SolicitacaoCreditoResponseDTO buscarPorId(Long id) {
        SolicitacaoCredito solicitacao = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Solicitacao nao encontrada com ID: " + id));
        return SolicitacaoCreditoResponseDTO.fromEntity(solicitacao);
    }

    @Transactional(readOnly = true)
    public SolicitacaoCreditoResponseDTO buscarPorId(BuscarSolicitacaoRequest request) {
        return buscarPorId(request.solicitacaoId());
    }

    @Transactional(readOnly = true)
    public List<SolicitacaoCreditoResponseDTO> listarTodasSolicitacoes() {
        return repository.findAllByOrderByDataSolicitacaoDesc().stream()
                .map(SolicitacaoCreditoResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public SolicitacaoCreditoResponseDTO aprovarSolicitacao(Long id, String parecerOperador) {
        SolicitacaoCredito solicitacao = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Solicitacao de credito nao encontrada com ID: " + id));

        solicitacao.setStatus(StatusSolicitacao.APROVADO);
        solicitacao.setParecerOperador("Aprovado pela Mesa de Operacoes: " + (parecerOperador != null ? parecerOperador : "OK"));
        solicitacao.setDataAtualizacao(LocalDateTime.now());

        SolicitacaoCredito salva = repository.save(solicitacao);
        notificacaoService.enviarNotificacao(salva.getCpfCliente().getValor(), "PARABENS! Sua solicitacao de credito #" + salva.getId() + " foi APROVADA pela Mesa de Operacoes.");
        return SolicitacaoCreditoResponseDTO.fromEntity(salva);
    }

    @Transactional
    public SolicitacaoCreditoResponseDTO aprovarSolicitacao(AprovarSolicitacaoRequest request) {
        return aprovarSolicitacao(request.solicitacaoId(), request.parecerOperador());
    }

    @Transactional
    public SolicitacaoCreditoResponseDTO reprovarSolicitacao(Long id, String motivoReprovacao) {
        SolicitacaoCredito solicitacao = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Solicitacao de credito nao encontrada com ID: " + id));

        solicitacao.setStatus(StatusSolicitacao.REPROVADO);
        solicitacao.setParecerOperador("Reprovado pela Mesa de Operacoes. Motivo: " + (motivoReprovacao != null ? motivoReprovacao : "Inconformidade nos criterios de risco"));
        solicitacao.setDataAtualizacao(LocalDateTime.now());

        SolicitacaoCredito salva = repository.save(solicitacao);
        notificacaoService.enviarNotificacao(salva.getCpfCliente().getValor(), "Sua solicitacao de credito #" + salva.getId() + " foi REPROVADA pela Mesa de Operacoes.");
        return SolicitacaoCreditoResponseDTO.fromEntity(salva);
    }

    @Transactional
    public SolicitacaoCreditoResponseDTO reprovarSolicitacao(ReprovarSolicitacaoRequest request) {
        return reprovarSolicitacao(request.solicitacaoId(), request.motivoReprovacao());
    }

    public Map<String, Object> consultarContaCliente(String cpfCliente) {
        return coreBancarioClientService.consultarContaCliente(cpfCliente);
    }
}
