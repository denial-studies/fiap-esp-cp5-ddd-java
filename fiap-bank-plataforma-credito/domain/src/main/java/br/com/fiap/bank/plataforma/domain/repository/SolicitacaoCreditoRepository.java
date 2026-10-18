package br.com.fiap.bank.plataforma.domain.repository;

import br.com.fiap.bank.plataforma.domain.model.SolicitacaoCredito;

import java.util.List;
import java.util.Optional;

public interface SolicitacaoCreditoRepository {

    SolicitacaoCredito save(SolicitacaoCredito solicitacao);

    Optional<SolicitacaoCredito> findById(Long id);

    List<SolicitacaoCredito> findByCpfClienteOrderByDataSolicitacaoDesc(String cpfCliente);

    List<SolicitacaoCredito> findAllByOrderByDataSolicitacaoDesc();

    default SolicitacaoCredito salvar(SolicitacaoCredito solicitacao) {
        return save(solicitacao);
    }

    default Optional<SolicitacaoCredito> buscarPorId(Long id) {
        return findById(id);
    }

    default List<SolicitacaoCredito> buscarPorCpf(String cpfCliente) {
        return findByCpfClienteOrderByDataSolicitacaoDesc(cpfCliente);
    }

    default List<SolicitacaoCredito> listarTodas() {
        return findAllByOrderByDataSolicitacaoDesc();
    }
}
