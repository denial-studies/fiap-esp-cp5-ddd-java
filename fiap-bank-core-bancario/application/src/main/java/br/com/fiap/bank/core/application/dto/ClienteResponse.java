package br.com.fiap.bank.core.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ClienteResponse(
        UUID id,
        String nome,
        String email,
        LocalDate dataNascimento,
        String cpf,
        BigDecimal rendaMensal,
        String numeroConta
) {}
