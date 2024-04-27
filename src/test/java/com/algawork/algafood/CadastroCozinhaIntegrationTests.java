package com.algawork.algafood;

import com.algawork.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algawork.algafood.domain.exception.EntidadeEmUsoException;
import com.algawork.algafood.domain.model.Cozinha;
import com.algawork.algafood.domain.service.CadastroCozinhaService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class CadastroCozinhaIntegrationTests {

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Test
	public void testarCadastroCozinhaComSucesso() {
		//Cenario
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");

		//Ação
		novaCozinha = cadastroCozinha.salvar(novaCozinha);

		//validação
		Assertions.assertThat(novaCozinha).isNotNull();
		Assertions.assertThat(novaCozinha.getId()).isNotNull();

	}

	@Test
	public void testarCadastroCozinhaSemNome() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);

		ConstraintViolationException erroEsperado =
				assertThrows(ConstraintViolationException.class, () -> {
					cadastroCozinha.salvar(novaCozinha);
				});

		Assertions.assertThat(erroEsperado).isNotNull();
	}

	@Test
	public void deveFalharQuandoExcluirCozinhaEmUso() {
		EntidadeEmUsoException erroEsperado =
				assertThrows(EntidadeEmUsoException.class, () -> {
					cadastroCozinha.excluir(1L);
				});

		Assertions.assertThat(erroEsperado).isNotNull();
	}
	@Test
	public void deveFalharQuandoExcluirCozinhaInexistente() {

		CozinhaNaoEncontradaException erroEsperado =
				assertThrows(CozinhaNaoEncontradaException.class, () -> {
					cadastroCozinha.excluir(300L);
				});

		Assertions.assertThat(erroEsperado).isNotNull();
	}
}
