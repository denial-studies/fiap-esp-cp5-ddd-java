package br.com.fiap.bank.core.application.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CpfValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT})
@Retention(RetentionPolicy.RUNTIME)
public @interface Cpf {

    String message() default "O CPF deve conter exatamente 11 dígitos numéricos ou o formato canônico XXX.XXX.XXX-XX.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
