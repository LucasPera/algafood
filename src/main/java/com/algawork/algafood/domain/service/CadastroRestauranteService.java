package com.algawork.algafood.domain.service;

import com.algawork.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algawork.algafood.domain.model.Cozinha;
import com.algawork.algafood.domain.model.Restaurante;
import com.algawork.algafood.domain.repository.CozinhaRepository;
import com.algawork.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    //colocar para salvar e dar rolback caso tenha mais de uma operação nesse metodo
    @Transactional
    public Restaurante salvar(Restaurante restaurante) {

        Long cozinhaId = restaurante.getCozinha().getId();

        //caso não exista a cozinha, lança a exceção
        Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(cozinhaId));

        restaurante.setCozinha(cozinha);

        return restauranteRepository.save(restaurante);
    }

    public Restaurante buscarOuFalhar(Long restauranteId) {
        return restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
    }
}
