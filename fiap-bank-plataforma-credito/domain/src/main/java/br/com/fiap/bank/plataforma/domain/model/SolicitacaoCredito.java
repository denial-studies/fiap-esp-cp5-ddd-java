package br.com.fiap.bank.plataforma.domain.model;

import br.com.fiap.bank.plataforma.domain.valueobject.Cpf;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SolicitacaoCredito {

    private Long id;
    private Cpf cpfCliente;
    private String nomeCliente;
    private String tipoCredito;
    private BigDecimal valorSolicitado;
    private Integer quantidadeParcelas;
    private BigDecimal rendaMensal;
    private Integer scoreCredito;
    private StatusSolicitacao status;
    private String parecerOperador;
    private Boolean scrRestricoes;
    private Integer scoreBureau;
    private LocalDateTime dataSolicitacao;
    private LocalDateTime dataAtualizacao;

    public SolicitacaoCredito() {
    }

    public SolicitacaoCredito(Long id, Cpf cpfCliente, String nomeCliente, String tipoCredito,
                              BigDecimal valorSolicitado, Integer quantidadeParcelas, BigDecimal rendaMensal,
                              Integer scoreCredito, StatusSolicitacao status, String parecerOperador,
                              Boolean scrRestricoes, Integer scoreBureau, LocalDateTime dataSolicitacao,
                              LocalDateTime dataAtualizacao) {
        this.id = id;
        this.cpfCliente = cpfCliente;
        this.nomeCliente = nomeCliente;
        this.tipoCredito = tipoCredito;
        this.valorSolicitado = valorSolicitado;
        this.quantidadeParcelas = quantidadeParcelas;
        this.rendaMensal = rendaMensal;
        this.scoreCredito = scoreCredito;
        this.status = status;
        this.parecerOperador = parecerOperador;
        this.scrRestricoes = scrRestricoes;
        this.scoreBureau = scoreBureau;
        this.dataSolicitacao = dataSolicitacao;
        this.dataAtualizacao = dataAtualizacao;
    }

    public SolicitacaoCredito(Long id, String cpfCliente, String nomeCliente, String tipoCredito,
                              BigDecimal valorSolicitado, Integer quantidadeParcelas, BigDecimal rendaMensal,
                              Integer scoreCredito, StatusSolicitacao status, String parecerOperador,
                              Boolean scrRestricoes, Integer scoreBureau, LocalDateTime dataSolicitacao,
                              LocalDateTime dataAtualizacao) {
        this(id, cpfCliente != null ? new Cpf(cpfCliente) : null, nomeCliente, tipoCredito,
                valorSolicitado, quantidadeParcelas, rendaMensal, scoreCredito, status,
                parecerOperador, scrRestricoes, scoreBureau, dataSolicitacao, dataAtualizacao);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cpf getCpfCliente() {
        return cpfCliente;
    }

    public String getCpfClienteValor() {
        return cpfCliente != null ? cpfCliente.getValor() : null;
    }

    public void setCpfCliente(Cpf cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente != null ? new Cpf(cpfCliente) : null;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getTipoCredito() {
        return tipoCredito;
    }

    public void setTipoCredito(String tipoCredito) {
        this.tipoCredito = tipoCredito;
    }

    public BigDecimal getValorSolicitado() {
        return valorSolicitado;
    }

    public void setValorSolicitado(BigDecimal valorSolicitado) {
        this.valorSolicitado = valorSolicitado;
    }

    public Integer getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public void setQuantidadeParcelas(Integer quantidadeParcelas) {
        this.quantidadeParcelas = quantidadeParcelas;
    }

    public BigDecimal getRendaMensal() {
        return rendaMensal;
    }

    public void setRendaMensal(BigDecimal rendaMensal) {
        this.rendaMensal = rendaMensal;
    }

    public Integer getScoreCredito() {
        return scoreCredito;
    }

    public void setScoreCredito(Integer scoreCredito) {
        this.scoreCredito = scoreCredito;
    }

    public StatusSolicitacao getStatus() {
        return status;
    }

    public void setStatus(StatusSolicitacao status) {
        this.status = status;
    }

    public String getParecerOperador() {
        return parecerOperador;
    }

    public void setParecerOperador(String parecerOperador) {
        this.parecerOperador = parecerOperador;
    }

    public Boolean getScrRestricoes() {
        return scrRestricoes;
    }

    public void setScrRestricoes(Boolean scrRestricoes) {
        this.scrRestricoes = scrRestricoes;
    }

    public Integer getScoreBureau() {
        return scoreBureau;
    }

    public void setScoreBureau(Integer scoreBureau) {
        this.scoreBureau = scoreBureau;
    }

    public LocalDateTime getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDateTime dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}
