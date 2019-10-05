package com.galaxy.result;

import com.galaxy.result.exception.ResultStatus;
import lombok.Getter;
import lombok.ToString;


/**
 * 后端返回给前端的json格式
 *
 * @author galaxy
 * @date 2019/10/05 16:19
 */
@Getter
@ToString
public class Result<T> {
    /**
     * 业务错误码
     */
    private Integer code;
    /**
     * 信息描述
     */
    private String message;
    /**
     * 返回参数
     */
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

    /** 业务成功返回业务代码和描述信息 */
    public static Result<Void> success() {
        return new Result<Void>(ResultStatus.SUCCESS);
    }

    /** 业务成功返回业务代码,描述和返回的参数 */
    public static <T> Result<T> success(T date) {
        return new Result<T>(ResultStatus.SUCCESS, date);
    }

    /** 业务异常返回业务代码和描述信息 */
    public static <T> Result<T> failure() {
        return new Result<T>(ResultStatus.INTERNAL_SERVER_ERROR);
    }

    /** 业务异常返回业务代码,描述和返回的参数 */
    public static <T> Result<T> failure(ResultStatus resultStatus) {
        if (resultStatus == null) {
            return new Result<T>(ResultStatus.INTERNAL_SERVER_ERROR);
        }
        return new Result<T>(resultStatus);
    }
}




