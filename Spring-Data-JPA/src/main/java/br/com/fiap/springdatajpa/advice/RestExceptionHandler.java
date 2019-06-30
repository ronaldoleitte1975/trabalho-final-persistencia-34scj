package br.com.fiap.springdatajpa.advice;

import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    //Manipulador genérico de exceções
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> handleGenericExceptions(Exception ex) {
        ResponseDefault responseDefault = new ResponseDefault("Ocorreu um erro durante o processamento da requisição.");

        return ResponseEntity.status(500).body(new Gson().toJson(responseDefault));
    }

}