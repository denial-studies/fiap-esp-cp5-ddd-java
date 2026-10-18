package br.com.fiap.bank.core.application.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record AtualizarSaldoRequest(
        @NotBlank(message = "A operação é obrigatória.")
        @Pattern(regexp = "(?i)^(DEPOSITO|SAQUE)$", message = "A operação contábil aceita exclusivamente DEPOSITO ou SAQUE.")
        String operacao,

        @NotNull(message = "O montante a ser movimentado é obrigatório.")
        @DecimalMin(value = "0.01", inclusive = true, message = "O montante a ser movimentado deve ser estritamente maior que zero.")
        BigDecimal valor
) {}
