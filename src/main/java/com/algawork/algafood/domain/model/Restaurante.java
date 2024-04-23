package com.algawork.algafood.domain.model;

import com.algawork.algafood.core.validation.Groups;
import com.algawork.algafood.core.validation.TaxaFrete;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Restaurante {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @NotNull
    //não pode null nem string vazia
//    @NotEmpty
//  Não pode ser null, nem string vazia e nem espaço em branco
    @NotBlank
    @Column(nullable = false)
    private String nome;

    //tem q passar o valor minimo 1
    //@DecimalMin("1")
    @NotNull
//    @PositiveOrZero(message = "{TaxaFrete.invalida}")
    @TaxaFrete
    @Column(name = "taxa_frete", nullable = false)
    private BigDecimal taxaFrete;

//    @JsonIgnore
//    @JsonIgnoreProperties("hibernateLazyInitializer")
    //Valida todas as propriedades de cozinha (que estão anotas dentro dela)
    @Valid
    @ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
    @NotNull
    @ManyToOne
    @JoinColumn(name = "cozinha_id", nullable = false)
    private Cozinha cozinha;

    @JsonIgnore
    @Embedded
    private Endereco endereco;

    //cria um timestamp automaticamente e atribui a essa propriedade
    @JsonIgnore
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime dataCadastro;

    @JsonIgnore
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;

    //cria tabela n para n e nomeia os ids
//    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "restaurante_forma_pagamento",
        joinColumns = @JoinColumn(name = "restaurante_id"),
        inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private List<FormaPagamento> formasPagamento = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "restaurante")
    private List<Produto> produto = new ArrayList<>();
}
