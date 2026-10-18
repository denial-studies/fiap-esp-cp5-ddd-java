package br.com.fiap.bank.plataforma.infrastructure.mapper;

import br.com.fiap.bank.plataforma.domain.model.SolicitacaoCredito;
import br.com.fiap.bank.plataforma.domain.valueobject.Cpf;
import br.com.fiap.bank.plataforma.infrastructure.entity.SolicitacaoCreditoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SolicitacaoCreditoMapper {

    SolicitacaoCreditoEntity toEntity(SolicitacaoCredito domain);

    SolicitacaoCredito toDomain(SolicitacaoCreditoEntity entity);

    default String map(Cpf cpf) {
        return cpf != null ? cpf.getValor() : null;
    }

    default Cpf mapToCpf(String valor) {
        return valor != null ? new Cpf(valor) : null;
    }
}
