package br.com.fiap.bank.core.infrastructure.repository;

import br.com.fiap.bank.core.infrastructure.entity.ContaBancariaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContaBancariaJpaRepository extends JpaRepository<ContaBancariaEntity, UUID> {

    Optional<ContaBancariaEntity> findByNumero(String numero);

    boolean existsByNumero(String numero);

    @Query("SELECT c.conta FROM ClienteEntity c WHERE c.cpf = :cpf")
    Optional<ContaBancariaEntity> findByClienteCpf(@Param("cpf") String cpf);
}
