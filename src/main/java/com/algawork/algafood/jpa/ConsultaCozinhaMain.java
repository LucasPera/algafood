package com.algawork.algafood.jpa;

import com.algawork.algafood.AlgafoodApplication;
import com.algawork.algafood.domain.model.Cozinha;
import com.algawork.algafood.domain.repository.CozinhaRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class ConsultaCozinhaMain {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApplication.class)
                .web(WebApplicationType.NONE) //não é uma aplicação web
                .run(args);

        CozinhaRepository cadastroCozinha = applicationContext.getBean(CozinhaRepository.class);

//        List<Cozinha> cozinhaList = cadastroCozinha.listar();
//
//        cozinhaList.forEach(c -> System.out.println(c.getNome()));

        Cozinha cozinha = cadastroCozinha.buscar(1L);

        System.out.println(cozinha.getNome());
    }

}
