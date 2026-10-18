package br.com.fiap.bank.core.domain.valueobject;

import java.util.Objects;

public class Cpf {

    private final String valor;

    public Cpf(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("O CPF não pode ser nulo ou vazio.");
        }
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cpf cpf = (Cpf) o;
        return Objects.equals(valor, cpf.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return valor;
    }
}
