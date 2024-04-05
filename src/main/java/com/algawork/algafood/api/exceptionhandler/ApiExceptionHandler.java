package com.algawork.algafood.api.exceptionhandler;

import com.algawork.algafood.domain.exception.EntidadeEmUsoException;
import com.algawork.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algawork.algafood.domain.exception.NegocioException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice //coloca um ponto central dos exception handlers
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocioException(
            NegocioException e, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        ProblemType problemType = ProblemType.ERRO_NEGOCIO;
        Problem problem = createProblemBuilder(status, problemType, e.getMessage())
                .build();

        return handleExceptionInternal(e, problem, new HttpHeaders(),
                status, request);
    }
    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException e, WebRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;

        ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
        Problem problem = createProblemBuilder(status, problemType, e.getMessage())
                .build();

        return handleExceptionInternal(e, problem, new HttpHeaders(),
                status, request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaException(
            EntidadeNaoEncontradaException e, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        ProblemType problemType = ProblemType.ENTIDADE_NAO_ENCONTRADA;
        Problem problem = createProblemBuilder(status, problemType, e.getMessage())
                .build();

        return handleExceptionInternal(e, problem, new HttpHeaders(),
                status, request);

    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if(body == null) {
            body = Problem.builder()
                .title(status.getReasonPhrase())
                .status(status.value())
                .build();
        } else if (body instanceof String) {
            body = Problem.builder()
                .title((String) body)
                .status(status.value())
                .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status,
            ProblemType problemType, String detail) {

        return Problem.builder()
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail);
    }

}
