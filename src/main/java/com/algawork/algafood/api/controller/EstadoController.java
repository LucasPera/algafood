package com.algawork.algafood.api.controller;

import com.algawork.algafood.domain.exception.EntidadeEmUsoException;
import com.algawork.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algawork.algafood.domain.model.Estado;
import com.algawork.algafood.domain.repository.EstadoRepository;
import com.algawork.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    @GetMapping
    public List<Estado> listar() {
        return estadoRepository.listar();
    }

    @GetMapping("/{idEstado}")
    public ResponseEntity<Estado> buscar(@PathVariable Long idEstado) {

        Estado estado = estadoRepository.buscar(idEstado);

        if(estado != null) {
            return ResponseEntity.ok(estado);
        }

        return ResponseEntity.notFound().build();

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado adicionar(@RequestBody Estado estado) {
        return cadastroEstadoService.salvar(estado);
    }

    @PutMapping("/{idEstado}")
    public ResponseEntity<Estado> atualizar(@PathVariable Long idEstado, @RequestBody Estado estado) {
        Estado estadoAtual = estadoRepository.buscar(idEstado);

        if(estadoAtual != null) {
            BeanUtils.copyProperties(estado, estadoAtual, "id");

            estadoAtual = cadastroEstadoService.salvar(estadoAtual);

            return ResponseEntity.ok(estadoAtual);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{idEstado}")
    public ResponseEntity remover(@PathVariable Long idEstado) {
        try {
            cadastroEstadoService.excluir(idEstado);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

}
