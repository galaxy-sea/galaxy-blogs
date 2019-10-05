package com.galaxy.result.exception;

import com.galaxy.result.ResultEnum;
import org.springframework.http.HttpStatus;

/**
 * @author galaxy
 * @date 2019/10/05 16:19
 */

public class ResultException extends Exception {

    ResultEnum resultEnum;

    HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    public ResultException() {
        this(ResultEnum.INTERNAL_SERVER_ERROR);
    }

    public ResultException(ResultEnum resultEnum) {
        super(resultEnum.message);
        this.resultEnum = resultEnum;
    }

    public ResultEnum getResultEnum() {
        return resultEnum;
    }

    public void setResultEnum(ResultEnum resultEnum) {
        this.resultEnum = resultEnum;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
