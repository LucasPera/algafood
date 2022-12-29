package com.algawork.algafood.api.controller;

import com.algawork.algafood.domain.exception.EntidadeEmUsoException;
import com.algawork.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algawork.algafood.domain.model.Cidade;
import com.algawork.algafood.domain.repository.CidadeRepository;
import com.algawork.algafood.domain.service.CadastroCidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @GetMapping
    public List<Cidade> listar() {
        return cidadeRepository.findAll();
    }

    @GetMapping("/{idCidade}")
    public ResponseEntity<Cidade> buscar(@PathVariable Long idCidade){
        Optional<Cidade> cidade = cidadeRepository.findById(idCidade);

        if(cidade.isPresent()) {
            return ResponseEntity.ok(cidade.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Cidade cidade) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(cadastroCidadeService.salvar(cidade));
        }catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{idCidade}")
    public ResponseEntity<?> atualizar(@PathVariable Long idCidade, @RequestBody Cidade cidade) {
        try {
            Optional<Cidade> cidadeAtual = cidadeRepository.findById(idCidade);

            if(cidadeAtual.isPresent()) {
                BeanUtils.copyProperties(cidade, cidadeAtual.get(), "id");

                Cidade cidadeSalva = cadastroCidadeService.salvar(cidadeAtual.get());

                return ResponseEntity.ok(cidadeSalva);

            }

            return ResponseEntity.notFound().build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{cidadeId}")
    public ResponseEntity<?> remover(@PathVariable Long cidadeId) {

        try {
            cadastroCidadeService.excluir(cidadeId);

            return ResponseEntity.noContent().build();
        }catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }


    }

}
