package br.com.fiap.bank.core.domain.valueobject;

import java.math.BigDecimal;
import java.util.Objects;

public class Saldo {

    private final BigDecimal valor;

    public Saldo(BigDecimal valor) {
        if (valor == null) {
            throw new IllegalArgumentException("O valor do saldo não pode ser nulo.");
        }
        this.valor = valor;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Saldo adicionar(BigDecimal quantia) {
        if (quantia == null || quantia.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("A quantia para adicionar deve ser maior que zero.");
        }
        return new Saldo(this.valor.add(quantia));
    }

    public Saldo subtrair(BigDecimal quantia) {
        if (quantia == null || quantia.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("A quantia para subtrair deve ser maior que zero.");
        }
        if (this.valor.compareTo(quantia) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar o saque.");
        }
        return new Saldo(this.valor.subtract(quantia));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Saldo saldo = (Saldo) o;
        return valor != null && saldo.valor != null && valor.compareTo(saldo.valor) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return valor != null ? valor.toString() : "0.00";
    }
}
