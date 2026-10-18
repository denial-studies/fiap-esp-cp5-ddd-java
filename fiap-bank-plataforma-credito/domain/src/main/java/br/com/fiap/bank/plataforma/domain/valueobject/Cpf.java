package br.com.fiap.bank.plataforma.domain.valueobject;

import java.util.Objects;

public class Cpf {

    private final String valor;

    public Cpf(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("O CPF nao pode ser nulo ou vazio.");
        }
        String limpo = valor.replaceAll("\\D", "");
        if (limpo.length() != 11) {
            throw new IllegalArgumentException("O CPF deve conter exatamente 11 digitos numericos.");
        }
        this.valor = limpo;
    }

    public String getValor() {
        return valor;
    }

    public String getFormatado() {
        if (valor == null || valor.length() != 11) {
            return valor;
        }
        return valor.substring(0, 3) + "." +
               valor.substring(3, 6) + "." +
               valor.substring(6, 9) + "-" +
               valor.substring(9, 11);
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
