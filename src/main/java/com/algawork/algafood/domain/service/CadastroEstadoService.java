package com.algawork.algafood.domain.service;

import com.algawork.algafood.domain.exception.EntidadeEmUsoException;
import com.algawork.algafood.domain.exception.EstadoNaoEncontradaException;
import com.algawork.algafood.domain.model.Estado;
import com.algawork.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroEstadoService {

    private static final String MSG_ESTADO_EM_USO  =
            "Estado de código %d não pode ser removido, pois está em uso";


    @Autowired
    private EstadoRepository estadoRepository;

    @Transactional
    public Estado salvar(Estado estado) {
       return estadoRepository.save(estado);
    }

    @Transactional
    public void excluir(Long idEstado) {
        try {
            estadoRepository.deleteById(idEstado);
        }catch (EmptyResultDataAccessException e) {
            throw new EstadoNaoEncontradaException(idEstado);
        }catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_ESTADO_EM_USO, idEstado));
        }
    }

    public Estado buscarOuFalhar(Long estadoId) {
        return estadoRepository.findById(estadoId)
                .orElseThrow(() -> new EstadoNaoEncontradaException(estadoId));
    }
}
