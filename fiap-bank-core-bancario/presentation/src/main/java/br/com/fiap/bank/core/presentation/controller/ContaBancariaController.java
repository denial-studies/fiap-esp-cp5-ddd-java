package br.com.fiap.bank.core.presentation.controller;

import br.com.fiap.bank.core.application.dto.AtualizarSaldoRequest;
import br.com.fiap.bank.core.application.dto.ContaBancariaResponse;
import br.com.fiap.bank.core.application.dto.CriarContaRequest;
import br.com.fiap.bank.core.application.service.ContaBancariaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contas")
public class ContaBancariaController {

    private final ContaBancariaService contaBancariaService;

    public ContaBancariaController(ContaBancariaService contaBancariaService) {
        this.contaBancariaService = contaBancariaService;
    }

    @GetMapping
    public List<ContaBancariaResponse> listarTodas() {
        return contaBancariaService.listarTodas();
    }

    @GetMapping("/{id}")
    public ContaBancariaResponse buscarPorIdentificador(@PathVariable String id) {
        return contaBancariaService.buscarPorIdentificador(id);
    }

    @GetMapping("/cliente/{cpf}")
    public ContaBancariaResponse buscarPorCpfCliente(@PathVariable String cpf) {
        return contaBancariaService.buscarPorCpfCliente(cpf);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContaBancariaResponse abrirConta(@Valid @RequestBody CriarContaRequest request) {
        return contaBancariaService.abrirConta(request);
    }

    @PutMapping("/{id}/saldo")
    public ContaBancariaResponse atualizarSaldo(@PathVariable String id, @Valid @RequestBody AtualizarSaldoRequest request) {
        return contaBancariaService.atualizarSaldo(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void encerrarConta(@PathVariable String id) {
        contaBancariaService.encerrarConta(id);
    }
}
