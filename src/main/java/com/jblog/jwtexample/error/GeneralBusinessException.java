package com.jblog.jwtexample.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class GeneralBusinessException extends RuntimeException {

    public GeneralBusinessException(String message) {
        super(message);
    }
}
