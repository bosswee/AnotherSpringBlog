package com.zhouchaowei.error;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/**
 * @author wee
 * @date 1/3/16.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{

    private String message;

    public NotFoundException() {

    }

    public NotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
