package br.com.fiap.bank.core.infrastructure.mapper;

import br.com.fiap.bank.core.domain.model.Cliente;
import br.com.fiap.bank.core.domain.valueobject.Cpf;
import br.com.fiap.bank.core.domain.valueobject.Email;
import br.com.fiap.bank.core.infrastructure.entity.ClienteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ContaBancariaMapper.class})
public interface ClienteMapper {

    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "conta", source = "conta")
    ClienteEntity toEntity(Cliente domain);

    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "conta", source = "conta")
    Cliente toDomain(ClienteEntity entity);

    default String map(Cpf cpf) {
        return cpf != null ? cpf.getValor() : null;
    }

    default Cpf mapToCpf(String valor) {
        return valor != null ? new Cpf(valor) : null;
    }

    default String map(Email email) {
        return email != null ? email.getValor() : null;
    }

    default Email mapToEmail(String valor) {
        return valor != null ? new Email(valor) : null;
    }
}
