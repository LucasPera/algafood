package com.algawork.algafood.infrastructure.repository;

import com.algawork.algafood.domain.model.Restaurante;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class RestauranteRepositoryImpl {

    @PersistenceContext
    private EntityManager manager;

    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {

        var jpql = new StringBuilder();

        var parametros = new HashMap<String, Object>();

        jpql.append("from Restaurante where 1=1 ");

        if(StringUtils.hasLength(nome)) {
            jpql.append("and nome like :nome ");
            parametros.put("nome", "%" + nome + "%");
        }

        if(taxaFreteInicial != null) {
            jpql.append("and taxaFrete >= :taxaInicial ");
            parametros.put("taxaInicial", taxaFreteInicial);
        }

        if(taxaFreteFinal != null) {
            jpql.append("and taxaFrete <= :taxaFinal ");
            parametros.put("taxaFinal", taxaFreteFinal);
        }

        TypedQuery<Restaurante> query = manager.createQuery(jpql.toString(), Restaurante.class);

        parametros.forEach((chave, valor) -> query.setParameter(chave, valor));

        return query.getResultList();


    }

    public List<Restaurante> findCriteria(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);

        //from Restaurante //raiz
        Root<Restaurante> root = criteria.from(Restaurante.class);

        var predicates = new ArrayList<Predicate>();

        //Filtros
        if(StringUtils.hasText(nome)) {
            predicates.add(builder.like(root.get("nome"), '%' + nome + '%'));
        }
        if(taxaFreteInicial != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
        }
        if(taxaFreteFinal != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
        }

        //where e and
        criteria.where(predicates.toArray(new Predicate[0]));

        return manager.createQuery(criteria).getResultList();

    }


}
