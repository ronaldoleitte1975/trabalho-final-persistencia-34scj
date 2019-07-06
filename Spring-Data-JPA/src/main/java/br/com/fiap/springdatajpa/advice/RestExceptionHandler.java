package br.com.fiap.springdatajpa.advice;

import com.google.gson.JsonObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Controller de advice, utilizado para gerenciar as exceções da aplicação e retornar mensagens tratadas.
 */
@ControllerAdvice
public class RestExceptionHandler {

    /**
     * Trata exceções genéricas
     * @param ex exceção genérica
     * @return mensagem padronizada de erro genérico
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> handleGenericExceptions(Exception ex) {
        ResponseError responseError = new ResponseError(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Ocorreu um erro durante o processamento da requisição.");

        return ResponseEntity
                .status(responseError.getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(getBody(responseError));
    }

    /**
     * Trata as exceções padronizadas lançadas pela aplicação
     * @param ex exceção
     * @return mensagem padronizada de erro
     */
    @ExceptionHandler(ResponseError.class)
    public final ResponseEntity<?> handleResponseErrorException(ResponseError ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(getBody(ex));
    }

    /**
     * Trata as exceções lançadas devido a dados inválidos na requisição
     * @param ex exceção
     * @return mensagem padronizada de erro
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ResponseError responseError = new ResponseError(
                HttpStatus.BAD_REQUEST,
                "Dados inválidos na requisição. Por favor verifique os dados informados e tente novamente.");

        return ResponseEntity
                .status(responseError.getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(getBody(responseError));
    }

    /**
     * Converte mensagem da exceção em um json tratado
     * @param ex exceção
     * @return json com mensagem de erro ou validação
     */
    private String getBody(Exception ex) {
        JsonObject json = new JsonObject();
        json.addProperty("message", ex.getMessage());
        return json.toString();
    }
}