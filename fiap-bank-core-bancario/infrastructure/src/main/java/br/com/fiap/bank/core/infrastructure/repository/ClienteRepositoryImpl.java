package br.com.fiap.bank.core.infrastructure.repository;

import br.com.fiap.bank.core.domain.model.Cliente;
import br.com.fiap.bank.core.domain.repository.ClienteRepository;
import br.com.fiap.bank.core.domain.valueobject.Cpf;
import br.com.fiap.bank.core.infrastructure.entity.ClienteEntity;
import br.com.fiap.bank.core.infrastructure.mapper.ClienteMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ClienteRepositoryImpl implements ClienteRepository {

    private final ClienteJpaRepository jpaRepository;
    private final ClienteMapper mapper;

    public ClienteRepositoryImpl(ClienteJpaRepository jpaRepository, ClienteMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Cliente salvar(Cliente cliente) {
        ClienteEntity entity = mapper.toEntity(cliente);
        ClienteEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Cliente> buscarPorId(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Cliente> buscarPorCpf(Cpf cpf) {
        return jpaRepository.findByCpf(cpf.getValor()).map(mapper::toDomain);
    }

    @Override
    public List<Cliente> buscarTodos() {
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
    public boolean existePorCpf(Cpf cpf) {
        return jpaRepository.existsByCpf(cpf.getValor());
    }
}
