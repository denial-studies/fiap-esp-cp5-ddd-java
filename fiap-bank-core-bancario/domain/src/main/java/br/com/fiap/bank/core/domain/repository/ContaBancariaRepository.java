package br.com.fiap.bank.core.domain.repository;

import br.com.fiap.bank.core.domain.model.ContaBancaria;
import br.com.fiap.bank.core.domain.valueobject.Cpf;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContaBancariaRepository {

    ContaBancaria salvar(ContaBancaria conta);

    Optional<ContaBancaria> buscarPorId(UUID id);

    Optional<ContaBancaria> buscarPorNumero(String numero);

    Optional<ContaBancaria> buscarPorCpfCliente(Cpf cpf);

    List<ContaBancaria> buscarTodas();

    void deletarPorId(UUID id);

    boolean existePorNumero(String numero);
}
