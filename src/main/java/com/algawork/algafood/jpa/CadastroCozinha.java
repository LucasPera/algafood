package com.algawork.algafood.jpa;

import com.algawork.algafood.domain.model.Cozinha;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class CadastroCozinha {

    //injeção especifica para o entityManager
    @PersistenceContext
    private EntityManager entityManager;

    public List<Cozinha> listar() {
        return entityManager.createQuery("from Cozinha", Cozinha.class)
                .getResultList();
    }

    @Transactional
    public Cozinha salvar(Cozinha cozinha) {
        return entityManager.merge(cozinha);
    }

    public Cozinha buscar(Long id) {
        return  entityManager.find(Cozinha.class, id);
    }

    @Transactional
    public void remover(Long id) {
        Cozinha cozinha = entityManager.find(Cozinha.class, id);
        entityManager.remove(cozinha);
    }
}
