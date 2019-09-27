package com.galaxy.result;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ResultExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(handlerException(ex), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public Result<?> handlerException(Throwable e) {
        if (e instanceof ResultException) {
            ResultException exception = (ResultException) e;
            return Result.failure(exception.getResultEnum());
        }
        return Result.failure();
    }
}
