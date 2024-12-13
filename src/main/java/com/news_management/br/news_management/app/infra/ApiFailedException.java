package com.news_management.br.news_management.app.infra;

import org.springframework.http.HttpStatus;

public class ApiFailedException extends RuntimeException {

    public ApiFailedException(String message) {
        super(message);
    }


}
