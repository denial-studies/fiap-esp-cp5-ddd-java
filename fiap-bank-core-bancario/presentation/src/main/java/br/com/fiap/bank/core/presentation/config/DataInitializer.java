package br.com.fiap.bank.core.presentation.config;

import br.com.fiap.bank.core.application.dto.CriarClienteRequest;
import br.com.fiap.bank.core.application.dto.CriarContaRequest;
import br.com.fiap.bank.core.application.service.ClienteService;
import br.com.fiap.bank.core.application.service.ContaBancariaService;
import br.com.fiap.bank.core.domain.model.TipoConta;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ClienteService clienteService;
    private final ContaBancariaService contaBancariaService;

    public DataInitializer(ClienteService clienteService, ContaBancariaService contaBancariaService) {
        this.clienteService = clienteService;
        this.contaBancariaService = contaBancariaService;
    }

    @Override
    public void run(String... args) {
        if (clienteService.listarTodos().isEmpty()) {
            // Cliente 1: Ana Silva
            clienteService.cadastrar(new CriarClienteRequest(
                    "12345678900",
                    "Ana Silva",
                    "ana.silva@fiap.com.br",
                    LocalDate.of(1990, 5, 15),
                    new BigDecimal("7500.00")
            ));

            contaBancariaService.abrirConta(new CriarContaRequest(
                    "12345678900",
                    "0001",
                    TipoConta.CORRENTE,
                    new BigDecimal("12500.00")
            ), "12345-6");

            // Cliente 2: Carlos Eduardo Oliveira
            clienteService.cadastrar(new CriarClienteRequest(
                    "98765432100",
                    "Carlos Eduardo Oliveira",
                    "carlos.oliveira@fiap.com.br",
                    LocalDate.of(1985, 8, 20),
                    new BigDecimal("12000.00")
            ));

            contaBancariaService.abrirConta(new CriarContaRequest(
                    "98765432100",
                    "0001",
                    TipoConta.CORRENTE,
                    new BigDecimal("45800.50")
            ), "78901-2");
        }
    }
}
