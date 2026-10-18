package br.com.fiap.bank.core.domain.model;

import br.com.fiap.bank.core.domain.valueobject.Saldo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class ContaBancaria {

    private UUID id;
    private String numero;
    private String agencia;
    private Saldo saldo;
    private LocalDate dataAbertura;
    private StatusConta status;
    private TipoConta tipo;

    public ContaBancaria() {
    }

    public ContaBancaria(UUID id, String numero, String agencia, Saldo saldo, LocalDate dataAbertura, StatusConta status, TipoConta tipo) {
        this.id = id;
        this.numero = numero;
        this.agencia = agencia;
        this.saldo = saldo;
        this.dataAbertura = dataAbertura;
        this.status = status;
        this.tipo = tipo;
    }

    public void depositar(BigDecimal quantia) {
        if (this.status != StatusConta.ATIVA) {
            throw new IllegalStateException("Não é possível realizar depósito em uma conta que não está ativa.");
        }
        this.saldo = this.saldo.adicionar(quantia);
    }

    public void sacar(BigDecimal quantia) {
        if (this.status != StatusConta.ATIVA) {
            throw new IllegalStateException("Não é possível realizar saque em uma conta que não está ativa.");
        }
        this.saldo = this.saldo.subtrair(quantia);
    }

    public void encerrar() {
        this.status = StatusConta.ENCERRADA;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public Saldo getSaldo() {
        return saldo;
    }

    public void setSaldo(Saldo saldo) {
        this.saldo = saldo;
    }

    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDate dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public StatusConta getStatus() {
        return status;
    }

    public void setStatus(StatusConta status) {
        this.status = status;
    }

    public TipoConta getTipo() {
        return tipo;
    }

    public void setTipo(TipoConta tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContaBancaria that = (ContaBancaria) o;
        return Objects.equals(id, that.id) || (numero != null && Objects.equals(numero, that.numero));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numero);
    }

    @Override
    public String toString() {
        return "ContaBancaria{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", agencia='" + agencia + '\'' +
                ", saldo=" + saldo +
                ", dataAbertura=" + dataAbertura +
                ", status=" + status +
                ", tipo=" + tipo +
                '}';
    }
}
