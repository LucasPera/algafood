package com.algawork.algafood.domain.repository;

import com.algawork.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>,
        JpaSpecificationExecutor<Restaurante> {

    @Query("from Restaurante r join r.cozinha left join fetch r.formasPagamento")
    List<Restaurante> findAll();

    List<Restaurante> getByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

    List<Restaurante> consultarPorNome(String nome, @Param("id") Long cozinha);

    Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);

    List<Restaurante> findTop2RestauranteByNomeContaining(String nome);

    int countByCozinhaId(Long cozinha);

    List<Restaurante> findCriteria(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);


    List<Restaurante> findComFreteGratis(String nome);
}
