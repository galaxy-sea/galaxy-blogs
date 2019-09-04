
<!-- TOC -->

- [spring 注解](#spring-%E6%B3%A8%E8%A7%A3)
    - [Component(组件)](#component%E7%BB%84%E4%BB%B6)
    - [DI 自动注入](#di-%E8%87%AA%E5%8A%A8%E6%B3%A8%E5%85%A5)
    - [AOP](#aop)
- [spring MVC](#spring-mvc)
    - [ControllerAdvice //TODO](#controlleradvice-todo)
        - [RestControllerAdvice //TODO](#restcontrolleradvice-todo)
    - [CookieValue](#cookievalue)
    - [CrossOrigin //TODO](#crossorigin-todo)
    - [ExceptionHandler //TODO](#exceptionhandler-todo)
    - [InitBinder //TODO](#initbinder-todo)
    - [Mapping](#mapping)
        - [RequestMapping](#requestmapping)
    - [MatrixVariable //TODO](#matrixvariable-todo)
    - [ModelAttribute //TODO](#modelattribute-todo)
    - [PathVariable //TODO](#pathvariable-todo)
    - [RequestAttribute //TODO](#requestattribute-todo)
    - [RequestBody //TODO](#requestbody-todo)
    - [RequestHeader //TODO](#requestheader-todo)
    - [RequestParam //TODO](#requestparam-todo)
    - [RequestPart //TODO](#requestpart-todo)
    - [ResponseBody //TODO](#responsebody-todo)
        - [RestController //TODO](#restcontroller-todo)
    - [ResponseStatus //TODO](#responsestatus-todo)
    - [SessionAttribute //TODO](#sessionattribute-todo)
    - [SessionAttributes //TODO](#sessionattributes-todo)
    - [ValueConstants //TODO](#valueconstants-todo)

<!-- /TOC -->



# spring 注解
## Component(组件)
The value may indicate a suggestion for a logical component name, to be turned into a Spring bean in case of an autodetected component.
| 属性        | 类型&默认值                | @AliasFor | 说明 |
| :---------- | :------------------------- | :-------- | :--- |
| ``value()`` | String value() default ""; |           |      |
>继承类

| 注解                | 作用域 | 说明                                           |
| :------------------ | :----- | :--------------------------------------------- |
| ``@Controller``     | 类     |                                                |
| ``@RestController`` | 类     | ``@Controller`` 和 ``@ResponseBody``组成的注解 |
| ``@Repository``     | 类     |                                                |
| ``@Service``        | 类     |                                                |


## DI 自动注入
| 注解           | 属性         | 类型                             | 说明                                                  |
| :------------- | :----------- | :------------------------------: | :---------------------------------------------------- |
| ``@Autowired`` | required     | boolean required() default true; | Declares whether the annotated dependency is required |
| `` @Resource`` | Java原生注解 |
## AOP

# spring MVC
## ControllerAdvice  //TODO
### RestControllerAdvice  //TODO
## CookieValue  

|注解对象|
|:-----------------------------|
|link javax.servlet.http.Cookie|
|String|
|int|
|etc...|


| 属性               | 类型&默认值                                                | @AliasFor          | 说明               |
| :----------------- | :--------------------------------------------------------- | :----------------- | :----------------- |
| ``value()``        | String value() default "";                                 | @AliasFor("name")  |                    |
| ``name()``         | String name() default "";                                  | @AliasFor("value") | 要绑定cookie的名称 |
| ``required()``     | boolean required() default true;                           |                    |                    |
| ``defaultValue()`` | String defaultValue() default ValueConstants.DEFAULT_NONE; |                    |                    |




## CrossOrigin  //TODO
## ExceptionHandler  //TODO
## InitBinder  //TODO

## Mapping
### RequestMapping
>属性

| 属性           | 类型&默认值                          | @AliasFor          | 说明 |
| :------------- | :----------------------------------- | :----------------- | :--- |
| ``name()``     | String name() default "";            |                    |      |
| ``value()``    | String[] value() default {};         | @AliasFor("path")  |      |
| ``path()``     | String[] path() default {};          | @AliasFor("value") |      |
| ``method()``   | RequestMethod[] method() default {}; |                    |      |
| ``params()``   | String[] params() default {};        |                    |      |
| ``headers()``  | String[] headers() default {};       |                    |      |
| ``consumes()`` | String[] consumes() default {};      |                    |      |
| ``produces()`` | String[] produces() default {};      |                    |Accept<br>produces = "text/plain"<br>produces = {"text/plain", "application/*"}<br>produces = "application/json; charset=UTF-8"     |
>继承类

| 注解               | 组合注解                                       | 说明       |
| :----------------- | :--------------------------------------------- | :--------- |
| ``@DeleteMapping`` | @RequestMapping(method = RequestMethod.DELETE) | method默认 |
| ``@GetMapping``    | @RequestMapping(method = RequestMethod.GET)    | method默认 |
| ``@PatchMapping``  | @RequestMapping(method = RequestMethod.PATCH)  | method默认 |
| ``@PostMapping``   | @RequestMapping(method = RequestMethod.POST)   | method默认 |
| ``@PutMapping``    | @RequestMapping(method = RequestMethod.PUT)    | method默认 |

继承类的method()方法是默认的属性
## MatrixVariable  //TODO
## ModelAttribute  //TODO
## PathVariable  //TODO
## RequestAttribute  //TODO
## RequestBody  //TODO
## RequestHeader  //TODO
## RequestParam  //TODO


## RequestPart  //TODO
## ResponseBody  //TODO
### RestController  //TODO
## ResponseStatus  //TODO
## SessionAttribute  //TODO
## SessionAttributes  //TODO
## ValueConstants  //TODO



