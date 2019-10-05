package com.galaxy.result.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 业务异常类
 *
 * @author galaxy
 * @date 2019/10/05 16:19
 */

@Getter
public class ResultException extends Exception {

    /**
     * 业务信息
     */
    ResultStatus resultStatus;

    /**
     * http协议, http的状态信息
     */
    HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    public ResultException() {
        this(ResultStatus.INTERNAL_SERVER_ERROR);
    }

    public ResultException(ResultStatus resultStatus) {
        super(resultStatus.message);
        this.resultStatus = resultStatus;
    }
}
