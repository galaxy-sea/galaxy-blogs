package com.galaxy.result;

import com.galaxy.result.exception.IResultStatus;
import com.galaxy.result.exception.ResultException;
import com.galaxy.result.exception.ResultStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.util.WebUtils;

/**
 * 当类和方法使用了@ResponseResultBody,  放接口的返回进行统一处理
 * <p>
 * 对异常请求进行统一的处理
 *
 * @author galaxy
 * @date 2019/10/05 16:19
 */
@Slf4j
@RestControllerAdvice
// @Order(Ordered.HIGHEST_PRECEDENCE)
public class ResponseResultBodyAdvice implements ResponseBodyAdvice<Object> {

    private static final Class<ResponseResultBody> ANNOTATION_TYPE = ResponseResultBody.class;

    // @Autowired
    // private ObjectMapper objectMapper;

    /** 判断类或者方法是否使用了 @ResponseResultBody */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), ANNOTATION_TYPE) || returnType.hasMethodAnnotation(ANNOTATION_TYPE);
    }

    /** 当类或者方法使用了 @ResponseResultBody 就会调用这个方法 */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        ResponseResultBody mergedAnnotation = AnnotatedElementUtils.getMergedAnnotation(returnType.getContainingClass(), ANNOTATION_TYPE);
        return convert(body, mergedAnnotation);
    }

    private Result<?> convert(Object body, ResponseResultBody mergedAnnotation) {
        if (body instanceof Result) {
            return (Result<?>) body;
        }
        if ("OK".equals(mergedAnnotation.message())) {
            return Result.success(body);
        }
        return Result.success(new IResultStatus() {
            @Override
            public HttpStatus getHttpStatus() {
                return HttpStatus.OK;
            }

            @Override
            public Integer getCode() {
                return 200;
            }

            @Override
            public String getMessage() {
                return mergedAnnotation.message();
            }
        }, body);

    }

//    private Object convert(Result<?> result, Class<? extends HttpMessageConverter<?>> selectedConverterType) {
//        if (selectedConverterType == StringHttpMessageConverter.class && result.getData() instanceof String) {
//            return "{\"code\":\"" + result.getCode() + "\",\"message\":\"" + result.getMessage() + "\",\"data\":\"" + result.getData() + "\"}";
//        }
//        return result;
//    }


    /**
     * 提供对标准Spring MVC异常的处理
     *
     * @param ex the target exception
     * @param request the current request
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Result<?>> exceptionHandler(Exception ex, WebRequest request) {
        log.error("ExceptionHandler: {}", ex.getMessage());
        HttpHeaders headers = new HttpHeaders();
        if (ex instanceof ResultException) {
            return this.handleResultException((ResultException) ex, headers, request);
        }
        // TODO: 2019/10/05 galaxy 这里可以自定义其他的异常拦截
        return this.handleException(ex, headers, request);
    }

    /** 对ResultException类返回返回结果的处理 */
    protected ResponseEntity<Result<?>> handleResultException(ResultException ex, HttpHeaders headers, WebRequest request) {
        Result<?> body = Result.failure(ex.getResultStatus());
        HttpStatus status = ex.getResultStatus().getHttpStatus();
        return this.handleExceptionInternal(ex, body, headers, status, request);
    }

    /** 异常类的统一处理 */
    protected ResponseEntity<Result<?>> handleException(Exception ex, HttpHeaders headers, WebRequest request) {
        Result<?> body = Result.failure();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return this.handleExceptionInternal(ex, body, headers, status, request);
    }

    /**
     * org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler#handleExceptionInternal(java.lang.Exception, java.lang.Object, org.springframework.http.HttpHeaders, org.springframework.http.HttpStatus, org.springframework.web.context.request.WebRequest)
     * <p>
     * A single place to customize the response body of all exception types.
     * <p>The default implementation sets the {@link WebUtils#ERROR_EXCEPTION_ATTRIBUTE}
     * request attribute and creates a {@link ResponseEntity} from the given
     * body, headers, and status.
     */
    protected ResponseEntity<Result<?>> handleExceptionInternal(
            Exception ex, Result<?> body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        return new ResponseEntity<>(body, headers, status);
    }
}
