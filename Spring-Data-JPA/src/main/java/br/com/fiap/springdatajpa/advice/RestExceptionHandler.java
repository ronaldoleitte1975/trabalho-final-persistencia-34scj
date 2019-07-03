package br.com.fiap.springdatajpa.advice;

import com.google.gson.JsonObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> handleGenericExceptions(Exception ex) {
        ResponseError responseError = new ResponseError("Ocorreu um erro durante o processamento da requisição.");

        return ResponseEntity
                .status(responseError.getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(getBody(ex));
    }

    @ExceptionHandler(ResponseError.class)
    public final ResponseEntity<?> handleResponseErrorException(ResponseError ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(getBody(ex));
    }

    private String getBody(Exception ex) {
        JsonObject json = new JsonObject();
        json.addProperty("message", ex.getMessage());
        return json.toString();
    }
}