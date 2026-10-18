package br.com.fiap.bank.plataforma.infrastructure.repository;

import br.com.fiap.bank.plataforma.domain.model.SolicitacaoCredito;
import br.com.fiap.bank.plataforma.domain.repository.SolicitacaoCreditoRepository;
import br.com.fiap.bank.plataforma.infrastructure.entity.SolicitacaoCreditoEntity;
import br.com.fiap.bank.plataforma.infrastructure.mapper.SolicitacaoCreditoMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class SolicitacaoCreditoRepositoryImpl implements SolicitacaoCreditoRepository {

    private final SolicitacaoCreditoJpaRepository jpaRepository;
    private final SolicitacaoCreditoMapper mapper;

    public SolicitacaoCreditoRepositoryImpl(SolicitacaoCreditoJpaRepository jpaRepository,
                                            SolicitacaoCreditoMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public SolicitacaoCredito save(SolicitacaoCredito solicitacao) {
        SolicitacaoCreditoEntity entity = mapper.toEntity(solicitacao);
        SolicitacaoCreditoEntity salva = jpaRepository.save(entity);
        return mapper.toDomain(salva);
    }

    @Override
    public Optional<SolicitacaoCredito> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<SolicitacaoCredito> findByCpfClienteOrderByDataSolicitacaoDesc(String cpfCliente) {
        return jpaRepository.findByCpfClienteOrderByDataSolicitacaoDesc(cpfCliente).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<SolicitacaoCredito> findAllByOrderByDataSolicitacaoDesc() {
        return jpaRepository.findAllByOrderByDataSolicitacaoDesc().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
