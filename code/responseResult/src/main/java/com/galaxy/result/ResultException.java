package com.galaxy.result;

public class ResultException extends Exception {

    ResultEnum resultEnum;

    public ResultException() {
        super(ResultEnum.ERROR.message);
        this.resultEnum = ResultEnum.ERROR;
    }

    public ResultException(ResultEnum resultEnum) {
        super(resultEnum.message);
        this.resultEnum = resultEnum;
    }
}
