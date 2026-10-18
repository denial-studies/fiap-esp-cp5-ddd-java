package br.com.fiap.bank.plataforma.infrastructure.mapper;

import br.com.fiap.bank.plataforma.domain.model.SolicitacaoCredito;
import br.com.fiap.bank.plataforma.infrastructure.entity.SolicitacaoCreditoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SolicitacaoCreditoMapper {

    SolicitacaoCreditoEntity toEntity(SolicitacaoCredito domain);

    SolicitacaoCredito toDomain(SolicitacaoCreditoEntity entity);
}
