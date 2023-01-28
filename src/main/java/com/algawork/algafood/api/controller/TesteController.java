package com.algawork.algafood.api.controller;

import com.algawork.algafood.domain.model.Cozinha;
import com.algawork.algafood.domain.model.Restaurante;
import com.algawork.algafood.domain.repository.CozinhaRepository;
import com.algawork.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teste")
public class TesteController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @GetMapping("/cozinhas/por-nome")
    public List<Cozinha> cozinhasPorNome(String nome) {
        return cozinhaRepository.findByNomeContaining(nome);
    }
    @GetMapping("/cozinhas/existe-por-nome")
    public boolean existePorNome(String nome) {
        return cozinhaRepository.existsByNome(nome);
    }
    @GetMapping("/restaurantes/primeiro-por-nome")
    public Optional<Restaurante> restaurantePorNome(String nome) {
        return restauranteRepository.findFirstRestauranteByNomeContaining(nome);
    }

    @GetMapping("/restaurantes/top2-por-nome")
    public List<Restaurante> restauranteTop2PorNome(String nome) {
        return restauranteRepository.findTop2RestauranteByNomeContaining(nome);
    }
    @GetMapping("/restaurantes/por-taxa-frete")
    public List<Restaurante> restaurantesPorTaxaFrete(String nome,BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return restauranteRepository.find(nome, taxaInicial, taxaFinal);
    }

}
