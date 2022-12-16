package com.algawork.algafood.infraestructure.repository;

import com.algawork.algafood.domain.model.Cozinha;
import com.algawork.algafood.domain.repository.CozinhaRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class CozinhaRepositoryImpl implements CozinhaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Cozinha> listar() {
        return entityManager.createQuery("from Cozinha", Cozinha.class)
                .getResultList();
    }

    @Override
    @Transactional
    public Cozinha salvar(Cozinha cozinha) {
        return entityManager.merge(cozinha);
    }

    @Override
    public Cozinha buscar(Long id) {
        return  entityManager.find(Cozinha.class, id);
    }

    @Override
    @Transactional
    public void remover(Long id) {
        Cozinha cozinha = buscar(id);

        if(cozinha == null) {
            //esperava uma cozinha
            throw new EmptyResultDataAccessException(1);
        }

        entityManager.remove(cozinha);
    }
}
