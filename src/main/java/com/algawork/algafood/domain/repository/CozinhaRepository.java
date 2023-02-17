package com.algawork.algafood.domain.repository;

import com.algawork.algafood.domain.model.Cozinha;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long> {

    List<Cozinha> findByNomeContaining(String nome);

    boolean existsByNome(String nome);

}


