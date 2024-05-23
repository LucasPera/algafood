package com.algawork.algafood.api.model.mixin;

import com.algawork.algafood.domain.model.Cozinha;
import com.algawork.algafood.domain.model.Endereco;
import com.algawork.algafood.domain.model.FormaPagamento;
import com.algawork.algafood.domain.model.Produto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//separa as anotações do jackson da entidade
public class RestauranteMixin {
    @JsonIgnoreProperties("nome")
    private Cozinha cozinha;

    @JsonIgnore
    private Endereco endereco;

    //cria um timestamp automaticamente e atribui a essa propriedade
    @JsonIgnore
    private LocalDateTime dataCadastro;

    @JsonIgnore
    private LocalDateTime dataAtualizacao;

    @JsonIgnore
    private List<FormaPagamento> formasPagamento = new ArrayList<>();

    @JsonIgnore
    private List<Produto> produto = new ArrayList<>();
}
