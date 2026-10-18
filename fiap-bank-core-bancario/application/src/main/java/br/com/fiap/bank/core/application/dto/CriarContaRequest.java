package br.com.fiap.bank.core.application.dto;

import br.com.fiap.bank.core.application.validation.Cpf;
import br.com.fiap.bank.core.domain.model.TipoConta;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CriarContaRequest(
        @NotBlank(message = "O CPF do cliente é obrigatório.")
        @Cpf
        String cpfCliente,

        @Pattern(regexp = "^$|^\\d{4}$", message = "A agência deve possuir exatamente 4 dígitos numéricos.")
        String agencia,

        @NotNull(message = "O tipo da conta é obrigatório (CORRENTE, POUPANCA ou SALARIO).")
        TipoConta tipoConta,

        @NotNull(message = "O saldo inicial é obrigatório.")
        @DecimalMin(value = "0.00", inclusive = true, message = "O saldo inicial não pode ser negativo.")
        BigDecimal saldoInicial
) {
    public String obterAgenciaPadrao() {
        return (agencia == null || agencia.isBlank()) ? "0001" : agencia;
    }
}
