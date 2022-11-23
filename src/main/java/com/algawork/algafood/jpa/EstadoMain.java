package com.algawork.algafood.jpa;

import com.algawork.algafood.AlgafoodApplication;
import com.algawork.algafood.domain.model.Estado;
import com.algawork.algafood.domain.repository.EstadoRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class EstadoMain {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApplication.class)
                .web(WebApplicationType.NONE) //não é uma aplicação web
                .run(args);

        EstadoRepository estadoRepository = applicationContext.getBean(EstadoRepository.class);

        //listar
        estadoRepository.listar().forEach(e -> System.out.println("Estado: " + e.getNome()));

        //buscar
        Estado estado = estadoRepository.buscar(1L);
        System.out.println(estado);


        //Atualizar
        Estado estadoAAtulizar = new Estado();
        estadoAAtulizar.setId(1L);
        estadoAAtulizar.setNome("sp");

        Estado estadoAtualizado = estadoRepository.salvar(estadoAAtulizar);
        System.out.println(estadoAtualizado);

        //inserir
        Estado estadoAInserir = new Estado();
        estadoAInserir.setNome("Goias");
        estadoRepository.salvar(estadoAInserir);

        //remover
        Estado estadoRemover = new Estado();
        estadoRemover.setId(2L);

        estadoRepository.remover(estadoRemover);


    }
}
