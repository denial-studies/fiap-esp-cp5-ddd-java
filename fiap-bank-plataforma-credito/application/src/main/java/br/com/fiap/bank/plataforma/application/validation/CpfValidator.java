package br.com.fiap.bank.plataforma.application.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class CpfValidator implements ConstraintValidator<Cpf, String> {

    private static final Pattern CPF_PATTERN = Pattern.compile("(^\\d{11}$)|(^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$)");

    @Override
    public boolean isValid(String valor, ConstraintValidatorContext context) {
        if (valor == null || valor.isBlank()) {
            return true;
        }
        return CPF_PATTERN.matcher(valor.trim()).matches();
    }
}
