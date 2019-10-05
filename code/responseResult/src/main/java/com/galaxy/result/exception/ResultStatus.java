package com.galaxy.result.exception;

import org.springframework.http.HttpStatus;

/**
 * @author galaxy
 * @date 2019/10/05 16:19
 */

public enum ResultStatus {

    SUCCESS(HttpStatus.OK, 200, "OK"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 400, "Bad Request"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "Internal Server Error"),
    ;

    public HttpStatus httpStatus;
    public Integer code;
    public String message;

    ResultStatus(HttpStatus httpStatus, Integer code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
