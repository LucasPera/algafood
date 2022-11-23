package com.algawork.algafood.jpa;

import com.algawork.algafood.AlgafoodApplication;
import com.algawork.algafood.domain.model.Restaurante;
import com.algawork.algafood.domain.repository.RestauranteRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;

public class AtualizarRestauranteMain {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApplication.class)
                .web(WebApplicationType.NONE) //não é uma aplicação web
                .run(args);

        RestauranteRepository cadastroRestaurante = applicationContext.getBean(RestauranteRepository.class);

        Restaurante restaurante = new Restaurante();
        restaurante.setId(1L);
        restaurante.setNome("Pera Burguer");
        restaurante.setTaxaFrete(new BigDecimal("5.0"));

        cadastroRestaurante.salvar(restaurante);
    }

}
