package com.algawork.algafood.jpa;

import com.algawork.algafood.AlgafoodApplication;
import com.algawork.algafood.domain.model.Cidade;
import com.algawork.algafood.domain.repository.CidadeRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class CidadeMain {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApplication.class)
                .web(WebApplicationType.NONE) //não é uma aplicação web
                .run(args);

        CidadeRepository cidadeRepository = applicationContext.getBean(CidadeRepository.class);

        //listar
        cidadeRepository.listar().forEach(c-> System.out.println("Cidade: " + c.getNome()));

        //buscar
        Cidade cidade = cidadeRepository.buscar(1L);
        System.out.println(cidade);

        //Atualizar
        Cidade cidadeAAtulizar = new Cidade();
        cidadeAAtulizar.setId(1L);
        cidadeAAtulizar.setNome("SP");

        Cidade cidadeAtualizado = cidadeRepository.salvar(cidadeAAtulizar);
        System.out.println(cidadeAtualizado);

        //inserir
        Cidade cidadeAInserir = new Cidade();
        cidadeAInserir.setNome("Osasco");
        cidadeRepository.salvar(cidadeAInserir);

        //remover
        Cidade cidadeRemover = new Cidade();
        cidadeRemover.setId(2L);

        cidadeRepository.remover(cidadeRemover);


    }
}
