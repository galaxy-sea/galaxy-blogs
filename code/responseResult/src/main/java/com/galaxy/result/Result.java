package com.galaxy.result;

/**
 * @author galaxy
 * @date 2019/10/05 16:19
 */

public class Result<T> {

    private Integer code;
    private String message;
    private T date;

    private Result(ResultEnum resultEnum) {
        this(resultEnum, null);
    }

    private Result(ResultEnum resultEnum, T date) {
        this(resultEnum.code, resultEnum.message, date);
    }

    private Result(Integer code, String message, T date) {
        this.code = code;
        this.message = message;
        this.date = date;
    }


    public static <T> Result<T> success() {
        return new Result<T>(ResultEnum.SUCCESS);
    }

    public static <T> Result<T> success(T date) {
        return new Result<T>(ResultEnum.SUCCESS, date);
    }

    public static <T> Result<T> failure() {
        return new Result<T>(ResultEnum.INTERNAL_SERVER_ERROR);
    }

    public static <T> Result<T> failure(ResultEnum resultEnum) {
        if (resultEnum == null) {
            return new Result<T>(ResultEnum.INTERNAL_SERVER_ERROR);
        }
        return new Result<T>(resultEnum);
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




