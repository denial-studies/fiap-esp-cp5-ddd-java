package br.com.fiap.bank.plataforma.application.dto;

import br.com.fiap.bank.plataforma.application.validation.Cpf;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record SolicitarCreditoRequest(
    @NotBlank(message = "O CPF do cliente e obrigatorio")
    @Cpf
    String cpfCliente,

    @NotBlank(message = "O nome do cliente e obrigatorio")
    String nomeCliente,

    @NotBlank(message = "O tipo de credito e obrigatorio")
    String tipoCredito,

    @NotNull(message = "O valor solicitado e obrigatorio")
    @Positive(message = "O valor solicitado deve ser maior que zero")
    BigDecimal valorSolicitado,

    @NotNull(message = "A quantidade de parcelas e obrigatoria")
    @Positive(message = "A quantidade de parcelas deve ser maior que zero")
    Integer quantidadeParcelas,

    @NotNull(message = "A renda mensal e obrigatoria")
    @Positive(message = "A renda mensal deve ser maior que zero")
    BigDecimal rendaMensal
) {}
