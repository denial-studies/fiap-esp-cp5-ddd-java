package br.com.fiap.bank.core.infrastructure.mapper;

import br.com.fiap.bank.core.domain.model.ContaBancaria;
import br.com.fiap.bank.core.domain.valueobject.Saldo;
import br.com.fiap.bank.core.infrastructure.entity.ContaBancariaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface ContaBancariaMapper {

    @Mapping(target = "saldo", source = "saldo")
    ContaBancariaEntity toEntity(ContaBancaria domain);

    @Mapping(target = "saldo", source = "saldo")
    ContaBancaria toDomain(ContaBancariaEntity entity);

    default BigDecimal map(Saldo saldo) {
        return saldo != null ? saldo.getValor() : null;
    }

    default Saldo mapToSaldo(BigDecimal valor) {
        return valor != null ? new Saldo(valor) : null;
    }
}
