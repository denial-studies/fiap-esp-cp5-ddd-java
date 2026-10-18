package br.com.fiap.bank.core.application.dto;

import br.com.fiap.bank.core.application.validation.Cpf;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CriarClienteRequest(
        @NotBlank(message = "O CPF é obrigatório.")
        @Cpf
        String cpf,

        @NotBlank(message = "O nome é obrigatório.")
        @Size(min = 3, max = 150, message = "O nome deve conter entre 3 e 150 caracteres.")
        String nome,

        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "O e-mail deve estar em formato válido.")
        String email,

        @NotNull(message = "A data de nascimento é obrigatória.")
        @Past(message = "A data de nascimento deve ser uma data no passado.")
        LocalDate dataNascimento,

        @NotNull(message = "A renda mensal é obrigatória.")
        @DecimalMin(value = "0.00", inclusive = true, message = "A renda mensal precisa ser maior ou igual a zero.")
        BigDecimal rendaMensal
) {}
