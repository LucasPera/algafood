package com.algawork.algafood.jpa;

import com.algawork.algafood.AlgafoodApplication;
import com.algawork.algafood.domain.model.FormaPagamento;
import com.algawork.algafood.domain.repository.FormaPagamentoRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class FormaPagamentoMain {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApplication.class)
                .web(WebApplicationType.NONE) //não é uma aplicação web
                .run(args);

        FormaPagamentoRepository formaPagamentoRepository = applicationContext.getBean(FormaPagamentoRepository.class);

        //listar
        formaPagamentoRepository.listar().forEach(fp -> System.out.println("FormaPagamento: " + fp.getDescricao()));

        //buscar
        FormaPagamento formaPagamentoBuscada = formaPagamentoRepository.buscar(1L);
        System.out.println(formaPagamentoBuscada);


        //Atualizar
        FormaPagamento formaPagamentoAAtulizar = new FormaPagamento();
        formaPagamentoAAtulizar.setId(1L);
        formaPagamentoAAtulizar.setDescricao("Debitox");

        FormaPagamento formaPagamentoAtualizada = formaPagamentoRepository.salvar(formaPagamentoAAtulizar);
        System.out.println(formaPagamentoAtualizada);

        //inserir
        FormaPagamento formaPagamentoAInserir = new FormaPagamento();
        formaPagamentoAInserir.setDescricao("Pix");

        formaPagamentoRepository.salvar(formaPagamentoAInserir);

        //remover
        FormaPagamento formaPagamentoRemover = new FormaPagamento();
        formaPagamentoRemover.setId(2L);

        formaPagamentoRepository.remover(formaPagamentoRemover);


    }
}
