<!-- TOC -->

- [内置约束](#%e5%86%85%e7%bd%ae%e7%ba%a6%e6%9d%9f)
  - [bean的验证约束](#bean%e7%9a%84%e9%aa%8c%e8%af%81%e7%ba%a6%e6%9d%9f)

<!-- /TOC -->


# 内置约束
## bean的验证约束

| 注解                            | 作用                                                  |
| ------------------------------- | ----------------------------------------------------- |
| @Null                           | 必须为null                                            |
| @NotNull                        | 不能为null                                            |
| @AssertTrue                     | 必须为true                                            |
| @AssertFalse                    | 必须为false                                           |
| @Pattern(regex=, flags=)        | 是否符合正则表达                                      |
| @Email                          | 验证邮箱格式                                          |
| @NotBlank                       | 不能为null,去空格后长度要大于0                        |
| @NotEmpty                       | 不能为null和""字符串和空集合                          |
| @Size(min=, max=)               | 字符串长度区间,集合大小区间 (min &gt; num &lt;= max ) |
| @Max(value=)                    | 必须小于等于指定的值( num &lt;= max )                 |
| @Min(value=)                    | 必须大于等于指定的值( num &gt;= min )                 |
| @Negative                       | 必须为负数, 不包含0 ( num &lt; 0 )                    |
| @NegativeOrZero                 | 必须为负数, 包含0 ( num &lt;= 0 )                     |
| @Positive                       | 必须为正数, 不包含0 ( num &gt; 0 )                    |
| @PositiveOrZero                 | 必须为正数, 包含0 ( num &gt;= 0 )                     |
| @DecimalMax(value=, inclusive=) | 必须小于等于指定的值, 字符串取长度 ( num &lt;= max )  |
| @DecimalMin(value=, inclusive=) | 必须大于等于指定的值, 字符串取长度 ( num &gt;= min )  |
| @Digits(integer=, fraction=)    | 最多由integer数字和fraction小数位组成的数字           |
| @Future                         | 为当前时间之后                                        |
| @FutureOrPresent                | 当前时间或当前时间之后                                |
| @Past                           | 为当前时间之前                                        |
| @PastOrPresent                  | 当前时间或当前时间之前                                |

> 注解支持的类型

| 注解                                                                                               | 支持类型                                                                                                                                                                                                                                                                                                                                                                                                             | HV额外支持                                                                         |
| -------------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------- |
| @Null<br>@NotNull                                                                                  | Object                                                                                                                                                                                                                                                                                                                                                                                                               |
| @AssertFalse<br> @AssertTrue                                                                       | Boolean,boolean                                                                                                                                                                                                                                                                                                                                                                                                      |
| @Pattern<br>@Email<br>@NotBlank                                                                    | CharSequence                                                                                                                                                                                                                                                                                                                                                                                                         |
| @NotEmpty<br>@Size(min=, max=)                                                                     | CharSequence<br>Collection, Map, arrays                                                                                                                                                                                                                                                                                                                                                                              |
| @Max(value=)<br>@Min(value=)<br>@Negative<br>@NegativeOrZero<br>@Positive<br>@PositiveOrZero       | BigDecimal<br>BigInteger<br>byte<br>short<br>int<br>long<br>相应的包装类;                                                                                                                                                                                                                                                                                                                                            | CharSequence子类,Number子类和javax.money.MonetaryAmount                            |
| @DecimalMax(value=, inclusive=)<br>@DecimalMin(value=, inclusive=)<br>@Digits(integer=, fraction=) | BigDecimal<br>BigInteger<br>CharSequence<br>byte<br>short<br>int<br>long<br>相应的包装类;                                                                                                                                                                                                                                                                                                                            | Number子类和javax.money.MonetaryAmount<br>@Digits不支持javax.money.MonetaryAmount  |
| @Future<br>@FutureOrPresent<br>@Past<br>@PastOrPresent                                             | java.util.Date<br>java.util.Calendar<br>java.time.Instant<br>java.time.LocalDate<br>java.time.LocalDateTime<br>java.time.LocalTime<br>java.time.MonthDay<br>java.time.OffsetDateTime<br>java.time.OffsetTime<br>java.time.Year<br>java.time.YearMonth<br>java.time.ZonedDateTime<br>java.time.chrono.HijrahDate<br>java.time.chrono.JapaneseDate<br>java.time.chrono.MinguoDate<br>java.time.chrono.ThaiBuddhistDate | 如果有Joda Time date/time API,则由HV附加支持：ReadablePartial子类和ReadableInstant |


