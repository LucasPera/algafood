package com.algawork.algafood.api.controller;

import com.algawork.algafood.domain.exception.EstadoNaoEncontradaException;
import com.algawork.algafood.domain.exception.NegocioException;
import com.algawork.algafood.domain.model.Cidade;
import com.algawork.algafood.domain.repository.CidadeRepository;
import com.algawork.algafood.domain.service.CadastroCidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping("/{cidadeId}")
    public Cidade buscar(@PathVariable Long cidadeId) {
        return cadastroCidadeService.buscarOuFalhar(cidadeId);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cidade adicionar(@RequestBody @Valid Cidade cidade) {
        try {
            return cadastroCidadeService.salvar(cidade);
        }catch (EstadoNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }

    }

    @PutMapping("/{cidadeId}")
    public Cidade atualizar(@PathVariable Long cidadeId,
                            @RequestBody @Valid Cidade cidade) {
        try {
            Cidade cidadeAtual = cadastroCidadeService.buscarOuFalhar(cidadeId);

            BeanUtils.copyProperties(cidade, cidadeAtual, "id");

            return cadastroCidadeService.salvar(cidadeAtual);
        }catch (EstadoNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }

    }


    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
        cadastroCidadeService.excluir(cidadeId);
    }



}
