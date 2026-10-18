package br.com.fiap.bank.core.application.service;

import br.com.fiap.bank.core.application.dto.AtualizarSaldoRequest;
import br.com.fiap.bank.core.application.dto.ContaBancariaResponse;
import br.com.fiap.bank.core.application.dto.CriarContaRequest;
import br.com.fiap.bank.core.application.exception.RecursoNaoEncontradoException;
import br.com.fiap.bank.core.application.exception.RegraNegocioException;
import br.com.fiap.bank.core.domain.model.Cliente;
import br.com.fiap.bank.core.domain.model.ContaBancaria;
import br.com.fiap.bank.core.domain.model.StatusConta;
import br.com.fiap.bank.core.domain.repository.ClienteRepository;
import br.com.fiap.bank.core.domain.repository.ContaBancariaRepository;
import br.com.fiap.bank.core.domain.valueobject.Cpf;
import br.com.fiap.bank.core.domain.valueobject.Saldo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class ContaBancariaService {

    private final ContaBancariaRepository contaBancariaRepository;
    private final ClienteRepository clienteRepository;
    private final Random random = new Random();

    public ContaBancariaService(ContaBancariaRepository contaBancariaRepository, ClienteRepository clienteRepository) {
        this.contaBancariaRepository = contaBancariaRepository;
        this.clienteRepository = clienteRepository;
    }

    public ContaBancariaResponse abrirConta(CriarContaRequest request) {
        String cpfLimpo = sanitizarCpf(request.cpfCliente());

        Cliente cliente = clienteRepository.buscarPorCpf(new Cpf(cpfLimpo))
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado para o CPF informado: " + request.cpfCliente()));

        if (cliente.getConta() != null) {
            throw new RegraNegocioException("O cliente já possui uma conta bancária vinculada.");
        }

        String numeroConta = gerarNumeroContaUnico();
        String agencia = request.obterAgenciaPadrao();

        ContaBancaria conta = new ContaBancaria(
                UUID.randomUUID(),
                numeroConta,
                agencia,
                new Saldo(request.saldoInicial()),
                LocalDate.now(),
                StatusConta.ATIVA,
                request.tipoConta()
        );

        ContaBancaria contaSalva = contaBancariaRepository.salvar(conta);

        cliente.setConta(contaSalva);
        clienteRepository.salvar(cliente);

        return mapearParaResponse(contaSalva, cpfLimpo);
    }

    public ContaBancariaResponse buscarPorIdentificador(String identificador) {
        ContaBancaria conta = obterContaPorIdentificador(identificador);
        return mapearParaResponse(conta, null);
    }

    public ContaBancariaResponse buscarPorCpfCliente(String cpf) {
        String cpfLimpo = sanitizarCpf(cpf);
        ContaBancaria conta = contaBancariaRepository.buscarPorCpfCliente(new Cpf(cpfLimpo))
                .orElseThrow(() -> new RecursoNaoEncontradoException("Conta bancária não encontrada para o CPF informado: " + cpf));
        return mapearParaResponse(conta, cpfLimpo);
    }

    public List<ContaBancariaResponse> listarTodas() {
        return contaBancariaRepository.buscarTodas()
                .stream()
                .map(c -> mapearParaResponse(c, null))
                .toList();
    }

    public ContaBancariaResponse atualizarSaldo(String identificador, AtualizarSaldoRequest request) {
        ContaBancaria conta = obterContaPorIdentificador(identificador);

        try {
            if ("DEPOSITO".equalsIgnoreCase(request.operacao())) {
                conta.depositar(request.valor());
            } else if ("SAQUE".equalsIgnoreCase(request.operacao())) {
                conta.sacar(request.valor());
            } else {
                throw new RegraNegocioException("Operação contábil inválida. Aceita exclusivamente DEPOSITO ou SAQUE.");
            }
        } catch (IllegalArgumentException | IllegalStateException ex) {
            throw new RegraNegocioException(ex.getMessage());
        }

        ContaBancaria contaAtualizada = contaBancariaRepository.salvar(conta);
        return mapearParaResponse(contaAtualizada, null);
    }

    public void encerrarConta(String identificador) {
        ContaBancaria conta = obterContaPorIdentificador(identificador);
        conta.encerrar();
        contaBancariaRepository.salvar(conta);
    }

    private ContaBancaria obterContaPorIdentificador(String identificador) {
        try {
            UUID id = UUID.fromString(identificador);
            return contaBancariaRepository.buscarPorId(id)
                    .orElseGet(() -> contaBancariaRepository.buscarPorNumero(identificador)
                            .orElseThrow(() -> new RecursoNaoEncontradoException("Conta bancária não encontrada: " + identificador)));
        } catch (IllegalArgumentException e) {
            return contaBancariaRepository.buscarPorNumero(identificador)
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Conta bancária não encontrada com o número: " + identificador));
        }
    }

    private String gerarNumeroContaUnico() {
        String numero;
        do {
            int principal = 10000 + random.nextInt(90000);
            int digito = random.nextInt(10);
            numero = principal + "-" + digito;
        } while (contaBancariaRepository.existePorNumero(numero));
        return numero;
    }

    private String sanitizarCpf(String cpf) {
        if (cpf == null) return "";
        return cpf.replaceAll("\\D", "");
    }

    private ContaBancariaResponse mapearParaResponse(ContaBancaria conta, String cpfCliente) {
        return new ContaBancariaResponse(
                conta.getId(),
                conta.getNumero(),
                conta.getAgencia(),
                conta.getSaldo() != null ? conta.getSaldo().getValor() : null,
                conta.getDataAbertura(),
                conta.getStatus(),
                conta.getTipo(),
                cpfCliente
        );
    }
}
