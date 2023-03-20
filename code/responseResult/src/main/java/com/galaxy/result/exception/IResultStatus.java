package com.galaxy.result.exception;

import org.springframework.http.HttpStatus;

/**
 * @author changjin wei(魏昌进)
 * @since 2023/3/20
 */
public interface IResultStatus {

    HttpStatus getHttpStatus();

    Integer getCode();

    String getMessage();
}
