package br.com.fiap.bank.plataforma.infrastructure.repository;

import br.com.fiap.bank.plataforma.infrastructure.entity.SolicitacaoCreditoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitacaoCreditoJpaRepository extends JpaRepository<SolicitacaoCreditoEntity, Long> {

    List<SolicitacaoCreditoEntity> findByCpfClienteOrderByDataSolicitacaoDesc(String cpfCliente);

    List<SolicitacaoCreditoEntity> findAllByOrderByDataSolicitacaoDesc();
}
