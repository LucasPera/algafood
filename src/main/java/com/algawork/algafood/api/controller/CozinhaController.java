package com.algawork.algafood.api.controller;

import com.algawork.algafood.api.model.CozinhasXmlWrapper;
import com.algawork.algafood.domain.exception.EntidadeEmUsoException;
import com.algawork.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algawork.algafood.domain.model.Cozinha;
import com.algawork.algafood.domain.repository.CozinhaRepository;
import com.algawork.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return cozinhaRepository.listar();
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public CozinhasXmlWrapper listarXml() {
        return new CozinhasXmlWrapper(cozinhaRepository.listar());
    }

    @GetMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId) {

        Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);

        if(cozinha != null) {
            return ResponseEntity.ok(cozinha);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody Cozinha cozinha) {
        return cadastroCozinhaService.salvar(cozinha);
    }

    @PutMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> atualizar( @PathVariable Long cozinhaId, @RequestBody Cozinha cozinha) {

        Cozinha cozinhaAtual = cozinhaRepository.buscar(cozinhaId);

        if(cozinhaAtual != null) {
            //copia os valores da cozinha autal para a cozinha passada por body e ignora o atributo id
            BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");

            cozinhaRepository.salvar(cozinha);

            return ResponseEntity.ok(cozinhaAtual);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{cozinhaId}")
    public ResponseEntity remover(@PathVariable Long cozinhaId) {

        try {
            cadastroCozinhaService.excluir(cozinhaId);

            return ResponseEntity.noContent().build();

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }


    }
}
