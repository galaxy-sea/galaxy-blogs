package com.galaxy.result;

public class Result<T> {
    
    private Integer code;
    private String message;
    private T date;

    private Result(ResultEnum success) {
        this(success, null);
    }

    private Result(ResultEnum resultEnum, T date) {
        this.code = resultEnum.code;
        this.message = resultEnum.message;
        this.date = date;
    }


    public static <T> Result<T> success() {
        return new Result<T>(ResultEnum.SUCCESS);
    }

    public static <T> Result<T> success(T date) {
        return new Result<T>(ResultEnum.SUCCESS, date);
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




