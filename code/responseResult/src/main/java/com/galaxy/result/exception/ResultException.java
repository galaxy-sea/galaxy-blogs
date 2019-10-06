package com.galaxy.result.exception;

import lombok.Getter;

/**
 * 业务异常类
 *
 * @author galaxy
 * @date 2019/10/05 16:19
 */

@Getter
public class ResultException extends Exception {

    /**
     * 业务异常信息信息
     */
    ResultStatus resultStatus;

    public ResultException() {
        this(ResultStatus.INTERNAL_SERVER_ERROR);
    }

    public ResultException(ResultStatus resultStatus) {
        super(resultStatus.getMessage());
        this.resultStatus = resultStatus;
    }
}
