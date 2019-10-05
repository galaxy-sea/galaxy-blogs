package com.galaxy.result.exception;

import org.springframework.http.HttpStatus;

/**
 * @author galaxy
 * @date 2019/10/05 16:19
 */

public class ResultException extends Exception {

    ResultStatus resultStatus;

    HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    public ResultException() {
        this(ResultStatus.INTERNAL_SERVER_ERROR);
    }

    public ResultException(ResultStatus resultStatus) {
        super(resultStatus.message);
        this.resultStatus = resultStatus;
    }

    public ResultStatus getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(ResultStatus resultStatus) {
        this.resultStatus = resultStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
