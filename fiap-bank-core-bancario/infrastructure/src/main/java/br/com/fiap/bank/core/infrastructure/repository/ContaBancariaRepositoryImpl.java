package br.com.fiap.bank.core.infrastructure.repository;

import br.com.fiap.bank.core.domain.model.ContaBancaria;
import br.com.fiap.bank.core.domain.repository.ContaBancariaRepository;
import br.com.fiap.bank.core.domain.valueobject.Cpf;
import br.com.fiap.bank.core.infrastructure.entity.ContaBancariaEntity;
import br.com.fiap.bank.core.infrastructure.mapper.ContaBancariaMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ContaBancariaRepositoryImpl implements ContaBancariaRepository {

    private final ContaBancariaJpaRepository jpaRepository;
    private final ContaBancariaMapper mapper;

    public ContaBancariaRepositoryImpl(ContaBancariaJpaRepository jpaRepository, ContaBancariaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public ContaBancaria salvar(ContaBancaria conta) {
        ContaBancariaEntity entity = mapper.toEntity(conta);
        ContaBancariaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<ContaBancaria> buscarPorId(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<ContaBancaria> buscarPorNumero(String numero) {
        return jpaRepository.findByNumero(numero).map(mapper::toDomain);
    }

    @Override
    public Optional<ContaBancaria> buscarPorCpfCliente(Cpf cpf) {
        return jpaRepository.findByClienteCpf(cpf.getValor()).map(mapper::toDomain);
    }

    @Override
    public List<ContaBancaria> buscarTodas() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deletarPorId(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existePorNumero(String numero) {
        return jpaRepository.existsByNumero(numero);
    }
}
