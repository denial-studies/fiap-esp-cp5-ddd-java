package br.com.fiap.bank.core.application.dto;

import br.com.fiap.bank.core.domain.model.StatusConta;
import br.com.fiap.bank.core.domain.model.TipoConta;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ContaBancariaResponse(
        UUID id,
        String numero,
        String agencia,
        BigDecimal saldo,
        LocalDate dataAbertura,
        StatusConta status,
        TipoConta tipo,
        String cpfCliente
) {}
