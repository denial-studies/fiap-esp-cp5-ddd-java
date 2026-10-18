package br.com.fiap.bank.core.infrastructure.entity;

import br.com.fiap.bank.core.domain.model.StatusConta;
import br.com.fiap.bank.core.domain.model.TipoConta;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "contas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContaBancariaEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "numero", nullable = false, unique = true, length = 20)
    private String numero;

    @Column(name = "agencia", nullable = false, length = 10)
    private String agencia;

    @Column(name = "saldo", nullable = false, precision = 15, scale = 2)
    private BigDecimal saldo;

    @Column(name = "data_abertura", nullable = false)
    private LocalDate dataAbertura;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusConta status;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoConta tipo;
}
