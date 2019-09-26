package com.galaxy.result;

public enum ResultEnum {

    SUCCESS(200, "成功"),
    INVALID(400, "参数无效"),
    ERROR(500, "错误"),
    ;

    Integer code;
    String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
