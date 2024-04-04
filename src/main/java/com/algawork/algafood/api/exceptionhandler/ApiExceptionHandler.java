package com.algawork.algafood.api.exceptionhandler;

import com.algawork.algafood.domain.exception.EntidadeEmUsoException;
import com.algawork.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algawork.algafood.domain.exception.NegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice //coloca um ponto central dos exception handlers
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> tratarNegocioException(
            NegocioException e) {

        Problema problema = Problema.builder()
                .dataHora(LocalDateTime.now())
                .mensagem(e.getMessage()).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(problema);
    }
    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> entidadeNaoEncontradaException(
            EntidadeNaoEncontradaException e) {

        Problema problema = Problema.builder()
                .dataHora(LocalDateTime.now())
                .mensagem(e.getMessage()).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(problema);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> entidadeEmUsoException(EntidadeEmUsoException e) {

        Problema problema = Problema.builder()
                .dataHora(LocalDateTime.now())
                .mensagem(e.getMessage()).build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(problema);
    }

}
