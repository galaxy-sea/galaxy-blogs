
<!-- TOC -->

- [无侵入式 统一返回JSON格式](#%e6%97%a0%e4%be%b5%e5%85%a5%e5%bc%8f-%e7%bb%9f%e4%b8%80%e8%bf%94%e5%9b%9ejson%e6%a0%bc%e5%bc%8f)
  - [定义JSON格式](#%e5%ae%9a%e4%b9%89json%e6%a0%bc%e5%bc%8f)
    - [定义返回JSON格式](#%e5%ae%9a%e4%b9%89%e8%bf%94%e5%9b%9ejson%e6%a0%bc%e5%bc%8f)
    - [定义JavaBean字段](#%e5%ae%9a%e4%b9%89javabean%e5%ad%97%e6%ae%b5)
      - [定义状态码枚举类](#%e5%ae%9a%e4%b9%89%e7%8a%b6%e6%80%81%e7%a0%81%e6%9e%9a%e4%b8%be%e7%b1%bb)
      - [定义返回体类](#%e5%ae%9a%e4%b9%89%e8%bf%94%e5%9b%9e%e4%bd%93%e7%b1%bb)
  - [Result实体返回测试](#result%e5%ae%9e%e4%bd%93%e8%bf%94%e5%9b%9e%e6%b5%8b%e8%af%95)
- [统一返回JSON格式进阶-全局处理(@RestControllerAdvice)](#%e7%bb%9f%e4%b8%80%e8%bf%94%e5%9b%9ejson%e6%a0%bc%e5%bc%8f%e8%bf%9b%e9%98%b6-%e5%85%a8%e5%b1%80%e5%a4%84%e7%90%86restcontrolleradvice)
  - [@ResponseBody继承类](#responsebody%e7%bb%a7%e6%89%bf%e7%b1%bb)
  - [ResponseBodyAdvice继承类](#responsebodyadvice%e7%bb%a7%e6%89%bf%e7%b1%bb)
  - [RestControllerAdvice返回测试](#restcontrolleradvice%e8%bf%94%e5%9b%9e%e6%b5%8b%e8%af%95)
- [统一返回JSON格式进阶-异常处理(@ExceptionHandler))](#%e7%bb%9f%e4%b8%80%e8%bf%94%e5%9b%9ejson%e6%a0%bc%e5%bc%8f%e8%bf%9b%e9%98%b6-%e5%bc%82%e5%b8%b8%e5%a4%84%e7%90%86exceptionhandler)
  - [异常处理@ResponseStatus(不推荐)](#%e5%bc%82%e5%b8%b8%e5%a4%84%e7%90%86responsestatus%e4%b8%8d%e6%8e%a8%e8%8d%90)
  - [全局异常处理@ExceptionHandler(推荐)](#%e5%85%a8%e5%b1%80%e5%bc%82%e5%b8%b8%e5%a4%84%e7%90%86exceptionhandler%e6%8e%a8%e8%8d%90)

<!-- /TOC -->
# 无侵入式 统一返回JSON格式
其实本没有没打算写这篇博客的，但还是要写一下写这篇博客的起因是因为，现在呆着的这家公司居然没有统一的API返回格式😓，询问主管他居然告诉我用HTTP状态码就够用了（fxxk），天哪HTTP状态码真的够用吗？在仔细的阅读了项目源码后发现，在API请求的是居然没有业务异常（黑人问好）。好吧 居然入坑了只能遵照项目风格了，懒得吐槽了。

因为项目已经开发了半年多了, 要是全部接口都做修改工作量还是挺大的, 只能用这种无侵入式的方案来解决.

> 项目源代码: https://github.com/469753862/galaxy-blogs/tree/master/code/responseResult

## 定义JSON格式
### 定义返回JSON格式
后端返回给前端一般情况下使用JSON格式, 定义如下
```JSON
{
    "code": 200,
    "message": "OK",
    "data": {

    }
}
```
code: 返回状态码
message: 返回信息的描述
data: 返回值

### 定义JavaBean字段

#### 定义状态码枚举类
```Java
@ToString
@Getter
public enum ResultStatus {

    SUCCESS(HttpStatus.OK, 200, "OK"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 400, "Bad Request"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "Internal Server Error"),;

    /** 返回的HTTP状态码,  符合http请求 */
    private HttpStatus httpStatus;
    /** 业务异常码 */
    private Integer code;
    /** 业务异常信息描述 */
    private String message;

    ResultStatus(HttpStatus httpStatus, Integer code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
```
状态码和信息以及http状态码就能一一对应了便于维护, 有同学有疑问了为什么要用到http状态码呀,因为我要兼容项目以前的代码, 没有其他原因, 当然其他同学不喜欢http状态码的可以吧源码中HttpStatus给删除了
#### 定义返回体类
```Java
@Getter
@ToString
public class Result<T> {
    /** 业务错误码 */
    private Integer code;
    /** 信息描述 */
    private String message;
    /** 返回参数 */
    private T data;

    private Result(ResultStatus resultStatus, T data) {
        this.code = resultStatus.getCode();
        this.message = resultStatus.getMessage();
        this.data = data;
    }

    /** 业务成功返回业务代码和描述信息 */
    public static Result<Void> success() {
        return new Result<Void>(ResultStatus.SUCCESS, null);
    }

    /** 业务成功返回业务代码,描述和返回的参数 */
    public static <T> Result<T> success(T data) {
        return new Result<T>(ResultStatus.SUCCESS, data);
    }

    /** 业务成功返回业务代码,描述和返回的参数 */
    public static <T> Result<T> success(ResultStatus resultStatus, T data) {
        if (resultStatus == null) {
            return success(data);
        }
        return new Result<T>(resultStatus, data);
    }

    /** 业务异常返回业务代码和描述信息 */
    public static <T> Result<T> failure() {
        return new Result<T>(ResultStatus.INTERNAL_SERVER_ERROR, null);
    }

    /** 业务异常返回业务代码,描述和返回的参数 */
    public static <T> Result<T> failure(ResultStatus resultStatus) {
        return failure(resultStatus, null);
    }

    /** 业务异常返回业务代码,描述和返回的参数 */
    public static <T> Result<T> failure(ResultStatus resultStatus, T data) {
        if (resultStatus == null) {
            return new Result<T>(ResultStatus.INTERNAL_SERVER_ERROR, null);
        }
        return new Result<T>(resultStatus, data);
    }
}
```
因为使用构造方法进行创建对象太麻烦了, 我们使用静态方法来创建对象这样简单明了


## Result实体返回测试
```Java
@RestController
@RequestMapping("/hello")
public class HelloController {

    private static final HashMap<String, Object> INFO;

    static {
        INFO = new HashMap<>();
        INFO.put("name", "galaxy");
        INFO.put("age", "70");
    }

    @GetMapping("/hello")
    public Map<String, Object> hello() {
        return INFO;
    }

    @GetMapping("/result")
    @ResponseBody
    public Result<Map<String, Object>> helloResult() {
        return Result.success(INFO);
    }
}
```
到这里我们已经简单的实现了统一JSON格式了, 但是我们也发现了一个问题了,想要返回统一的JSON格式需要返回``Result<Object>``才可以, 我明明返回``Object``可以了, 为什么要重复劳动, 有没有解决方法, 当然是有的啦, 下面我们开始优化我们的代码吧


# 统一返回JSON格式进阶-全局处理(@RestControllerAdvice)
我师傅经常告诉我的一句话: "你就是一个小屁孩, 你遇到的问题都已经不知道有多少人遇到过了, 你会想到的问题, 已经有前辈想到过了.  你准备解决的问题, 已经有人把坑填了"。  是不是很鸡汤, 是不是很励志, 让我对前辈们充满着崇拜,  事实上他对我说的是: "自己去百度", 这五个大字, 其实这五个大字已经说明上明的B话了, 通过不断的百度和Google发现了很多的解决方案. 

我们都知道使用``@ResponseBody``注解会把返回``Object``序列化成``JSON字符串``,就先从这个入手吧, 大致就是在序列化前把``Object``赋值给``Result<Object>``就可以了, 大家可以观摩``org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice``和``org.springframework.web.bind.annotation.ResponseBody``

## @ResponseBody继承类
我们已经决定从``@ResponseBody``注解入手了就创建一个注解类继承``@ResponseBody``, 很干净什么都没有哈哈, 
``@ResponseResultBody`` 可以标记在类和方法上这样我们就可以跟自由的进行使用了
```Java
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@ResponseBody
public @interface ResponseResultBody {

}
```
## ResponseBodyAdvice继承类


```Java
@RestControllerAdvice
public class ResponseResultBodyAdvice implements ResponseBodyAdvice<Object> {

    private static final Class<? extends Annotation> ANNOTATION_TYPE = ResponseResultBody.class;

    /**
     * 判断类或者方法是否使用了 @ResponseResultBody
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), ANNOTATION_TYPE) || returnType.hasMethodAnnotation(ANNOTATION_TYPE);
    }

    /**
     * 当类或者方法使用了 @ResponseResultBody 就会调用这个方法
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 防止重复包裹的问题出现
        if (body instanceof Result) {
            return body;
        }
        return Result.success(body);
    }
}
```
## RestControllerAdvice返回测试
```Java
@RestController
@RequestMapping("/helloResult")
@ResponseResultBody
public class HelloResultController {

    private static final HashMap<String, Object> INFO;

    static {
        INFO = new HashMap<String, Object>();
        INFO.put("name", "galaxy");
        INFO.put("age", "70");
    }

    @GetMapping("hello")
    public HashMap<String, Object> hello() {
        return INFO;
    }

    /** 测试重复包裹 */
    @GetMapping("result")
    public Result<Map<String, Object>> helloResult() {
        return Result.success(INFO);
    }

    @GetMapping("helloError")
    public HashMap<String, Object> helloError() throws Exception {
        throw new Exception("helloError");
    }

    @GetMapping("helloMyError")
    public HashMap<String, Object> helloMyError() throws Exception {
        throw new ResultException();
    }
}
```
是不是很神奇, 直接返回``Object``就可以统一JSON格式了, 就不用每个返回都返回``Result<T>``对象了,直接让SpringMVC帮助我们进行统一的管理, 简直完美

只想看接口哦, ``helloError``和``helloMyError``是会直接抛出异常的接口,我好像没有对异常返回进行统一的处理哦

# 统一返回JSON格式进阶-异常处理(@ExceptionHandler))
卧槽, 异常处理, 差点把这茬给忘了, 这个异常处理就有很多方法了,先看看我师傅的处理方式, 我刚拿到这个代码的时候很想吐槽, 对异常类的处理这么残暴的吗, 直接用``PrintWriter``直接输出结果, 果然是老师傅, 我要是有100个异常类, 不得要写100个 if else了. 赶紧改改睡吧
```java
@Configuration
public class MyExceptionHandler implements HandlerExceptionResolver {

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception ex) {
        PrintWriter out = getPrintWrite(response);
        if (ex instanceof XXXException) {
            out.write(JsonUtil.formatJson(ResultEnum.PAY_ERROR.getCode(), ex.getMessage()));
        } else {
            out.write(JsonUtil.formatJson(ResultEnum.FAIL.getCode(), "服务器异常"));
        }
        if (null != out) {
            out.close();
        }
        return mav;
    }

    private PrintWriter getPrintWrite(HttpServletResponse response) {
        PrintWriter out = null;
        try {
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            out = response.getWriter();
        } catch (IOException e) {
            log.error("PrintWriter is exception", e);
        }
        return out;
    }
}
```
上面的代码看看还是没有问题的, 别学过去哦,  

## 异常处理@ResponseStatus(不推荐)

``@ResponseStatus``用法如下,可用在Controller类和Controller方法上以及Exception类上但是这样的工作量还是挺大的
```Java
@RestController
@RequestMapping("/error")
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Java的异常")
public class HelloExceptionController {

    private static final HashMap<String, Object> INFO;

    static {
        INFO = new HashMap<String, Object>();
        INFO.put("name", "galaxy");
        INFO.put("age", "70");
    }

    @GetMapping()
    public HashMap<String, Object> helloError() throws Exception {
        throw new Exception("helloError");
    }

    @GetMapping("helloJavaError")
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Java的异常")
    public HashMap<String, Object> helloJavaError() throws Exception {
        throw new Exception("helloError");
    }

    @GetMapping("helloMyError")
    public HashMap<String, Object> helloMyError() throws Exception {
        throw new MyException();
    }
}

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "自己定义的异常")
class MyException extends Exception {

}
```
## 全局异常处理@ExceptionHandler(推荐)
把``ResponseResultBodyAdvice``类进行改造一下,代码有点多了
主要参考了``org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler#handleException()``方法, 有空可以看一下

```Java
@Slf4j
@RestControllerAdvice
public class ResponseResultBodyAdvice implements ResponseBodyAdvice<Object> {

    private static final Class<? extends Annotation> ANNOTATION_TYPE = ResponseResultBody.class;

    /** 判断类或者方法是否使用了 @ResponseResultBody */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), ANNOTATION_TYPE) || returnType.hasMethodAnnotation(ANNOTATION_TYPE);
    }

    /** 当类或者方法使用了 @ResponseResultBody 就会调用这个方法 */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof Result) {
            return body;
        }
        return Result.success(body);
    }


    /**
     * 提供对标准Spring MVC异常的处理
     *
     * @param ex      the target exception
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
```

> 参考博客列表:
- https://www.toutiao.com/i6694404645827117572/
- https://blog.csdn.net/qq_36722039/article/details/80825117
- http://www.imooc.com/article/260354
- https://my.oschina.net/wangkang80/blog/1519189