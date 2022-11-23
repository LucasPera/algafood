package com.algawork.algafood.jpa;

import com.algawork.algafood.AlgafoodApplication;
import com.algawork.algafood.domain.model.Permissao;
import com.algawork.algafood.domain.repository.PermissaoRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class PermissaoMain {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApplication.class)
                .web(WebApplicationType.NONE) //não é uma aplicação web
                .run(args);

        PermissaoRepository permissaoRepository = applicationContext.getBean(PermissaoRepository.class);

        //listar
        permissaoRepository.listar().forEach(fp -> System.out.println("FormaPagamento: " + fp.getDescricao()));

        //buscar
        Permissao permissao = permissaoRepository.buscar(1L);
        System.out.println(permissao);


        //Atualizar
        Permissao permissaoAAtulizar = new Permissao();
        permissaoAAtulizar.setId(1L);
        permissaoAAtulizar.setNome("Gravação");
        permissaoAAtulizar.setDescricao("Permissao Gravação");

        Permissao permissaoAtualizada = permissaoRepository.salvar(permissaoAAtulizar);
        System.out.println(permissaoAtualizada);

        //inserir
        Permissao permissaoAInserir = new Permissao();
        permissaoAInserir.setNome("Permissao inserida");
        permissaoAInserir.setDescricao("Permissao inserida descricao");

        permissaoRepository.salvar(permissaoAInserir);

        //remover
        Permissao permissaoRemover = new Permissao();
        permissaoRemover.setId(2L);

        permissaoRepository.remover(permissaoRemover);


    }
}
