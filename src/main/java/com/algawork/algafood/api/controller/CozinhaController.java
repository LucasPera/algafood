package com.algawork.algafood.api.controller;

import com.algawork.algafood.domain.model.Cozinha;
import com.algawork.algafood.domain.repository.CozinhaRepository;
import com.algawork.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @GetMapping
    public List<Cozinha> listar() {
        return cozinhaRepository.findAll();
    }

    @GetMapping("/{cozinhaId}")
    public Cozinha buscar(@PathVariable Long cozinhaId) {
        return cadastroCozinhaService.buscarOuFalhar(cozinhaId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody @Valid Cozinha cozinha) {
        return cadastroCozinhaService.salvar(cozinha);
    }

    @PutMapping("/{cozinhaId}")
    public Cozinha atualizar( @PathVariable Long cozinhaId, @Valid @RequestBody Cozinha cozinha) {

        Cozinha cozinhaAtual = cadastroCozinhaService.buscarOuFalhar(cozinhaId);

        BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");

        return cadastroCozinhaService.salvar(cozinhaAtual);

    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cozinhaId) {
        cadastroCozinhaService.excluir(cozinhaId);
    }
}
