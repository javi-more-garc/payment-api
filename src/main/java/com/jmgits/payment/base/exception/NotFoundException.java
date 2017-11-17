package com.jmgits.payment.base.exception;

import lombok.Getter;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Created by javi.more.garc on 12-11-17.
 */
@ResponseStatus(NOT_FOUND)
@Getter
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
