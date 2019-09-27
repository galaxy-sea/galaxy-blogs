package com.galaxy.result;

public class ResultException extends Exception {

    private ResultEnum resultEnum;

    public ResultException() {
        super(ResultEnum.ERROR.message);
        this.resultEnum = ResultEnum.ERROR;
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
}
