package com.galaxy.result;

import com.galaxy.result.exception.ResultStatus;

/**
 * @author galaxy
 * @date 2019/10/05 16:19
 */

public class Result<T> {

    private Integer code;
    private String message;
    private T date;

    private Result(ResultStatus resultStatus) {
        this(resultStatus, null);
    }

    private Result(ResultStatus resultStatus, T date) {
        this(resultStatus.code, resultStatus.message, date);
    }

    private Result(Integer code, String message, T date) {
        this.code = code;
        this.message = message;
        this.date = date;
    }


    public static <T> Result<T> success() {
        return new Result<T>(ResultStatus.SUCCESS);
    }

    public static <T> Result<T> success(T date) {
        return new Result<T>(ResultStatus.SUCCESS, date);
    }

    public static <T> Result<T> failure() {
        return new Result<T>(ResultStatus.INTERNAL_SERVER_ERROR);
    }

    public static <T> Result<T> failure(ResultStatus resultStatus) {
        if (resultStatus == null) {
            return new Result<T>(ResultStatus.INTERNAL_SERVER_ERROR);
        }
        return new Result<T>(resultStatus);
    }


    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getDate() {
        return date;
    }
}




