package br.com.fiap.bank.core.domain.valueobject;

import java.util.Objects;

public class Email {

    private final String valor;

    public Email(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("O e-mail não pode ser nulo ou vazio.");
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
        Email email = (Email) o;
        return Objects.equals(valor, email.valor);
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
