package br.com.fiap.bank.core.domain.model;

import br.com.fiap.bank.core.domain.valueobject.Cpf;
import br.com.fiap.bank.core.domain.valueobject.Email;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Cliente {

    private UUID id;
    private String nome;
    private Email email;
    private LocalDate dataNascimento;
    private Cpf cpf;
    private BigDecimal rendaMensal;
    private ContaBancaria conta;

    public Cliente() {
    }

    public Cliente(UUID id, String nome, Email email, LocalDate dataNascimento, Cpf cpf, BigDecimal rendaMensal, ContaBancaria conta) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.rendaMensal = rendaMensal;
        this.conta = conta;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Cpf getCpf() {
        return cpf;
    }

    public void setCpf(Cpf cpf) {
        this.cpf = cpf;
    }

    public BigDecimal getRendaMensal() {
        return rendaMensal;
    }

    public void setRendaMensal(BigDecimal rendaMensal) {
        this.rendaMensal = rendaMensal;
    }

    public ContaBancaria getConta() {
        return conta;
    }

    public void setConta(ContaBancaria conta) {
        this.conta = conta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id) || (cpf != null && Objects.equals(cpf, cliente.cpf));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cpf);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email=" + email +
                ", dataNascimento=" + dataNascimento +
                ", cpf=" + cpf +
                ", rendaMensal=" + rendaMensal +
                ", conta=" + (conta != null ? conta.getNumero() : "null") +
                '}';
    }
}
