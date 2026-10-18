package br.com.fiap.bank.core.application.service;

import br.com.fiap.bank.core.application.dto.AtualizarClienteRequest;
import br.com.fiap.bank.core.application.dto.ClienteResponse;
import br.com.fiap.bank.core.application.dto.CriarClienteRequest;
import br.com.fiap.bank.core.application.exception.RecursoNaoEncontradoException;
import br.com.fiap.bank.core.application.exception.RegraNegocioException;
import br.com.fiap.bank.core.domain.model.Cliente;
import br.com.fiap.bank.core.domain.repository.ClienteRepository;
import br.com.fiap.bank.core.domain.valueobject.Cpf;
import br.com.fiap.bank.core.domain.valueobject.Email;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public ClienteResponse cadastrar(CriarClienteRequest request) {
        String cpfLimpo = sanitizarCpf(request.cpf());

        if (clienteRepository.existePorCpf(new Cpf(cpfLimpo))) {
            throw new RegraNegocioException("Já existe um cliente cadastrado com o CPF informado: " + request.cpf());
        }

        Cliente cliente = new Cliente(
                UUID.randomUUID(),
                request.nome(),
                new Email(request.email()),
                request.dataNascimento(),
                new Cpf(cpfLimpo),
                request.rendaMensal(),
                null
        );

        Cliente salvo = clienteRepository.salvar(cliente);
        return mapearParaResponse(salvo);
    }

    public ClienteResponse buscarPorId(UUID id) {
        Cliente cliente = clienteRepository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado com o ID: " + id));
        return mapearParaResponse(cliente);
    }

    public ClienteResponse buscarPorCpf(String cpf) {
        String cpfLimpo = sanitizarCpf(cpf);
        Cliente cliente = clienteRepository.buscarPorCpf(new Cpf(cpfLimpo))
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado com o CPF informado: " + cpf));
        return mapearParaResponse(cliente);
    }

    public List<ClienteResponse> listarTodos() {
        return clienteRepository.buscarTodos()
                .stream()
                .map(this::mapearParaResponse)
                .toList();
    }

    public ClienteResponse atualizar(UUID id, AtualizarClienteRequest request) {
        Cliente cliente = clienteRepository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado com o ID: " + id));

        cliente.setNome(request.nome());
        cliente.setEmail(new Email(request.email()));
        cliente.setDataNascimento(request.dataNascimento());
        cliente.setRendaMensal(request.rendaMensal());

        Cliente salvo = clienteRepository.salvar(cliente);
        return mapearParaResponse(salvo);
    }

    public void deletar(UUID id) {
        if (clienteRepository.buscarPorId(id).isEmpty()) {
            throw new RecursoNaoEncontradoException("Cliente não encontrado com o ID: " + id);
        }
        clienteRepository.deletarPorId(id);
    }

    private String sanitizarCpf(String cpf) {
        if (cpf == null) return "";
        return cpf.replaceAll("\\D", "");
    }

    private ClienteResponse mapearParaResponse(Cliente cliente) {
        String numeroConta = cliente.getConta() != null ? cliente.getConta().getNumero() : null;
        return new ClienteResponse(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail() != null ? cliente.getEmail().getValor() : null,
                cliente.getDataNascimento(),
                cliente.getCpf() != null ? cliente.getCpf().getValor() : null,
                cliente.getRendaMensal(),
                numeroConta
        );
    }
}
