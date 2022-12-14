package com.algawork.algafood.api.controller;

import com.algawork.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algawork.algafood.domain.model.Restaurante;
import com.algawork.algafood.domain.repository.CozinhaRepository;
import com.algawork.algafood.domain.repository.RestauranteRepository;
import com.algawork.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @GetMapping
    public List<Restaurante> listar() {

        return  restauranteRepository.findAll();

    }

    @GetMapping("/{idRestaurante}")
    public ResponseEntity<Restaurante> buscar(@PathVariable Long idRestaurante) {

        Optional<Restaurante> restaurante = restauranteRepository.findById(idRestaurante);

        if(restaurante.isPresent()) {
            return  ResponseEntity.ok(restaurante.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> adicionar(@RequestBody  Restaurante restaurante) {
        try {
            restaurante = cadastroRestauranteService.salvar(restaurante);

            return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{idRestaurante}")
    public ResponseEntity<?> atualizar(@PathVariable Long idRestaurante, @RequestBody Restaurante restaurante) {

        try {
            Optional<Restaurante> restauranteAtual = restauranteRepository.findById(idRestaurante);

            if (restauranteAtual.isPresent()) {
                BeanUtils.copyProperties(restaurante, restauranteAtual.get(), "id");

                Restaurante restauranteSalvo = cadastroRestauranteService.salvar(restauranteAtual.get());
                return ResponseEntity.ok(restauranteSalvo);
            }

            return ResponseEntity.notFound().build();

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }

    }

    @PatchMapping("/{idRestaurante}")
    public ResponseEntity<?> atualizarParcial(@PathVariable Long idRestaurante,
                                              @RequestBody Map<String, Object> campos) {
       Optional<Restaurante> restauranteAtual = restauranteRepository.findById(idRestaurante);

       if(restauranteAtual.isEmpty()) {
           return ResponseEntity.notFound().build();
       }

        merge(campos, restauranteAtual.get());

        return atualizar(idRestaurante, restauranteAtual.get());
    }

    private static void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
        //Cria um Restaurante a partir do map
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

        dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
            //pega a instancia do campo
            Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);

            //permite acesso aos atributos privados da classe restaurante
            field.setAccessible(true);

            //pega o objeto convertido
            Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

            //atribui o novoValor para cada instancia
            ReflectionUtils.setField(field, restauranteDestino, novoValor);
        });
    }

}
