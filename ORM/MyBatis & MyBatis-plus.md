全篇篇幅很长很长, 请静静的慢慢看O(∩_∩)O
<!-- TOC -->

- [1. 准备工作](#1-%E5%87%86%E5%A4%87%E5%B7%A5%E4%BD%9C)
- [2. Mapper XML 文件](#2-Mapper-XML-%E6%96%87%E4%BB%B6)
  - [2.1. select](#21-select)
    - [2.1.1. 分页参数RowBounds](#211-%E5%88%86%E9%A1%B5%E5%8F%82%E6%95%B0RowBounds)
  - [2.2. insert, update 和 delete](#22-insert-update-%E5%92%8C-delete)
  - [2.3. sql](#23-sql)
  - [2.4. Parameters //TODO](#24-Parameters-TODO)
    - [2.4.1. 字符串替换](#241-%E5%AD%97%E7%AC%A6%E4%B8%B2%E6%9B%BF%E6%8D%A2)
  - [2.5. Result Maps](#25-Result-Maps)
    - [2.5.1. 高级结果映射](#251-%E9%AB%98%E7%BA%A7%E7%BB%93%E6%9E%9C%E6%98%A0%E5%B0%84)
    - [2.5.2. resultMap](#252-resultMap)
    - [2.5.3. id & result](#253-id--result)
    - [2.5.4. 支持的 JDBC 类型](#254-%E6%94%AF%E6%8C%81%E7%9A%84-JDBC-%E7%B1%BB%E5%9E%8B)
    - [2.5.5. 构造方法](#255-%E6%9E%84%E9%80%A0%E6%96%B9%E6%B3%95)
    - [2.5.6. 关联](#256-%E5%85%B3%E8%81%94)
    - [2.5.7. 关联的嵌套查询](#257-%E5%85%B3%E8%81%94%E7%9A%84%E5%B5%8C%E5%A5%97%E6%9F%A5%E8%AF%A2)
    - [2.5.8. 关联的嵌套结果](#258-%E5%85%B3%E8%81%94%E7%9A%84%E5%B5%8C%E5%A5%97%E7%BB%93%E6%9E%9C)
    - [2.5.9. 集合](#259-%E9%9B%86%E5%90%88)
    - [2.5.10. 集合的嵌套查询](#2510-%E9%9B%86%E5%90%88%E7%9A%84%E5%B5%8C%E5%A5%97%E6%9F%A5%E8%AF%A2)
    - [2.5.11. 集合的嵌套结果](#2511-%E9%9B%86%E5%90%88%E7%9A%84%E5%B5%8C%E5%A5%97%E7%BB%93%E6%9E%9C)
    - [2.5.12. 鉴别器](#2512-%E9%89%B4%E5%88%AB%E5%99%A8)
  - [2.6. 自动映射](#26-%E8%87%AA%E5%8A%A8%E6%98%A0%E5%B0%84)
  - [2.7. 缓存](#27-%E7%BC%93%E5%AD%98)
    - [2.7.1. 使用自定义缓存](#271-%E4%BD%BF%E7%94%A8%E8%87%AA%E5%AE%9A%E4%B9%89%E7%BC%93%E5%AD%98)
    - [2.7.2. 参照缓存](#272-%E5%8F%82%E7%85%A7%E7%BC%93%E5%AD%98)
- [3. 动态SQL](#3-%E5%8A%A8%E6%80%81SQL)
  - [3.1. if](#31-if)
  - [3.2. choose, when, otherwise](#32-choose-when-otherwise)
  - [3.3. trim, where, set](#33-trim-where-set)
  - [3.4. foreach](#34-foreach)
  - [3.5. bind](#35-bind)
  - [3.6. 多数据库支持](#36-%E5%A4%9A%E6%95%B0%E6%8D%AE%E5%BA%93%E6%94%AF%E6%8C%81)
  - [3.7. 动态 SQL 中的可插拔脚本语言](#37-%E5%8A%A8%E6%80%81-SQL-%E4%B8%AD%E7%9A%84%E5%8F%AF%E6%8F%92%E6%8B%94%E8%84%9A%E6%9C%AC%E8%AF%AD%E8%A8%80)
- [4. 映射器注解](#4-%E6%98%A0%E5%B0%84%E5%99%A8%E6%B3%A8%E8%A7%A3)
  - [4.1. 参数](#41-%E5%8F%82%E6%95%B0)
  - [4.2. 使用](#42-%E4%BD%BF%E7%94%A8)
- [5. SQL语句构建器类](#5-SQL%E8%AF%AD%E5%8F%A5%E6%9E%84%E5%BB%BA%E5%99%A8%E7%B1%BB)
  - [5.1. 问题](#51-%E9%97%AE%E9%A2%98)
  - [5.2. The Solution](#52-The-Solution)
  - [5.3. SQL类](#53-SQL%E7%B1%BB)
  - [5.4. SqlBuilder 和 SelectBuilder (已经废弃)](#54-SqlBuilder-%E5%92%8C-SelectBuilder-%E5%B7%B2%E7%BB%8F%E5%BA%9F%E5%BC%83)
- [6. 关系映射](#6-%E5%85%B3%E7%B3%BB%E6%98%A0%E5%B0%84)
  - [6.1. OneToOne](#61-OneToOne)
    - [6.1.1. table & bean & dao](#611-table--bean--dao)
    - [6.1.2. xml](#612-xml)
    - [6.1.3. 注解](#613-%E6%B3%A8%E8%A7%A3)
  - [6.2. OneToMany](#62-OneToMany)
    - [6.2.1. table & bean &dao](#621-table--bean-dao)
    - [6.2.2. xml](#622-xml)
    - [6.2.3. 注解](#623-%E6%B3%A8%E8%A7%A3)
  - [6.3. ManyToMany](#63-ManyToMany)
    - [6.3.1. table & bean & dao](#631-table--bean--dao)
    - [6.3.2. xml](#632-xml)
    - [6.3.3. 注解](#633-%E6%B3%A8%E8%A7%A3)
- [7. 插件 // TODO](#7-%E6%8F%92%E4%BB%B6--TODO)
  - [7.1. 插件接口](#71-%E6%8F%92%E4%BB%B6%E6%8E%A5%E5%8F%A3)
  - [7.2. 插件初始化](#72-%E6%8F%92%E4%BB%B6%E5%88%9D%E5%A7%8B%E5%8C%96)
  - [7.3. 插件的的代理和反射技术](#73-%E6%8F%92%E4%BB%B6%E7%9A%84%E7%9A%84%E4%BB%A3%E7%90%86%E5%92%8C%E5%8F%8D%E5%B0%84%E6%8A%80%E6%9C%AF)
  - [7.4. 插件的工具类--MetaObject](#74-%E6%8F%92%E4%BB%B6%E7%9A%84%E5%B7%A5%E5%85%B7%E7%B1%BB--MetaObject)
  - [7.5. 插件开发的过程和实例](#75-%E6%8F%92%E4%BB%B6%E5%BC%80%E5%8F%91%E7%9A%84%E8%BF%87%E7%A8%8B%E5%92%8C%E5%AE%9E%E4%BE%8B)
    - [7.5.1. 需要拦截的签名](#751-%E9%9C%80%E8%A6%81%E6%8B%A6%E6%88%AA%E7%9A%84%E7%AD%BE%E5%90%8D)
    - [7.5.2. 实现拦截的方法](#752-%E5%AE%9E%E7%8E%B0%E6%8B%A6%E6%88%AA%E7%9A%84%E6%96%B9%E6%B3%95)
    - [7.5.3. 配置和运行](#753-%E9%85%8D%E7%BD%AE%E5%92%8C%E8%BF%90%E8%A1%8C)
    - [7.5.4. 插件的实例](#754-%E6%8F%92%E4%BB%B6%E7%9A%84%E5%AE%9E%E4%BE%8B)
  - [7.6. 分页插件](#76-%E5%88%86%E9%A1%B5%E6%8F%92%E4%BB%B6)
- [8. MyBatis-Plus](#8-MyBatis-Plus)
  - [8.1. 安装](#81-%E5%AE%89%E8%A3%85)
    - [8.1.1. Spring Boot](#811-Spring-Boot)
    - [8.1.2. Spring MVC](#812-Spring-MVC)
  - [8.2. 快速开始](#82-%E5%BF%AB%E9%80%9F%E5%BC%80%E5%A7%8B)
    - [8.2.1. 初始化工程](#821-%E5%88%9D%E5%A7%8B%E5%8C%96%E5%B7%A5%E7%A8%8B)
    - [8.2.2. 添加依赖](#822-%E6%B7%BB%E5%8A%A0%E4%BE%9D%E8%B5%96)
    - [8.2.3. 配置](#823-%E9%85%8D%E7%BD%AE)
    - [8.2.4. 编码](#824-%E7%BC%96%E7%A0%81)
    - [8.2.5. 测试](#825-%E6%B5%8B%E8%AF%95)
- [9. 代码生成器](#9-%E4%BB%A3%E7%A0%81%E7%94%9F%E6%88%90%E5%99%A8)
- [10. CRUD 接口](#10-CRUD-%E6%8E%A5%E5%8F%A3)
  - [10.1. Model CRUD 接口 // TODO 未实践](#101-Model-CRUD-%E6%8E%A5%E5%8F%A3--TODO-%E6%9C%AA%E5%AE%9E%E8%B7%B5)
  - [10.2. Mapper CRUD 接口](#102-Mapper-CRUD-%E6%8E%A5%E5%8F%A3)
    - [10.2.1. insert](#1021-insert)
    - [10.2.2. deleteById](#1022-deleteById)
    - [10.2.3. deleteByMap](#1023-deleteByMap)
    - [10.2.4. delete](#1024-delete)
    - [10.2.5. deleteBatchIds](#1025-deleteBatchIds)
    - [10.2.6. updateById](#1026-updateById)
    - [10.2.7. update](#1027-update)
    - [10.2.8. selectById](#1028-selectById)
    - [10.2.9. selectBatchIds](#1029-selectBatchIds)
    - [10.2.10. selectByMap](#10210-selectByMap)
    - [10.2.11. selectOne](#10211-selectOne)
    - [10.2.12. selectCount](#10212-selectCount)
    - [10.2.13. selectList](#10213-selectList)
    - [10.2.14. selectMaps](#10214-selectMaps)
    - [10.2.15. selectObjs](#10215-selectObjs)
    - [10.2.16. selectPage](#10216-selectPage)
    - [10.2.17. selectMapsPage](#10217-selectMapsPage)
  - [10.3. Service CRUD 接口](#103-Service-CRUD-%E6%8E%A5%E5%8F%A3)
    - [10.3.1. save](#1031-save)
    - [10.3.2. saveBatch](#1032-saveBatch)
    - [10.3.3. saveOrUpdateBatch](#1033-saveOrUpdateBatch)
    - [10.3.4. removeById](#1034-removeById)
    - [10.3.5. removeByMap](#1035-removeByMap)
    - [10.3.6. remove](#1036-remove)
    - [10.3.7. removeByIds](#1037-removeByIds)
    - [10.3.8. updateById](#1038-updateById)
    - [10.3.9. update](#1039-update)
    - [10.3.10. updateBatchById](#10310-updateBatchById)
    - [10.3.11. saveOrUpdate](#10311-saveOrUpdate)
    - [10.3.12. getById](#10312-getById)
    - [10.3.13. listByIds](#10313-listByIds)
    - [10.3.14. listByMap](#10314-listByMap)
    - [10.3.15. getOne](#10315-getOne)
    - [10.3.16. getMap](#10316-getMap)
    - [10.3.17. getObj](#10317-getObj)
    - [10.3.18. count](#10318-count)
    - [10.3.19. list](#10319-list)
    - [10.3.20. page](#10320-page)
    - [10.3.21. listMaps](#10321-listMaps)
    - [10.3.22. listObjs](#10322-listObjs)
    - [10.3.23. pageMaps](#10323-pageMaps)
- [11. 条件构造器](#11-%E6%9D%A1%E4%BB%B6%E6%9E%84%E9%80%A0%E5%99%A8)
  - [11.1. AbstractWrapper](#111-AbstractWrapper)
    - [11.1.1. allEq](#1111-allEq)
    - [11.1.2. eq](#1112-eq)
    - [11.1.3. ne](#1113-ne)
    - [11.1.4. gt](#1114-gt)
    - [11.1.5. ge](#1115-ge)
    - [11.1.6. lt](#1116-lt)
    - [11.1.7. le](#1117-le)
    - [11.1.8. between](#1118-between)
    - [11.1.9. notBetween](#1119-notBetween)
    - [11.1.10. like](#11110-like)
    - [11.1.11. notLike](#11111-notLike)
    - [11.1.12. likeLeft](#11112-likeLeft)
    - [11.1.13. likeRight](#11113-likeRight)
    - [11.1.14. isNull](#11114-isNull)
    - [11.1.15. isNotNull](#11115-isNotNull)
    - [11.1.16. in](#11116-in)
    - [11.1.17. notIn](#11117-notIn)
    - [11.1.18. inSql](#11118-inSql)
    - [11.1.19. notInSql](#11119-notInSql)
    - [11.1.20. groupBy](#11120-groupBy)
    - [11.1.21. orderByAsc](#11121-orderByAsc)
    - [11.1.22. orderByDesc](#11122-orderByDesc)
    - [11.1.23. orderBy](#11123-orderBy)
    - [11.1.24. having](#11124-having)
    - [11.1.25. or](#11125-or)
    - [11.1.26. and](#11126-and)
    - [11.1.27. apply](#11127-apply)
    - [11.1.28. last](#11128-last)
    - [11.1.29. exists](#11129-exists)
    - [11.1.30. notExists](#11130-notExists)
    - [11.1.31. nested](#11131-nested)
  - [11.2. QueryWrapper](#112-QueryWrapper)
    - [11.2.1. select](#1121-select)
    - [11.2.2. excludeColumns``@Deprecated``](#1122-excludeColumnsDeprecated)
  - [11.3. UpdateWrapper](#113-UpdateWrapper)
    - [11.3.1. set](#1131-set)
    - [11.3.2. setSql](#1132-setSql)
    - [11.3.3. lambda](#1133-lambda)
- [12. 分页插件](#12-%E5%88%86%E9%A1%B5%E6%8F%92%E4%BB%B6)
  - [12.1. 分页插件配置](#121-%E5%88%86%E9%A1%B5%E6%8F%92%E4%BB%B6%E9%85%8D%E7%BD%AE)
  - [12.2. 分页demo](#122-%E5%88%86%E9%A1%B5demo)

<!-- /TOC -->

# 1. 准备工作
环境准备
- Java8环境及ide
- maven 3
- MySQL 5.6

# 2. Mapper XML 文件
MyBatis 的真正强大在于它的映射语句，也是它的魔力所在。由于它的异常强大，映射器的 XML 文件就显得相对简单。如果拿它跟具有相同功能的 JDBC 代码进行对比，你会立即发现省掉了将近 95% 的代码。MyBatis 就是针对 SQL 构建的，并且比普通的方法做的更好。

SQL 映射文件有很少的几个顶级元素：
| 元素             | 描述                                                                                | 备注                                       |
| :--------------- | ----------------------------------------------------------------------------------- | ------------------------------------------ |
| cache            | 给定命名空间的缓存配置。                                                            |
| cache-re         | 其他命名空间缓存配置的引用。                                                        |
| resultMap        | 是最复杂也是最强大的元素，用来描述如何从数据库结果集中来加载对象。                  | 提供映射规则                               |
| ~~parameterMap~~ | 已废弃！老式风格的参数映射。内联参数是首选,这个元素可能在将来被移除，这里不会记录。 | 要删除了,不要用了O(∩_∩)O                   |
| sql              | 可被其他语句引用的可重用语句块。                                                    | 自定义的SQL语句，可以在多个 SQL 语句中使用 |
| insert           | 映射插入语句                                                                        | 执行后返回一个整数，代表插入的条数         |
| update           | 映射更新语句                                                                        | 执行后返回一个整数，代表更新的条数         |
| delete           | 映射删除语句                                                                        | 执行后返回一个整数，代表删除的条数         |
| select           | 映射查询语句                                                                        | 可以自定义参数,返回结果集等                |

## 2.1. select

查询语句是 MyBatis 中最常用的元素之一，光能把数据存到数据库中价值并不大，如果还能重新取出来才有用，多数应用也都是查询比修改要频繁。对每个插入、更新或删除操作，通常对应多个查询操作。这是 MyBatis 的基本原则之一，也是将焦点和努力放到查询和结果映射的原因。简单查询的 select 元素是非常简单的。比如：
```xml
<select id="selectPerson" parameterType="int" resultType="hashmap">
  SELECT * FROM PERSON WHERE ID = #{id}
</select>
```
这个语句被称作 selectPerson，接受一个 int（或 Integer）类型的参数，并返回一个 HashMap 类型的对象，其中的键是列名，值便是结果行中的对应值。
>
这就告诉 MyBatis 创建一个预处理语句参数，通过 JDBC，这样的一个参数在 SQL 中会由一个“?”来标识，并被传递到一个新的预处理语句中，就像这样：
```java
// Similar JDBC code, NOT MyBatis…
String selectPerson = "SELECT * FROM PERSON WHERE ID=?";
PreparedStatement ps = conn.prepareStatement(selectPerson);
ps.setInt(1,id);
```
当然，这需要很多单独的 JDBC 的代码来提取结果并将它们映射到对象实例中，这就是 MyBatis 节省你时间的地方。我们需要深入了解参数和结果映射，细节部分我们下面来了解。

select 元素有很多属性允许你配置，来决定每条语句的作用细节。

```xml
<select
  id="selectPerson"
  parameterType="int"
  parameterMap="deprecated"
  resultType="hashmap"
  resultMap="personResultMap"
  flushCache="false"
  useCache="true"
  timeout="10000"
  fetchSize="256"
  statementType="PREPARED"
  resultSetType="FORWARD_ONLY">
```
| 属性             | 描述                                                                                                                                                                                                                                   |
| :--------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| id               | 在命名空间中唯一的标识符，可以被用来引用这条语句。                                                                                                                                                                                     |
| parameterType    | 将会传入这条语句的参数类的完全限定名或别名。这个属性是可选的，因为 MyBatis 可以通过 TypeHandler 推断出具体传入语句的参数，默认值为 unset。                                                                                             |
| ~~parameterMap~~ | 这是引用外部 parameterMap 的已经被废弃的方法。使用内联参数映射和 parameterType 属性。                                                                                                                                                  |
| resultType       | 从这条语句中返回的期望类型的类的完全限定名或别名。注意如果是集合情形，那应该是集合可以包含的类型，而不能是集合本身。使用 resultType 或 resultMap，但不能同时使用。                                                                     |
| resultMap        | 外部 resultMap 的命名引用。结果集的映射是 MyBatis 最强大的特性，对其有一个很好的理解的话，许多复杂映射的情形都能迎刃而解。使用 resultMap 或 resultType，但不能同时使用。                                                               |
| flushCache       | 将其设置为 true，任何时候只要语句被调用，都会导致本地缓存和二级缓存都会被清空，默认值：false。                                                                                                                                         |
| useCache         | 将其设置为 true，将会导致本条语句的结果被二级缓存，默认值：对 select 元素为 true。                                                                                                                                                     |
| timeout          | 这个设置是在抛出异常之前，驱动程序等待数据库返回请求结果的秒数。默认值为 unset（依赖驱动）。                                                                                                                                           |
| fetchSize        | 这是尝试影响驱动程序每次批量返回的结果行数和这个设置值相等。默认值为 unset（依赖驱动）。                                                                                                                                               |
| statementType    | STATEMENT，PREPARED 或 CALLABLE 的一个。这会让 MyBatis 分别使用 Statement，PreparedStatement 或 CallableStatement，默认值：PREPARED。                                                                                                  |
| resultSetType    | FORWARD_ONLY，SCROLL_SENSITIVE 或 SCROLL_INSENSITIVE 中的一个，默认值为 unset （依赖驱动）。                                                                                                                                           |
| databaseId       | 如果配置了 databaseIdProvider，MyBatis 会加载所有的不带 databaseId 或匹配当前 databaseId 的语句；如果带或者不带的语句都有，则不带的会被忽略。                                                                                          |
| resultOrdered    | 这个设置仅针对嵌套结果 select 语句适用：如果为 true，就是假设包含了嵌套结果集或是分组了，这样的话当返回一个主结果行的时候，就不会发生有对前面结果集的引用的情况。这就使得在获取嵌套的结果集的时候不至于导致内存不够用。默认值：false。 |
| resultSets       | 这个设置仅对多结果集的情况适用，它将列出语句执行后返回的结果集并每个结果集给一个名称，名称是逗号分隔的。                                                                                                                               |
### 2.1.1. 分页参数RowBounds
MyBatis不仅支持分页,它还内置了一个专门处理分页的类RowBounds
```java
package org.apache.ibatis.session;

/**
 * @author Clinton Begin
 */
public class RowBounds {

  public static final int NO_ROW_OFFSET = 0;
  public static final int NO_ROW_LIMIT = Integer.MAX_VALUE;
  public static final RowBounds DEFAULT = new RowBounds();

  private int offset; //偏移量,即从的N行开始读取记录
  private int limit;  //限制条数,

  public RowBounds() {
    this.offset = NO_ROW_OFFSET;
    this.limit = NO_ROW_LIMIT;
  }

  public RowBounds(int offset, int limit) {
    this.offset = offset;
    this.limit = limit;
  }
}
//get&set
```
使用它十分简单,只要给接口增加一个RowBounds参数即可
```Java
List<User> findByRowBounds(RowBounds rowBounds);
```
对于SQL而言,映射文件不需要RowBounds的内容,RowBounds是MyBatis的一个附加参数,MyBatis会自动识别他,据此进行分页的.
```XML
    <!-- 通用查询结果列 -->
    <sql id="Base_Column_List">
        id, name, age, email
    </sql>

    <select id="findByRowBounds" resultType="User">
        select <include refid="Base_Column_List"></include>
        where user
    </select>
```
>注意:<p>
Row Bounds分页运用场景,他只能运用于一些小数据的查询.Row Bounds分页的原理是执行SQL的查询后,按照偏移量返回查询结果,所以对于大量的数据查询,它的性能并不佳,可以通过分页插件去处理.*^____^*// TODO
## 2.2. insert, update 和 delete
数据变更语句 insert，update 和 delete 的实现非常接近：
```xml
<insert
  id="insertAuthor"
  parameterType="domain.blog.Author"
  flushCache="true"
  statementType="PREPARED"
  keyProperty=""
  keyColumn=""
  useGeneratedKeys=""
  timeout="20">

<update
  id="updateAuthor"
  parameterType="domain.blog.Author"
  flushCache="true"
  statementType="PREPARED"
  timeout="20">

<delete
  id="deleteAuthor"
  parameterType="domain.blog.Author"
  flushCache="true"
  statementType="PREPARED"
  timeout="20">
```

| 属性             | 描述                                                                                                                                                                                                                 |
| :--------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| id               | 命名空间中的唯一标识符，可被用来代表这条语句。                                                                                                                                                                       |
| parameterType    | 将要传入语句的参数的完全限定类名或别名。这个属性是可选的，因为 MyBatis 可以通过 TypeHandler 推断出具体传入语句的参数，默认值为 unset。                                                                               |
| parameterMap     | 这是引用外部 parameterMap 的已经被废弃的方法。使用内联参数映射和 parameterType 属性。                                                                                                                                |
| flushCache       | 将其设置为 true，任何时候只要语句被调用，都会导致本地缓存和二级缓存都会被清空，默认值：true（对应插入、更新和删除语句）。                                                                                            |
| timeout          | 这个设置是在抛出异常之前，驱动程序等待数据库返回请求结果的秒数。默认值为 unset（依赖驱动）。                                                                                                                         |
| statementType    | STATEMENT，PREPARED 或 CALLABLE 的一个。这会让 MyBatis 分别使用 Statement，PreparedStatement 或 CallableStatement，默认值：PREPARED。                                                                                |
| useGeneratedKeys | （仅对 insert 和 update 有用）这会令 MyBatis 使用 JDBC 的 getGeneratedKeys 方法来取出由数据库内部生成的主键（比如：像 MySQL 和 SQL Server 这样的关系数据库管理系统的自动递增字段），默认值：false。                  |
| keyProperty      | （仅对 insert 和 update 有用）唯一标记一个属性，MyBatis 会通过 getGeneratedKeys 的返回值或者通过 insert 语句的 selectKey 子元素设置它的键值，默认：unset。如果希望得到多个生成的列，也可以是逗号分隔的属性名称列表。 |
| keyColumn        | （仅对 insert 和 update 有用）通过生成的键值设置表中的列名，这个设置仅在某些数据库（像 PostgreSQL）是必须的，当主键列不是表中的第一列的时候需要设置。如果希望得到多个生成的列，也可以是逗号分隔的属性名称列表。      |
| databaseId       | 如果配置了 databaseIdProvider，MyBatis 会加载所有的不带 databaseId 或匹配当前 databaseId 的语句；如果带或者不带的语句都有，则不带的会被忽略。                                                                        |

>下面就是 insert，update 和 delete 语句的示例：
```xml
<insert id="insertAuthor">
  insert into Author (id,username,password,email,bio)
  values (#{id},#{username},#{password},#{email},#{bio})
</insert>

<update id="updateAuthor">
  update Author set
    username = #{username},
    password = #{password},
    email = #{email},
    bio = #{bio}
  where id = #{id}
</update>

<delete id="deleteAuthor">
  delete from Author where id = #{id}
</delete>
```
如前所述，插入语句的配置规则更加丰富，在插入语句里面有一些额外的属性和子元素用来处理主键的生成，而且有多种生成方式。

首先，如果你的数据库支持自动生成主键的字段（比如 MySQL 和 SQL Server），那么你可以设置 useGeneratedKeys=”true”，然后再把 keyProperty 设置到目标属性上就OK了。例如，如果上面的 Author 表已经对 id 使用了自动生成的列类型，那么语句可以修改为:

```xml
<insert id="insertAuthor" useGeneratedKeys="true"
    keyProperty="id">
  insert into Author (username,password,email,bio)
  values (#{username},#{password},#{email},#{bio})
</insert>
```
如果你的数据库还支持多行插入, 你也可以传入一个`Author`s数组或集合，并返回自动生成的主键。
```xml
<insert id="insertAuthor" useGeneratedKeys="true"
    keyProperty="id">
  insert into Author (username, password, email, bio) values
  <foreach item="item" collection="list" separator=",">
    (#{item.username}, #{item.password}, #{item.email}, #{item.bio})
  </foreach>
</insert>
```
对于不支持自动生成类型的数据库或可能不支持自动生成主键的 JDBC 驱动，MyBatis 有另外一种方法来生成主键。

这里有一个简单（甚至很傻）的示例，它可以生成一个随机 ID（你最好不要这么做，但这里展示了 MyBatis 处理问题的灵活性及其所关心的广度）：

```xml
<insert id="insertAuthor">
  <selectKey keyProperty="id" resultType="int" order="BEFORE">
    select CAST(RANDOM()*1000000 as INTEGER) a from SYSIBM.SYSDUMMY1
  </selectKey>
  insert into Author
    (id, username, password, email,bio, favourite_section)
  values
    (#{id}, #{username}, #{password}, #{email}, #{bio}, #{favouriteSection,jdbcType=VARCHAR})
</insert>
```
在上面的示例中，selectKey 元素将会首先运行，Author 的 id 会被设置，然后插入语句会被调用。这给你了一个和数据库中来处理自动生成的主键类似的行为，避免了使 Java 代码变得复杂。

selectKey 元素描述如下：
```xml
<selectKey
  keyProperty="id"
  resultType="int"
  order="BEFORE"
  statementType="PREPARED">
```

| 属性          | 描述                                                                                                                                                                                                                                     |
| :------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| keyProperty   | selectKey 语句结果应该被设置的目标属性。如果希望得到多个生成的列，也可以是逗号分隔的属性名称列表。                                                                                                                                       |
| keyColumn     | 匹配属性的返回结果集中的列名称。如果希望得到多个生成的列，也可以是逗号分隔的属性名称列表。                                                                                                                                               |
| resultType    | 结果的类型。MyBatis 通常可以推算出来，但是为了更加确定写上也不会有什么问题。MyBatis 允许任何简单类型用作主键的类型，包括字符串。如果希望作用于多个生成的列，则可以使用一个包含期望属性的 Object 或一个 Map。                             |
| order         | 这可以被设置为 BEFORE 或 AFTER。如果设置为 BEFORE，那么它会首先选择主键，设置 keyProperty 然后执行插入语句。如果设置为 AFTER，那么先执行插入语句，然后是 selectKey 元素 - 这和像 Oracle 的数据库相似，在插入语句内部可能有嵌入索引调用。 |
| statementType | 与前面相同，MyBatis 支持 STATEMENT，PREPARED 和 CALLABLE 语句的映射类型，分别代表 PreparedStatement 和 CallableStatement 类型。                                                                                                          |
## 2.3. sql
这个元素可以被用来定义可重用的 SQL 代码段，可以包含在其他语句中。它可以被静态地(在加载参数) 参数化. 不同的属性值通过包含的实例变化. 比如：
```xml
<sql id="userColumns"> ${alias}.id,${alias}.username,${alias}.password </sql>
```

这个 SQL 片段可以被包含在其他语句中，例如：
```xml
<select id="selectUsers" resultType="map">
  select
    <include refid="userColumns"><property name="alias" value="t1"/></include>,
    <include refid="userColumns"><property name="alias" value="t2"/></include>
  from some_table t1
    cross join some_table t2
</select>
```
属性值也可以被用在 include 元素的 refid 属性里（
```xml
<include refid="${include_target}"/>
```
）或 include 内部语句中（
```
${prefix}Table
```
），例如：
```xml
<sql id="sometable">
  ${prefix}Table
</sql>

<sql id="someinclude">
  from
    <include refid="${include_target}"/>
</sql>

<select id="select" resultType="map">
  select
    field1, field2, field3
  <include refid="someinclude">
    <property name="prefix" value="Some"/>
    <property name="include_target" value="sometable"/>
  </include>
</select>
```
## 2.4. Parameters //TODO
前面的所有语句中你所见到的都是简单参数的例子，实际上参数是 MyBatis 非常强大的元素，对于简单的做法，大概 90% 的情况参数都很少，比如：
```xml
<select id="selectUsers" resultType="User">
  select id, username, password
  from users
  where id = #{id}
</select>
```
上面的这个示例说明了一个非常简单的命名参数映射。参数类型被设置为 int，这样这个参数就可以被设置成任何内容。原生的类型或简单数据类型（比如整型和字符串）因为没有相关属性，它会完全用参数值来替代。然而，如果传入一个复杂的对象，行为就会有一点不同了。比如：
```xml
<insert id="insertUser" parameterType="User">
  insert into users (id, username, password)
  values (#{id}, #{username}, #{password})
</insert>
```

如果 User 类型的参数对象传递到了语句中，id、username 和 password 属性将会被查找，然后将它们的值传入预处理语句的参数中。

这点相对于向语句中传参是比较好的，而且又简单，不过参数映射的功能远不止于此。

首先，像 MyBatis 的其他部分一样，参数也可以指定一个特殊的数据类型。

    #{property,javaType=int,jdbcType=NUMERIC}

像 MyBatis 的剩余部分一样，javaType 通常可以由参数对象确定，除非该对象是一个 HashMap。这时所使用的 `TypeHandler` 应该明确指明 javaType。

NOTE 如果一个列允许 null 值，并且会传递值 null 的参数，就必须要指定 JDBC Type。阅读 `PreparedStatement.setNull()` 的 JavaDocs 文档来获取更多信息。

为了以后定制类型处理方式，你也可以指定一个特殊的类型处理器类（或别名），比如：

    #{age,javaType=int,jdbcType=NUMERIC,typeHandler=MyTypeHandler}

尽管看起来配置变得越来越繁琐，但实际上，很少需要去设置它们。

对于数值类型，还有一个小数保留位数的设置，来确定小数点后保留的位数。

    #{height,javaType=double,jdbcType=NUMERIC,numericScale=2}

最后，mode 属性允许你指定 IN，OUT 或 INOUT 参数。如果参数为 OUT 或 INOUT，参数对象属性的真实值将会被改变，就像你在获取输出参数时所期望的那样。如果 mode 为 OUT（或 INOUT），而且 jdbcType 为 CURSOR(也就是 Oracle 的 REFCURSOR)，你必须指定一个 `resultMap` 来映射结果集 `ResultMap` 到参数类型。要注意这里的 `javaType` 属性是可选的，如果留空并且 jdbcType 是 `CURSOR`，它会被自动地被设为 `ResultMap。`

    #{department, mode=OUT, jdbcType=CURSOR, javaType=ResultSet, resultMap=departmentResultMap}

MyBatis 也支持很多高级的数据类型，比如结构体，但是当注册 out 参数时你必须告诉它语句类型名称。比如（再次提示，在实际中要像这样不能换行）：

    #{middleInitial, mode=OUT, jdbcType=STRUCT, jdbcTypeName=MY_TYPE, resultMap=departmentResultMap}

尽管所有这些选项很强大，但大多时候你只须简单地指定属性名，其他的事情 MyBatis 会自己去推断，顶多要为可能为空的列指定 `jdbcType`。

    #{firstName}
    #{middleInitial,jdbcType=VARCHAR}
    #{lastName}

### 2.4.1. 字符串替换
默认情况下,使用 `#{}` 格式的语法会导致 MyBatis 创建 `PreparedStatement` 参数并安全地设置参数（就像使用 ? 一样）。这样做更安全，更迅速，通常也是首选做法，不过有时你就是想直接在 SQL 语句中插入一个不转义的字符串。比如，像 ORDER BY，你可以这样来使用：

ORDER BY ${columnName}
这里 MyBatis 不会修改或转义字符串。

`NOTE` 用这种方式接受用户的输入，并将其用于语句中的参数是不安全的，会导致潜在的 SQL 注入攻击，因此要么不允许用户输入这些字段，要么自行转义并检验。

## 2.5. Result Maps
`resultMap` 元素是 MyBatis 中最重要最强大的元素。它可以让你从 90% 的 JDBC `ResultSets` 数据提取代码中解放出来, 并在一些情形下允许你做一些 JDBC 不支持的事情。 实际上，在对复杂语句进行联合映射的时候，它很可能可以代替数千行的同等功能的代码。 ResultMap 的设计思想是，简单的语句不需要明确的结果映射，而复杂一点的语句只需要描述它们的关系就行了。

你已经见过简单映射语句的示例了,但没有明确的 resultMap。比如:
```xml
<select id="selectUsers" resultType="map">
  select id, username, hashedPassword
  from some_table
  where id = #{id}
</select>
```
上述语句只是简单地将所有的列映射到 HashMap 的键上，这由 resultType 属性指定。 虽然在大部分情况下都够用，但是 HashMap 不是一个很好的领域模型。 你的程序更可能会使用 JavaBean 或 POJO(Plain Old Java Objects，普通 Java 对象)作为领域模型。 MyBatis 对两者都支持。看看下面这个 JavaBean:
```java
public class User {
  private int id;
  private String username;
  private String hashedPassword;
  get/set...
}
```
基于 JavaBean 的规范，上面这个类有 3 个属性：id,username 和 hashedPassword。这些属性会对应到 select 语句中的列名。

这样的一个 JavaBean 可以被映射到 `ResultSet`，就像映射到 `HashMap` 一样简单。
```xml
<select id="selectUsers" resultType="com.someapp.model.User">
  select id, username, hashedPassword
  from some_table
  where id = #{id}
</select>
```xml
类型别名是你的好帮手。使用它们，你就可以不用输入类的完全限定名称了。比如：
```xml
<!-- In mybatis-config.xml file -->
<typeAlias type="com.someapp.model.User" alias="User"/>

<!-- In SQL Mapping XML file -->
<select id="selectUsers" resultType="User">
  select id, username, hashedPassword
  from some_table
  where id = #{id}
</select>
```
这些情况下，MyBatis 会在幕后自动创建一个 `ResultMap`，再基于属性名来映射列到 JavaBean 的属性上。如果列名和属性名没有精确匹配，可以在 SELECT 语句中对列使用别名（这是一个 基本的 SQL 特性）来匹配标签。比如：
```xml
<select id="selectUsers" resultType="User">
  select
    user_id             as "id",
    user_name           as "userName",
    hashed_password     as "hashedPassword"
  from some_table
  where id = #{id}
</select>
```
`ResultMap` 最优秀的地方在于，虽然你已经对它相当了解了，但是根本就不需要显式地用到他们。 上面这些简单的示例根本不需要下面这些繁琐的配置。 出于示范的原因，让我们来看看最后一个示例中，如果使用外部的 resultMap 会怎样，这也是解决列名不匹配的另外一种方式。
```xml
<resultMap id="userResultMap" type="User">
  <id property="id" column="user_id" />
  <result property="username" column="user_name"/>
  <result property="password" column="hashed_password"/>
</resultMap>
```
引用它的语句使用 resultMap 属性就行了（注意我们去掉了 resultType 属性）。比如:
```xml
<select id="selectUsers" resultMap="userResultMap">
  select user_id, user_name, hashed_password
  from some_table
  where id = #{id}
</select>
```
如果世界总是这么简单就好了😭。
### 2.5.1. 高级结果映射
MyBatis 创建的一个想法是：数据库不可能永远是你所想或所需的那个样子。 我们希望每个数据库都具备良好的第三范式或 BCNF 范式，可惜它们不总都是这样。 如果有一个独立且完美的数据库映射模式，所有应用程序都可以使用它，那就太好了，但可惜也没有。 ResultMap 就是 MyBatis 对这个问题的答案。

比如，我们如何映射下面这个语句？
```xml
<!-- Very Complex Statement -->
<select id="selectBlogDetails" resultMap="detailedBlogResultMap">
  select
       B.id as blog_id,
       B.title as blog_title,
       B.author_id as blog_author_id,
       A.id as author_id,
       A.username as author_username,
       A.password as author_password,
       A.email as author_email,
       A.bio as author_bio,
       A.favourite_section as author_favourite_section,
       P.id as post_id,
       P.blog_id as post_blog_id,
       P.author_id as post_author_id,
       P.created_on as post_created_on,
       P.section as post_section,
       P.subject as post_subject,
       P.draft as draft,
       P.body as post_body,
       C.id as comment_id,
       C.post_id as comment_post_id,
       C.name as comment_name,
       C.comment as comment_text,
       T.id as tag_id,
       T.name as tag_name
  from Blog B
       left outer join Author A on B.author_id = A.id
       left outer join Post P on B.id = P.blog_id
       left outer join Comment C on P.id = C.post_id
       left outer join Post_Tag PT on PT.post_id = P.id
       left outer join Tag T on PT.tag_id = T.id
  where B.id = #{id}
</select>
```
你可能想把它映射到一个智能的对象模型，这个对象表示了一篇博客，它由某位作者所写， 有很多的博文，每篇博文有零或多条的评论和标签。 我们来看看下面这个完整的例子，它是一个非常复杂的 ResultMap （假设作者,博客,博文,评论和标签都是类型的别名）。 不用紧张，我们会一步一步来说明。 虽然它看起来令人望而生畏，但其实非常简单。

```xml
<!-- 超复杂的 Result Map -->
<resultMap id="detailedBlogResultMap" type="Blog">
  <constructor>
    <idArg column="blog_id" javaType="int"/>
  </constructor>
  <result property="title" column="blog_title"/>
  <association property="author" javaType="Author">
    <id property="id" column="author_id"/>
    <result property="username" column="author_username"/>
    <result property="password" column="author_password"/>
    <result property="email" column="author_email"/>
    <result property="bio" column="author_bio"/>
    <result property="favouriteSection" column="author_favourite_section"/>
  </association>
  <collection property="posts" ofType="Post">
    <id property="id" column="post_id"/>
    <result property="subject" column="post_subject"/>
    <association property="author" javaType="Author"/>
    <collection property="comments" ofType="Comment">
      <id property="id" column="comment_id"/>
    </collection>
    <collection property="tags" ofType="Tag" >
      <id property="id" column="tag_id"/>
    </collection>
    <discriminator javaType="int" column="draft">
      <case value="1" resultType="DraftPost"/>
    </discriminator>
  </collection>
</resultMap>
```
`resultMap` 元素有很多子元素和一个值得讨论的结构。 下面是 `resultMap` 元素的概念视图。

### 2.5.2. resultMap
- constructor - 用于在实例化类时，注入结果到构造方法中
  - idArg - ID 参数;标记出作为 ID 的结果可以帮助提高整体性能
  - arg - 将被注入到构造方法的一个普通结果
- id – 一个 ID 结果;标记出作为 ID 的结果可以帮助提高整体性能
- result – 注入到字段或 JavaBean 属性的普通结果
- association – 一个复杂类型的关联;许多结果将包装成这种类型
  - 嵌套结果映射 – 关联可以指定为一个 resultMap 元素，或者引用一个
- collection – 一个复杂类型的集合
  - 嵌套结果映射 – 集合可以指定为一个 resultMap 元素，或者引用一个
- discriminator – 使用结果值来决定使用哪个 resultMap
  - case – 基于某些值的结果映射
    - 嵌套结果映射 – 一个 case 也是一个映射它本身的结果,因此可以包含很多相 同的元素，或者它可以参照一个外部的 resultMap。

**ResultMap**
| 属性        | 描述                                                                                                                            |
| :---------- | ------------------------------------------------------------------------------------------------------------------------------- |
| id          | 当前命名空间中的一个唯一标识，用于标识一个result map.                                                                           |
| type        | 类的完全限定名, 或者一个类型别名 (内置的别名可以参考上面的表格).                                                                |
| autoMapping | 如果设置这个属性，MyBatis将会为这个ResultMap开启或者关闭自动映射。这个属性会覆盖全局的属性 autoMappingBehavior。默认值为：unset |
`最佳实践` 最好一步步地建立结果映射。单元测试可以在这个过程中起到很大帮助。如果你尝试一次创建一个像上面示例那样的巨大的结果映射， 那么很可能会出现错误而且很难去使用它来完成工作。 从最简单的形态开始，逐步进化。而且别忘了单元测试！使用框架的缺点是有时候它们看上去像黑盒子(无论源代码是否可见)。 为了确保你实现的行为和想要的一致，最好的选择是编写单元测试。提交 bug 的时候它也能起到很大的作用。

下一部分将详细说明每个元素。
### 2.5.3. id & result
```xml
<id property="id" column="post_id"/>
<result property="subject" column="post_subject"/>
```
这些是结果映射最基本的内容。id 和 result 都将一个列的值映射到一个简单数据类型(字符串,整型,双精度浮点数,日期等)的属性或字段。

这两者之间的唯一不同是， id 表示的结果将是对象的标识属性，这会在比较对象实例时用到。 这样可以提高整体的性能，尤其是缓存和嵌套结果映射(也就是联合映射)的时候。

两个元素都有一些属性:

**id & result**

| 属性        | 描述                                                                                                                                                                                                                                                                                                               |
| :---------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| property    | 映射到列结果的字段或属性。如果用来匹配的 JavaBeans 存在给定名字的属性，那么它将会被使用。否则 MyBatis 将会寻找给定名称 property 的字段。 无论是哪一种情形，你都可以使用通常的点式分隔形式进行复杂属性导航。比如,你可以这样映射一些简单的东西: “username” ,或者映射到一些复杂的东西: “address.street.number” 。 |
| column      | 数据库中的列名,或者是列的别名。一般情况下，这和 传递给 resultSet.getString(columnName) 方法的参数一样。                                                                                                                                                                                                            |
| javaType    | 一个 Java 类的完全限定名,或一个类型别名(参考上面内建类型别名 的列表) 。如果你映射到一个 JavaBean,MyBatis 通常可以断定类型。 然而,如果你映射到的是 HashMap,那么你应该明确地指定 javaType 来保证期望的行为。                                                                                                         |
| jdbcType    | JDBC 类型，所支持的 JDBC 类型参见这个表格之后的“支持的 JDBC 类型”。 只需要在可能执行插入、更新和删除的允许空值的列上指定 JDBC 类型。这是 JDBC 的要求而非 MyBatis 的要求。如果你直接面向 JDBC 编程,你需要对可能为 null 的值指定这个类型。                                                                         |
| typeHandler | 我们在前面讨论过的默认类型处理器。使用这个属性,你可以覆盖默 认的类型处理器。这个属性值是一个类型处理 器实现类的完全限定名，或者是类型别名。                                                                                                                                                                        |

### 2.5.4. 支持的 JDBC 类型
为了未来的参考,MyBatis 通过包含的 jdbcType 枚举型,支持下面的 JDBC 类型。
| -    | -   | -   | -   | - | - |
| :--- | --- | --- | --- |--|-----|
|BIT      | FLOAT|	CHAR|	TIMESTAMP|	OTHER|	UNDEFINED|
|TINYINT  |	REAL|	VARCHAR|	BINARY|	BLOB|	NVARCHAR|
|SMALLINT |	DOUBLE|	LONGVARCHAR|	VARBINARY|	CLOB|	NCHAR|
|INTEGER  |	NUMERIC|	DATE|	LONGVARBINARY|	BOOLEAN|	NCLOB|
|BIGINT   |	DECIMAL|	TIME|	NULL|	CURSOR|	ARRAY|

### 2.5.5. 构造方法
通过修改对象属性的方式，可以满足大多数的数据传输对象(Data Transfer Object,DTO)以及绝大部分领域模型的要求。 但有些情况下你想使用不可变类。 通常来说，很少或基本不变的、包含引用或查询数 据的表，很适合使用不可变类。 构造方法注入允许你在初始化时 为类设置属性的值，而不用暴露出公有方法。MyBatis 也支持私有属性和私有 JavaBeans 属 性来达到这个目的，但有一些人更青睐于构造方法注入。constructor 元素就是为此而生的。

看看下面这个构造方法:
```Java
public class User {
   //...
   public User(Integer id, String username, int age) {
     //...
  }
//...
}
```
为了将结果注入构造方法，MyBatis需要通过某种方式定位相应的构造方法。 在下面的例子中，MyBatis搜索一个声明了三个形参的的构造方法，以 `java.lang.Integer`, `java.lang.String` and `int` 的顺序排列。

```xml
<constructor>
   <idArg column="id" javaType="int"/>
   <arg column="username" javaType="String"/>
   <arg column="age" javaType="_int"/>
</constructor>
```
当你在处理一个带有多个形参的构造方法时，很容易在保证 arg 元素的正确顺序上出错。 从版本 3.4.3 开始，可以在指定参数名称的前提下，以任意顺序编写 arg 元素。 为了通过名称来引用构造方法参数，你可以添加 `@Param `注解，或者使用 '-parameters' 编译选项并启用 `useActualParamName` 选项（默认开启）来编译项目。 下面的例子对于同一个构造方法依然是有效的，尽管第二和第三个形参顺序与构造方法中声明的顺序不匹配。
```xml
<constructor>
   <idArg column="id" javaType="int" name="id" />
   <arg column="age" javaType="_int" name="age" />
   <arg column="username" javaType="String" name="username" />
</constructor>
```
如果类中存在名称和类型相同的属性，那么可以省略 javaType 。

剩余的属性和规则和普通的 id 和 result 元素是一样的。
| 属性        | 描述                                                                                                                                                                                                                                                                                                                                              |
| :---------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| column      | 数据库中的列名,或者是列的别名。一般情况下，这和 传递给 resultSet.getString(columnName) 方法的参数一样。                                                                                                                                                                                                                                           |
| javaType    | 一个 Java 类的完全限定名,或一个类型别名(参考上面内建类型别名的列表)。 如果你映射到一个 JavaBean,MyBatis 通常可以断定类型。然而,如 果你映射到的是 HashMap,那么你应该明确地指定 javaType 来保证期望的 行为。                                                                                                                                        |
| jdbcType    | JDBC 类型，所支持的 JDBC 类型参见这个表格之前的“支持的 JDBC 类型”。 只需要在可能执行插入、更新和删除的允许空值的列上指定 JDBC 类型。这是 JDBC 的要求而非 MyBatis 的要求。如果你直接面向 JDBC 编程,你需要对可能为 null 的值指定这个类型。                                                                                                        |
| typeHandler | 我们在前面讨论过的默认类型处理器。使用这个属性,你可以覆盖默 认的类型处理器。这个属性值是一个类型处理 器实现类的完全限定名，或者是类型别名。                                                                                                                                                                                                       |
| select      | 用于加载复杂类型属性的映射语句的 ID,它会从 column 属性中指定的列检索数据，作为参数传递给此 select 语句。具体请参考 Association 标签。                                                                                                                                                                                                             |
| resultMap   | ResultMap 的 ID，可以将嵌套的结果集映射到一个合适的对象树中，功能和 select 属性相似，它可以实现将多表连接操作的结果映射成一个单一的ResultSet。这样的ResultSet将会将包含重复或部分数据重复的结果集正确的映射到嵌套的对象树中。为了实现它, MyBatis允许你 “串联” ResultMap,以便解决嵌套结果集的问题。想了解更多内容，请参考下面的Association元素。 |
| name        | 构造方法形参的名字。从3.4.3版本开始，通过指定具体的名字，你可以以任意顺序写入arg元素。参看上面的解释。                                                                                                                                                                                                                                            |

### 2.5.6. 关联
```xml
<association property="author" column="blog_author_id" javaType="Author">
  <id property="id" column="author_id"/>
  <result property="username" column="author_username"/>
</association>
```
关联元素处理“有一个”类型的关系。比如,在我们的示例中,一个博客有一个用户。 关联映射就工作于这种结果之上。你指定了目标属性,来获取值的列,属性的 java 类型(很 多情况下 MyBatis 可以自己算出来) ,如果需要的话还有 jdbc 类型,如果你想覆盖或获取的 结果值还需要类型控制器。

关联中不同的是你需要告诉 MyBatis 如何加载关联。MyBatis 在这方面会有两种不同的 方式:

- 嵌套查询:通过执行另外一个 SQL 映射语句来返回预期的复杂类型。
- 嵌套结果:使用嵌套结果映射来处理重复的联合结果的子集。首先,然让我们来查看这个元素的属性。所有的你都会看到,它和普通的只由 select 和
resultMap 属性的结果映射不同。

| 属性        | 描述                                                                                                                                                                                                                                                                                                  |
| :---------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| property    | 映射到列结果的字段或属性。如果用来匹配的 JavaBeans 存在给定名字的属性，那么它将会被使用。 否则 MyBatis 将会寻找与给定名称相同的字段。 这两种情形你可以使用通常点式的复杂属性导航。比如,你可以这样映射 一 些 东 西 :“ username ”, 或 者 映 射 到 一 些 复 杂 的 东 西 : “address.street.number” 。 |
| javaType    | 一个 Java 类的完全限定名,或一个类型别名(参考上面内建类型别名的列 表) 。如果你映射到一个 JavaBean,MyBatis 通常可以断定类型。然而,如 javaType 果你映射到的是 HashMap,那么你应该明确地指定 javaType 来保证所需的 行为。                                                                                  |
| jdbcType    | 在这个表格之前的所支持的 JDBC 类型列表中的类型。JDBC 类型是仅仅 需要对插入, 更新和删除操作可能为空的列进行处理。这是 JDBC 的需要, jdbcType 而不是 MyBatis 的。如果你直接使用 JDBC 编程,你需要指定这个类型-但 仅仅对可能为空的值。                                                                     |
| typeHandler | 我们在前面讨论过默认的类型处理器。使用这个属性,你可以覆盖默认的 typeHandler 类型处理器。 这个属性值是类的完全限定名或者是一个类型处理器的实现, 或者是类型别名。                                                                                                                                       |

### 2.5.7. 关联的嵌套查询
| 属性      | 描述                                                                                                                                                                                                                                                                                                                                                                |
| :-------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| column    | 来自数据库的列名,或重命名的列标签。这和通常传递给 resultSet.getString(columnName)方法的字符串是相同的。 column 注 意 : 要 处 理 复 合 主 键 , 你 可 以 指 定 多 个 列 名 通 过 column= ” {prop1=col1,prop2=col2} ” 这种语法来传递给嵌套查询语 句。这会引起 prop1 和 prop2 以参数对象形式来设置给目标嵌套查询语句。                                                |
| select    | 另外一个映射语句的 ID,可以加载这个属性映射需要的复杂类型。获取的 在列属性中指定的列的值将被传递给目标 select 语句作为参数。表格后面 有一个详细的示例。 select 注 意 : 要 处 理 复 合 主 键 , 你 可 以 指 定 多 个 列 名 通 过 column= ” {prop1=col1,prop2=col2} ” 这种语法来传递给嵌套查询语 句。这会引起 prop1 和 prop2 以参数对象形式来设置给目标嵌套查询语句。 |
| fetchType | 可选的。有效值为 lazy和eager。 如果使用了，它将取代全局配置参数lazyLoadingEnabled。                                                                                                                                                                                                                                                                                 |

示例:
```xml
<resultMap id="blogResult" type="Blog">
  <association property="author" column="author_id" javaType="Author" select="selectAuthor"/>
</resultMap>

<select id="selectBlog" resultMap="blogResult">
  SELECT * FROM BLOG WHERE ID = #{id}
</select>

<select id="selectAuthor" resultType="Author">
  SELECT * FROM AUTHOR WHERE ID = #{id}
</select>
```
我们有两个查询语句:一个来加载博客,另外一个来加载作者,而且博客的结果映射描 述了“selectAuthor”语句应该被用来加载它的 author 属性。

其他所有的属性将会被自动加载,假设它们的列和属性名相匹配。

这种方式很简单, 但是对于大型数据集合和列表将不会表现很好。 问题就是我们熟知的 “N+1 查询问题”。概括地讲,N+1 查询问题可以是这样引起的:

- 你执行了一个单独的 SQL 语句来获取结果列表(就是“+1”)。
- 对返回的每条记录,你执行了一个查询语句来为每个加载细节(就是“N”)。
这个问题会导致成百上千的 SQL 语句被执行。这通常不是期望的。

MyBatis 能延迟加载这样的查询就是一个好处,因此你可以分散这些语句同时运行的消 耗。然而,如果你加载一个列表,之后迅速迭代来访问嵌套的数据,你会调用所有的延迟加 载,这样的行为可能是很糟糕的。

  所以还有另外一种方法。
### 2.5.8. 关联的嵌套结果
| 属性          | 描述                                                                                                                                                                                                                                                                                                                                                         |
| :------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| resultMap     | 这是结果映射的 ID,可以映射关联的嵌套结果到一个合适的对象图中。这 是一种替代方法来调用另外一个查询语句。这允许你联合多个表来合成到 resultMap 一个单独的结果集。这样的结果集可能包含重复,数据的重复组需要被分 解,合理映射到一个嵌套的对象图。为了使它变得容易,MyBatis 让你“链 接”结果映射,来处理嵌套结果。一个例子会很容易来仿照,这个表格后 面也有一个示例。 |
| columnPrefix  | 当连接多表时，你将不得不使用列别名来避免ResultSet中的重复列名。指定columnPrefix允许你映射列名到一个外部的结果集中。 请看后面的例子。                                                                                                                                                                                                                         |
| notNullColumn | 默认情况下，子对象仅在至少一个列映射到其属性非空时才创建。 通过对这个属性指定非空的列将改变默认行为，这样做之后Mybatis将仅在这些列非空时才创建一个子对象。 可以指定多个列名，使用逗号分隔。默认值：未设置(unset)。                                                                                                                                           |
| autoMapping   | 如果使用了，当映射结果到当前属性时，Mybatis将启用或者禁用自动映射。 该属性覆盖全局的自动映射行为。 注意它对外部结果集无影响，所以在select or resultMap属性中这个是毫无意义的。 默认值：未设置(unset)。                                                                                                                                                       |
在上面你已经看到了一个非常复杂的嵌套关联的示例。 下面这个是一个非常简单的示例 来说明它如何工作。代替了执行一个分离的语句,我们联合博客表和作者表在一起,就像:
```xml
<select id="selectBlog" resultMap="blogResult">
  select
    B.id            as blog_id,
    B.title         as blog_title,
    B.author_id     as blog_author_id,
    A.id            as author_id,
    A.username      as author_username,
    A.password      as author_password,
    A.email         as author_email,
    A.bio           as author_bio
  from Blog B left outer join Author A on B.author_id = A.id
  where B.id = #{id}
</select>
```
注意这个联合查询, 以及采取保护来确保所有结果被唯一而且清晰的名字来重命名。 这使得映射非常简单。现在我们可以映射这个结果:
```xml
<resultMap id="blogResult" type="Blog">
  <id property="id" column="blog_id" />
  <result property="title" column="blog_title"/>
  <association property="author" column="blog_author_id" javaType="Author" resultMap="authorResult"/>
</resultMap>

<resultMap id="authorResult" type="Author">
  <id property="id" column="author_id"/>
  <result property="username" column="author_username"/>
  <result property="password" column="author_password"/>
  <result property="email" column="author_email"/>
  <result property="bio" column="author_bio"/>
</resultMap>
```
在上面的示例中你可以看到博客的作者关联代表着“authorResult”结果映射来加载作 者实例。

非常重要: id元素在嵌套结果映射中扮演着非 常重要的角色。你应该总是指定一个或多个可以唯一标识结果的属性。实际上如果你不指定它的话, MyBatis仍然可以工作,但是会有严重的性能问题。在可以唯一标识结果的情况下, 尽可能少的选择属性。主键是一个显而易见的选择（即使是复合主键）。

现在,上面的示例用了外部的结果映射元素来映射关联。这使得 Author 结果映射可以 重用。然而,如果你不需要重用它的话,或者你仅仅引用你所有的结果映射合到一个单独描 述的结果映射中。你可以嵌套结果映射。这里给出使用这种方式的相同示例:
```xml
<resultMap id="blogResult" type="Blog">
  <id property="id" column="blog_id" />
  <result property="title" column="blog_title"/>
  <association property="author" javaType="Author">
    <id property="id" column="author_id"/>
    <result property="username" column="author_username"/>
    <result property="password" column="author_password"/>
    <result property="email" column="author_email"/>
    <result property="bio" column="author_bio"/>
  </association>
</resultMap>
```
如果blog有一个co-author怎么办？ select语句将看起来这个样子：

```xml
<select id="selectBlog" resultMap="blogResult">
  select
    B.id            as blog_id,
    B.title         as blog_title,
    A.id            as author_id,
    A.username      as author_username,
    A.password      as author_password,
    A.email         as author_email,
    A.bio           as author_bio,
    CA.id           as co_author_id,
    CA.username     as co_author_username,
    CA.password     as co_author_password,
    CA.email        as co_author_email,
    CA.bio          as co_author_bio
  from Blog B
  left outer join Author A on B.author_id = A.id
  left outer join Author CA on B.co_author_id = CA.id
  where B.id = #{id}
</select>
```
再次调用Author的resultMap将定义如下：
```xml
<resultMap id="authorResult" type="Author">
  <id property="id" column="author_id"/>
  <result property="username" column="author_username"/>
  <result property="password" column="author_password"/>
  <result property="email" column="author_email"/>
  <result property="bio" column="author_bio"/>
</resultMap>
```
因为结果中的列名与resultMap中的列名不同。 你需要指定columnPrefix去重用映射co-author结果的resultMap。
```xml
<resultMap id="blogResult" type="Blog">
  <id property="id" column="blog_id" />
  <result property="title" column="blog_title"/>
  <association property="author"
    resultMap="authorResult" />
  <association property="coAuthor"
    resultMap="authorResult"
    columnPrefix="co_" />
</resultMap>
```
上面你已经看到了如何处理“有一个”类型关联。但是“有很多个”是怎样的?下面这 个部分就是来讨论这个主题的。

### 2.5.9. 集合
```xml
<collection property="posts" ofType="domain.blog.Post">
  <id property="id" column="post_id"/>
  <result property="subject" column="post_subject"/>
  <result property="body" column="post_body"/>
</collection>
```
集合元素的作用几乎和关联是相同的。实际上,它们也很相似,文档的异同是多余的。 所以我们更多关注于它们的不同。

我们来继续上面的示例,一个博客只有一个作者。但是博客有很多文章。在博客类中, 这可以由下面这样的写法来表示:

```xml
private List<Post> posts;
```
要映射嵌套结果集合到 List 中,我们使用集合元素。就像关联元素一样,我们可以从 连接中使用嵌套查询,或者嵌套结果。

### 2.5.10. 集合的嵌套查询
首先,让我们看看使用嵌套查询来为博客加载文章。

```xml
<resultMap id="blogResult" type="Blog">
  <collection property="posts" javaType="ArrayList" column="id" ofType="Post" select="selectPostsForBlog"/>
</resultMap>

<select id="selectBlog" resultMap="blogResult">
  SELECT * FROM BLOG WHERE ID = #{id}
</select>

<select id="selectPostsForBlog" resultType="Post">
  SELECT * FROM POST WHERE BLOG_ID = #{id}
</select>
```
这里你应该注意很多东西,但大部分代码和上面的关联元素是非常相似的。首先,你应 该注意我们使用的是集合元素。然后要注意那个新的“ofType”属性。这个属性用来区分 JavaBean(或字段)属性类型和集合包含的类型来说是很重要的。所以你可以读出下面这个 映射:

```xml
<collection property="posts" javaType="ArrayList" column="id" ofType="Post" select="selectPostsForBlog"/>
```
读作: “在 Post 类型的 ArrayList 中的 posts 的集合。”

javaType 属性是不需要的,因为 MyBatis 在很多情况下会为你算出来。所以你可以缩短 写法:

```xml
<collection property="posts" column="id" ofType="Post" select="selectPostsForBlog"/>
```
### 2.5.11. 集合的嵌套结果

至此,你可以猜测集合的嵌套结果是如何来工作的,因为它和关联完全相同,除了它应 用了一个“ofType”属性

首先, 让我们看看 SQL:

```xml
<select id="selectBlog" resultMap="blogResult">
  select
  B.id as blog_id,
  B.title as blog_title,
  B.author_id as blog_author_id,
  P.id as post_id,
  P.subject as post_subject,
  P.body as post_body,
  from Blog B
  left outer join Post P on B.id = P.blog_id
  where B.id = #{id}
</select>
```
我们又一次联合了博客表和文章表,而且关注于保证特性,结果列标签的简单映射。现 在用文章映射集合映射博客,可以简单写为:
```xml
<resultMap id="blogResult" type="Blog">
  <id property="id" column="blog_id" />
  <result property="title" column="blog_title"/>
  <collection property="posts" ofType="Post">
    <id property="id" column="post_id"/>
    <result property="subject" column="post_subject"/>
    <result property="body" column="post_body"/>
  </collection>
</resultMap>
```
同样,要记得 id 元素的重要性,如果你不记得了,请阅读上面的关联部分。

同样, 如果你引用更长的形式允许你的结果映射的更多重用, 你可以使用下面这个替代 的映射:

```xml
<resultMap id="blogResult" type="Blog">
  <id property="id" column="blog_id" />
  <result property="title" column="blog_title"/>
  <collection property="posts" ofType="Post" resultMap="blogPostResult" columnPrefix="post_"/>
</resultMap>

<resultMap id="blogPostResult" type="Post">
  <id property="id" column="id"/>
  <result property="subject" column="subject"/>
  <result property="body" column="body"/>
</resultMap>
```
``注意`` 这个对你所映射的内容没有深度,广度或关联和集合相联合的限制。当映射它们 时你应该在大脑中保留它们的表现。 你的应用在找到最佳方法前要一直进行的单元测试和性 能测试。好在 myBatis 让你后来可以改变想法,而不对你的代码造成很小(或任何)影响。

高级关联和集合映射是一个深度的主题。文档只能给你介绍到这了。加上一点联系,你 会很快清楚它们的用法。
### 2.5.12. 鉴别器
```xml
<discriminator javaType="int" column="draft">
  <case value="1" resultType="DraftPost"/>
</discriminator>
```
有时一个单独的数据库查询也许返回很多不同 (但是希望有些关联) 数据类型的结果集。 鉴别器元素就是被设计来处理这个情况的, 还有包括类的继承层次结构。 鉴别器非常容易理 解,因为它的表现很像 Java 语言中的 switch 语句。

定义鉴别器指定了 column 和 javaType 属性。 列是 MyBatis 查找比较值的地方。 JavaType 是需要被用来保证等价测试的合适类型(尽管字符串在很多情形下都会有用)。比如:

```xml
<resultMap id="vehicleResult" type="Vehicle">
  <id property="id" column="id" />
  <result property="vin" column="vin"/>
  <result property="year" column="year"/>
  <result property="make" column="make"/>
  <result property="model" column="model"/>
  <result property="color" column="color"/>
  <discriminator javaType="int" column="vehicle_type">
    <case value="1" resultMap="carResult"/>
    <case value="2" resultMap="truckResult"/>
    <case value="3" resultMap="vanResult"/>
    <case value="4" resultMap="suvResult"/>
  </discriminator>
</resultMap>
```
在这个示例中, MyBatis 会从结果集中得到每条记录, 然后比较它的 vehicle 类型的值。 如果它匹配任何一个鉴别器的实例,那么就使用这个实例指定的结果映射。换句话说,这样 做完全是剩余的结果映射被忽略(除非它被扩展,这在第二个示例中讨论) 。如果没有任何 一个实例相匹配,那么 MyBatis 仅仅使用鉴别器块外定义的结果映射。所以,如果 carResult 按如下声明:

```xml
<resultMap id="carResult" type="Car">
  <result property="doorCount" column="door_count" />
</resultMap>
```
那么只有 doorCount 属性会被加载。这步完成后完整地允许鉴别器实例的独立组,尽管 和父结果映射可能没有什么关系。这种情况下,我们当然知道 cars 和 vehicles 之间有关系, 如 Car 是一个 Vehicle 实例。因此,我们想要剩余的属性也被加载。我们设置的结果映射的 简单改变如下。

```xml
<resultMap id="carResult" type="Car" extends="vehicleResult">
  <result property="doorCount" column="door_count" />
</resultMap>
```
现在 vehicleResult 和 carResult 的属性都会被加载了。

尽管曾经有些人会发现这个外部映射定义会多少有一些令人厌烦之处。 因此还有另外一 种语法来做简洁的映射风格。比如:

```xml
<resultMap id="vehicleResult" type="Vehicle">
  <id property="id" column="id" />
  <result property="vin" column="vin"/>
  <result property="year" column="year"/>
  <result property="make" column="make"/>
  <result property="model" column="model"/>
  <result property="color" column="color"/>
  <discriminator javaType="int" column="vehicle_type">
    <case value="1" resultType="carResult">
      <result property="doorCount" column="door_count" />
    </case>
    <case value="2" resultType="truckResult">
      <result property="boxSize" column="box_size" />
      <result property="extendedCab" column="extended_cab" />
    </case>
    <case value="3" resultType="vanResult">
      <result property="powerSlidingDoor" column="power_sliding_door" />
    </case>
    <case value="4" resultType="suvResult">
      <result property="allWheelDrive" column="all_wheel_drive" />
    </case>
  </discriminator>
</resultMap>
```
``要记得`` 这些都是结果映射, 如果你不指定任何结果, 那么 MyBatis 将会为你自动匹配列 和属性。所以这些例子中的大部分是很冗长的,而其实是不需要的。也就是说,很多数据库 是很复杂的,我们不太可能对所有示例都能依靠它。

## 2.6. 自动映射
正如你在前面一节看到的，在简单的场景下，MyBatis可以替你自动映射查询结果。 如果遇到复杂的场景，你需要构建一个result map。 但是在本节你将看到，你也可以混合使用这两种策略。 让我们到深一点的层面上看看自动映射是怎样工作的。

当自动映射查询结果时，MyBatis会获取sql返回的列名并在java类中查找相同名字的属性（忽略大小写）。 这意味着如果Mybatis发现了ID列和id属性，Mybatis会将ID的值赋给id。

通常数据库列使用大写单词命名，单词间用下划线分隔；而java属性一般遵循驼峰命名法。 为了在这两种命名方式之间启用自动映射，需要将 ``mapUnderscoreToCamelCase``设置为true。

自动映射甚至在特定的result map下也能工作。在这种情况下，对于每一个result map,所有的ResultSet提供的列， 如果没有被手工映射，则将被自动映射。自动映射处理完毕后手工映射才会被处理。 在接下来的例子中， id 和 userName列将被自动映射， hashed_password 列将根据配置映射。

```xml
<select id="selectUsers" resultMap="userResultMap">
  select
    user_id             as "id",
    user_name           as "userName",
    hashed_password
  from some_table
  where id = #{id}
</select>
```
```xml
<resultMap id="userResultMap" type="User">
  <result property="password" column="hashed_password"/>
</resultMap>
```
有三种自动映射等级：

- ``NONE`` - 禁用自动映射。仅设置手动映射属性。
- ``PARTIAL`` - 将自动映射结果除了那些有内部定义内嵌结果映射的(joins).
- ``FULL`` - 自动映射所有。
默认值是``PARTIAL``，这是有原因的。当使用``FULL``时，自动映射会在处理join结果时执行，并且join取得若干相同行的不同实体数据，因此这可能导致非预期的映射。下面的例子将展示这种风险：

```xml
<select id="selectBlog" resultMap="blogResult">
  select
    B.id,
    B.title,
    A.username,
  from Blog B left outer join Author A on B.author_id = A.id
  where B.id = #{id}
</select>
```
```xml
<resultMap id="blogResult" type="Blog">
  <association property="author" resultMap="authorResult"/>
</resultMap>

<resultMap id="authorResult" type="Author">
  <result property="username" column="author_username"/>
</resultMap>
```
在结果中Blog和Author均将自动映射。但是注意Author有一个id属性，在ResultSet中有一个列名为id， 所以Author的id将被填充为Blog的id，这不是你所期待的。所以需要谨慎使用``FULL``。

通过添加``autoMapping``属性可以忽略自动映射等级配置，你可以启用或者禁用自动映射指定的ResultMap。
```xml
<resultMap id="userResultMap" type="User" autoMapping="false">
  <result property="password" column="hashed_password"/>
</resultMap>
```
## 2.7. 缓存
MyBatis 包含一个非常强大的查询缓存特性,它可以非常方便地配置和定制。MyBatis 3 中的缓存实现的很多改进都已经实现了,使得它更加强大而且易于配置。

默认情况下是没有开启缓存的,除了局部的 session 缓存,可以增强变现而且处理循环 依赖也是必须的。要开启二级缓存,你需要在你的 SQL 映射文件中添加一行:
```xml
<cache/>
```
字面上看就是这样。这个简单语句的效果如下:

- 映射语句文件中的所有 select 语句将会被缓存。
- 映射语句文件中的所有 insert,update 和 delete 语句会刷新缓存。
- 缓存会使用 Least Recently Used(LRU,最近最少使用的)算法来收回。
- 根据时间表(比如 no Flush Interval,没有刷新间隔), 缓存不会以任何时间顺序 来刷新。
- 缓存会存储列表集合或对象(无论查询方法返回什么)的 1024 个引用。
- 缓存会被视为是 read/write(可读/可写)的缓存,意味着对象检索不是共享的,而 且可以安全地被调用者修改,而不干扰其他调用者或线程所做的潜在修改。
  
``NOTE`` The cache will only apply to statements declared in the mapping file where the cache tag is located. If you are using the Java API in conjunction with the XML mapping files, then statements declared in the companion interface will not be cached by default. You will need to refer to the cache region using the @CacheNamespaceRef annotation.

```xml
<cache
  eviction="FIFO"
  flushInterval="60000"
  size="512"
  readOnly="true"/>
```
这个更高级的配置创建了一个 FIFO 缓存,并每隔 60 秒刷新,存数结果对象或列表的 512 个引用,而且返回的对象被认为是只读的,因此在不同线程中的调用者之间修改它们会 导致冲突。

可用的收回策略有:

- ``LRU`` – 最近最少使用的:移除最长时间不被使用的对象。
- ``FIFO`` – 先进先出:按对象进入缓存的顺序来移除它们。
- ``SOFT`` – 软引用:移除基于垃圾回收器状态和软引用规则的对象。
- ``WEAK`` – 弱引用:更积极地移除基于垃圾收集器状态和弱引用规则的对象。
默认的是 LRU。

``flushInterval``(刷新间隔)可以被设置为任意的正整数,而且它们代表一个合理的毫秒 形式的时间段。默认情况是不设置,也就是没有刷新间隔,缓存仅仅调用语句时刷新。

``size``(引用数目)可以被设置为任意正整数,要记住你缓存的对象数目和你运行环境的 可用内存资源数目。默认值是 1024。

``readOnly``(只读)属性可以被设置为 true 或 false。只读的缓存会给所有调用者返回缓 存对象的相同实例。因此这些对象不能被修改。这提供了很重要的性能优势。可读写的缓存 会返回缓存对象的拷贝(通过序列化) 。这会慢一些,但是安全,因此默认是 false。

### 2.7.1. 使用自定义缓存
除了这些自定义缓存的方式, 你也可以通过实现你自己的缓存或为其他第三方缓存方案 创建适配器来完全覆盖缓存行为。
```xml
<cache type="com.domain.something.MyCustomCache"/>
```
这个示 例展 示了 如何 使用 一个 自定义 的缓 存实 现。type 属 性指 定的 类必 须实现 org.mybatis.cache.Cache 接口。这个接口是 MyBatis 框架中很多复杂的接口之一,但是简单 给定它做什么就行。
```java
public interface Cache {
  String getId();
  int getSize();
  void putObject(Object key, Object value);
  Object getObject(Object key);
  boolean hasKey(Object key);
  Object removeObject(Object key);
  void clear();
}
```
要配置你的缓存, 简单和公有的 JavaBeans 属性来配置你的缓存实现, 而且是通过 cache 元素来传递属性, 比如, 下面代码会在你的缓存实现中调用一个称为 “setCacheFile(String file)” 的方法:
```xml
<cache type="com.domain.something.MyCustomCache">
  <property name="cacheFile" value="/tmp/my-custom-cache.tmp"/>
</cache>
```
你可以使用所有简单类型作为 JavaBeans 的属性,MyBatis 会进行转换。 And you can specify a placeholder(e.g. ``${cache.file}``) to replace value defined at configuration properties.

从3.4.2版本开始，MyBatis已经支持在所有属性设置完毕以后可以调用一个初始化方法。如果你想要使用这个特性，请在你的自定义缓存类里实现 ``org.apache.ibatis.builder.InitializingObject`` 接口。
```java
public interface InitializingObject {
  void initialize() throws Exception;
}
```
记得缓存配置和缓存实例是绑定在 SQL 映射文件的命名空间是很重要的。因此,所有 在相同命名空间的语句正如绑定的缓存一样。 语句可以修改和缓存交互的方式, 或在语句的 语句的基础上使用两种简单的属性来完全排除它们。默认情况下,语句可以这样来配置:

```xml
<select ... flushCache="false" useCache="true"/>
<insert ... flushCache="true"/>
<update ... flushCache="true"/>
<delete ... flushCache="true"/>
```
因为那些是默认的,你明显不能明确地以这种方式来配置一条语句。相反,如果你想改 变默认的行为,只能设置 flushCache 和 useCache 属性。比如,在一些情况下你也许想排除 从缓存中查询特定语句结果,或者你也许想要一个查询语句来刷新缓存。相似地,你也许有 一些更新语句依靠执行而不需要刷新缓存。
### 2.7.2. 参照缓存
回想一下上一节内容, 这个特殊命名空间的唯一缓存会被使用或者刷新相同命名空间内 的语句。也许将来的某个时候,你会想在命名空间中共享相同的缓存配置和实例。在这样的 情况下你可以使用 cache-ref 元素来引用另外一个缓存。

```xml
<cache-ref namespace="com.someone.application.data.SomeMapper"/>
```

# 3. 动态SQL
MyBatis 的强大特性之一便是它的动态 SQL。如果你有使用 JDBC 或其它类似框架的经验，你就能体会到根据不同条件拼接 SQL 语句的痛苦。例如拼接时要确保不能忘记添加必要的空格，还要注意去掉列表最后一个列名的逗号。利用动态 SQL 这一特性可以彻底摆脱这种痛苦。

     
虽然在以前使用动态 SQL 并非一件易事，但正是 MyBatis 提供了可以被用在任意 SQL 映射语句中的强大的动态 SQL 语言得以改进这种情形。

     
动态 SQL 元素和 JSTL 或基于类似 XML 的文本处理器相似。在 MyBatis 之前的版本中，有很多元素需要花时间了解。MyBatis 3 大大精简了元素种类，现在只需学习原来一半的元素便可。MyBatis 采用功能强大的基于 OGNL 的表达式来淘汰其它大部分元素。

- if
- choose (when, otherwise)
- trim (where, set)
- foreach

## 3.1. if
动态 SQL 通常要做的事情是根据条件包含 where 子句的一部分。比如：
```xml
<select id="findActiveBlogWithTitleLike"
     resultType="Blog">
  SELECT * FROM BLOG 
  WHERE state = ‘ACTIVE’ 
  <if test="title != null">
    AND title like #{title}
  </if>
</select>
```
       
这条语句提供了一种可选的查找文本功能。如果没有传入“title”，那么所有处于“ACTIVE”状态的BLOG都会返回；反之若传入了“title”，那么就会对“title”一列进行模糊查找并返回 BLOG 结果（细心的读者可能会发现，“title”参数值是可以包含一些掩码或通配符的）。

       
如果希望通过“title”和“author”两个参数进行可选搜索该怎么办呢？首先，改变语句的名称让它更具实际意义；然后只要加入另一个条件即可。

```xml
<select id="findActiveBlogLike"
     resultType="Blog">
  SELECT * FROM BLOG WHERE state = ‘ACTIVE’ 
  <if test="title != null">
    AND title like #{title}
  </if>
  <if test="author != null and author.name != null">
    AND author_name like #{author.name}
  </if>
</select>
```
## 3.2. choose, when, otherwise
有时我们不想应用到所有的条件语句，而只想从中择其一项。针对这种情况，MyBatis 提供了 choose 元素，它有点像 Java 中的 switch 语句。

还是上面的例子，但是这次变为提供了“title”就按“title”查找，提供了“author”就按“author”查找的情形，若两者都没有提供，就返回所有符合条件的 BLOG（实际情况可能是由管理员按一定策略选出 BLOG 列表，而不是返回大量无意义的随机结果）。

```xml
<select id="findActiveBlogLike"
     resultType="Blog">
  SELECT * FROM BLOG WHERE state = ‘ACTIVE’
  <choose>
    <when test="title != null">
      AND title like #{title}
    </when>
    <when test="author != null and author.name != null">
      AND author_name like #{author.name}
    </when>
    <otherwise>
      AND featured = 1
    </otherwise>
  </choose>
</select> 
```
## 3.3. trim, where, set
前面几个例子已经合宜地解决了一个臭名昭著（︶^︶）的动态 SQL 问题。现在回到“if”示例，这次我们将“ACTIVE = 1”也设置成动态的条件，看看会发生什么。
```xml
<select id="findActiveBlogLike"
     resultType="Blog">
  SELECT * FROM BLOG 
  WHERE 
  <if test="state != null">
    state = #{state}
  </if> 
  <if test="title != null">
    AND title like #{title}
  </if>
  <if test="author != null and author.name != null">
    AND author_name like #{author.name}
  </if>
</select>
```
如果这些条件没有一个能匹配上会发生什么？最终这条 SQL 会变成这样：

```sql
SELECT * FROM BLOG
WHERE
```
这会导致查询失败。如果仅仅第二个条件匹配又会怎样？这条 SQL 最终会是这样:

```sql
SELECT * FROM BLOG
WHERE 
AND title like ‘someTitle’
```
       
这个查询也会失败。这个问题不能简单地用条件句式来解决，如果你也曾经被迫这样写过，那么你很可能从此以后都不会再写出这种语句了。

       
MyBatis 有一个简单的处理，这在 90% 的情况下都会有用。而在不能使用的地方，你可以自定义处理方式来令其正常工作。一处简单的修改就能达到目的：

```xml
<select id="findActiveBlogLike"
     resultType="Blog">
  SELECT * FROM BLOG 
  <where> 
    <if test="state != null">
         state = #{state}
    </if> 
    <if test="title != null">
        AND title like #{title}
    </if>
    <if test="author != null and author.name != null">
        AND author_name like #{author.name}
    </if>
  </where>
</select>
```
where 元素只会在至少有一个子元素的条件返回 SQL 子句的情况下才去插入“WHERE”子句。而且，若语句的开头为“AND”或“OR”，where 元素也会将它们去除。

如果 where 元素没有按正常套路出牌，我们可以通过自定义 trim 元素来定制 where 元素的功能。比如，和 where 元素等价的自定义 trim 元素为：
```xml
<trim prefix="WHERE" prefixOverrides="AND |OR ">
  ... 
</trim>
```
prefixOverrides 属性会忽略通过管道分隔的文本序列（"AND``口``|OR``口`` "注意此例中的空格也是必要的）。它的作用是移除所有指定在 prefixOverrides 属性中的内容，并且插入 prefix 属性中指定的内容。

       
类似的用于动态更新语句的解决方案叫做 set。set 元素可以用于动态包含需要更新的列，而舍去其它的。比如：
```xml
<update id="updateAuthorIfNecessary">
  update Author
    <set>
      <if test="username != null">username=#{username},</if>
      <if test="password != null">password=#{password},</if>
      <if test="email != null">email=#{email},</if>
      <if test="bio != null">bio=#{bio}</if>
    </set>
  where id=#{id}
</update>
```
       
这里，set 元素会动态前置 SET 关键字，同时也会删掉无关的逗号，因为用了条件语句之后很可能就会在生成的 SQL 语句的后面留下这些逗号。（译者注：因为用的是“if”元素，若最后一个“if”没有匹配上而前面的匹配上，SQL 语句的最后就会有一个逗号遗留）

       
若你对 set 元素等价的自定义 trim 元素的代码感兴趣，那这就是它的真面目：

```xml
<trim prefix="SET" suffixOverrides=",">
  ...
</trim>
```
       
注意这里我们删去的是后缀值，同时添加了前缀值。
## 3.4. foreach
动态 SQL 的另外一个常用的操作需求是对一个集合进行遍历，通常是在构建 IN 条件语句的时候。比如：
```xml
<select id="selectPostIn" resultType="domain.blog.Post">
  SELECT *
  FROM POST P
  WHERE ID in
  <foreach item="item" index="index" collection="list" open="(" separator="," close=")">
        #{item}
  </foreach>
</select>
```
foreach 元素的功能非常强大，它允许你指定一个集合，声明可以在元素体内使用的集合项（item）和索引（index）变量。它也允许你指定开头与结尾的字符串以及在迭代结果之间放置分隔符。这个元素是很智能的，因此它不会偶然地附加多余的分隔符。

       
``注意`` 你可以将任何可迭代对象（如 List、Set 等）、Map 对象或者数组对象传递给 foreach 作为集合参数。当使用可迭代对象或者数组时，index 是当前迭代的次数，item 的值是本次迭代获取的元素。当使用 Map 对象（或者 Map.Entry 对象的集合）时，index 是键，item 是值。

到此我们已经完成了涉及 XML 配置文件和 XML 映射文件的讨论。下一章将详细探讨 Java API，这样就能提高已创建的映射文件的利用效率。
## 3.5. bind
``bind`` 元素可以从 OGNL 表达式中创建一个变量并将其绑定到上下文。比如：
```xml
<select id="selectBlogsLike" resultType="Blog">
  <bind name="pattern" value="'%' + _parameter.getTitle() + '%'" />
  SELECT * FROM BLOG
  WHERE title LIKE #{pattern}
</select>
```
## 3.6. 多数据库支持
一个配置了“_databaseId”变量的 databaseIdProvider 可用于动态代码中，这样就可以根据不同的数据库厂商构建特定的语句。比如下面的例子：
```xml
<insert id="insert">
  <selectKey keyProperty="id" resultType="int" order="BEFORE">
    <if test="_databaseId == 'oracle'">
      select seq_users.nextval from dual
    </if>
    <if test="_databaseId == 'db2'">
      select nextval for seq_users from sysibm.sysdummy1"
    </if>
  </selectKey>
  insert into users values (#{id}, #{name})
</insert>
```

## 3.7. 动态 SQL 中的可插拔脚本语言
MyBatis 从 3.2 开始支持可插拔脚本语言，这允许你插入一种脚本语言驱动，并基于这种语言来编写动态 SQL 查询语句。

       
可以通过实现以下接口来插入一种语言：

```java
public interface LanguageDriver {
  ParameterHandler createParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql);
  SqlSource createSqlSource(Configuration configuration, XNode script, Class<?> parameterType);
  SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType);
}
```
       
一旦设定了自定义语言驱动，你就可以在 mybatis-config.xml 文件中将它设置为默认语言：

```xml
<typeAliases>
  <typeAlias type="org.sample.MyLanguageDriver" alias="myLanguage"/>
</typeAliases>
<settings>
  <setting name="defaultScriptingLanguage" value="myLanguage"/>
</settings>
```
除了设置默认语言，你也可以针对特殊的语句指定特定语言，可以通过如下的 ``lang`` 属性来完成：

```xml
<select id="selectBlog" lang="myLanguage">
  SELECT * FROM BLOG
</select>
```
       
或者，如果你使用的是映射器接口类，在抽象方法上加上 ``@Lang`` 注解即可：

```java
public interface Mapper {
  @Lang(MyLanguageDriver.class)
  @Select("SELECT * FROM BLOG")
  List<Blog> selectBlog();
}
```
注意 可以将 Apache Velocity 作为动态语言来使用，更多细节请参考 MyBatis-Velocity 项目。

       
你前面看到的所有 ``xml`` 标签都是由默认 MyBatis 语言提供的，而它由别名为 xml 的语言驱动器 ``org.apache.ibatis.scripting.xmltags.XmlLanguageDriver`` 所提供。



# 4. 映射器注解
>``官方吐槽~~~///(^v^)\\\~~~``不幸的是，Java 注解的的表达力和灵活性十分有限。尽管很多时间都花在调查、设计和试验上，最强大的 MyBatis 映射并不能用注解来构建——并不是在开玩笑，的确是这样。比方说，C#属性就没有这些限制，因此 MyBatis.NET 将会比 XML 有更丰富的选择。也就是说，基于 Java 注解的配置离不开它的特性。
## 4.1. 参数
| 注解                                                                     | 使用对象 | 相对应的 XML                                                 | 描述                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       |
| :----------------------------------------------------------------------- | -------- | ------------------------------------------------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| @CacheNamespace                                                          | 类       | ``<cache>``                                                  | 为给定的命名空间（比如类）配置缓存。属性有：``implemetation``, ``eviction``, ``flushInterval``, ``size``, ``readWrite``, ``blocking`` 和``properties``。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
| @Property                                                                | N/A      | ``<property>``                                               | 指定参数值或占位值（placeholder）（能被 ``mybatis-config.xml``内的配置属性覆盖）。属性有：``name``, ``value``。（仅在MyBatis 3.4.2以上版本生效）                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           |
| @CacheNamespaceRef                                                       | 类       | ``<cacheRef>``                                               | 参照另外一个命名空间的缓存来使用。属性有：``value``, ``name``。如果你使用了这个注解，你应设置 ``value`` 或者 ``name`` 属性的其中一个。``value`` 属性用于指定 Java 类型而指定命名空间（命名空间名就是指定的 Java 类型的全限定名），``name`` 属性（这个属性仅在MyBatis 3.4.2以上版本生效）直接指定了命名空间的名字。                                                                                                                                                                                                                                                                                                                                                                                                                                                         |
| @ConstructorArgs                                                         | 方法     | ``<constructor>``                                            | 收集一组结果传递给一个结果对象的构造方法。属性有：``value``，它是形式参数数组。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| @Arg                                                                     | N/A      | ``<arg>``<br>``<idArg>``                                     | 单参数构造方法，是 ConstructorArgs 集合的一部分。属性有：``id``, ``column``, ``javaType``, ``jdbcType``, ``typeHandler``, ``select`` 和 ``resultMap``。id 属性是布尔值，来标识用于比较的属性，和``<idArg>`` XML 元素相似。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 |
| @TypeDiscriminator                                                       | 方法     | ``<discriminator>``                                          | 一组实例值被用来决定结果映射的表现。属性有：column, javaType, jdbcType, typeHandler 和 cases。cases 属性是实例数组。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       |
| @Case                                                                    | N/A      | ``<case>``                                                   | 单独实例的值和它对应的映射。属性有：value, type, results。results 属性是结果数组，因此这个注解和实际的 ResultMap 很相似，由下面的 Results 注解指定。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       |
| @Results                                                                 | 方法     | ``<resultMap>``                                              | 结果映射的列表，包含了一个特别结果列如何被映射到属性或字段的详情。属性有：``value``, ``id``。``value ``属性是 Result 注解的数组。这个 id 的属性是结果映射的名称。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          |
| @Result                                                                  | N/A      | ``<result>``<br>``<id>``                                     | 在列和属性或字段之间的单独结果映射。属性有：``id``, ``column``, ``javaType``, ``jdbcType``, ``typeHandler``, ``one``, ``many``。``id`` 属性是一个布尔值，来标识应该被用于比较（和在 XML 映射中的``<id>``相似）的属性。one 属性是单独的联系，和 ``<association>`` 相似，而 many 属性是对集合而言的，和``<collection>``相似。它们这样命名是为了避免名称冲突。                                                                                                                                                                                                                                                                                                                                                                                                                |
| @One                                                                     | N/A      | ``<association>``                                            | 复杂类型的单独属性值映射。属性有：``select``，已映射语句（也就是映射器方法）的全限定名，它可以加载合适类型的实例。``fetchType``会覆盖全局的配置参数 ``lazyLoadingEnabled``。<br>``注意`` 联合映射在注解 API中是不支持的。这是因为 Java 注解的限制,不允许循环引用。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         |
| @Many                                                                    | N/A      | ``<collection>``                                             | 映射到复杂类型的集合属性。属性有：``select``，已映射语句（也就是映射器方法）的全限定名，它可以加载合适类型的实例的集合，``fetchType`` 会覆盖全局的配置参数 ``lazyLoadingEnabled``。<br>``注意`` 联合映射在注解 API中是不支持的。这是因为 Java 注解的限制，不允许循环引用                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
| @MapKey                                                                  | 方法     | N/A                                                          | 这是一个用在返回值为 Map 的方法上的注解。它能够将存放对象的 List 转化为 key 值为对象的某一属性的 Map。属性有： ``value``，填入的是对象的属性名，作为 Map 的 key 值。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       |
| @Options                                                                 | 方法     | 映射语句的属性                                               | 这个注解提供访问大范围的交换和配置选项的入口，它们通常在映射语句上作为属性出现。``Options`` 注解提供了通俗易懂的方式来访问它们，而不是让每条语句注解变复杂。属性有：``useCache=true``, ``flushCache=FlushCachePolicy.DEFAULT``, ``resultSetType=FORWARD_ONLY``, ``statementType=PREPARED``, ``fetchSize=-1, timeout=-1``, ``useGeneratedKeys=false``, ``keyProperty="id"``, ``keyColumn=""``, ``resultSets=""``。值得一提的是， Java 注解无法指定 ``null`` 值。因此，一旦你使用了 ``Options`` 注解，你的语句就会被上述属性的默认值所影响。要注意避免默认值带来的预期以外的行为。<br> 注意： ``keyColumn`` 属性只在某些数据库中有效（如 Oracle、PostgreSQL等）。请在插入语句一节查看更多关于 ``keyColumn`` 和 ``keyProperty`` 两者的有效值详情。                            |
| @Insert <br>@Update <br>@Delete<br>@Select                               | 方法     | ``<insert>``<br>``<update>``<br>``<delete>``<br>``<select>`` | 这四个注解分别代表将会被执行的 SQL 语句。它们用字符串数组（或单个字符串）作为参数。如果传递的是字符串数组，字符串之间先会被填充一个空格再连接成单个完整的字符串。这有效避免了以 Java 代码构建 SQL 语句时的“丢失空格”的问题。然而，你也可以提前手动连接好字符串。属性有：``value``，填入的值是用来组成单个 SQL 语句的字符串数组。                                                                                                                                                                                                                                                                                                                                                                                                                                         |
| @InsertProvider<br>@UpdateProvider<br>@DeleteProvider<br>@SelectProvider | 方法     | ``<insert>``<br>``<update>``<br>``<delete>``<br>``<select>`` | 允许构建动态 SQL。这些备选的 SQL 注解允许你指定类名和返回在运行时执行的 SQL 语句的方法。（自从MyBatis 3.4.6开始，你可以用 ``CharSequence`` 代替 ``String`` 来返回类型返回值了。）当执行映射语句的时候，MyBatis 会实例化类并执行方法，类和方法就是填入了注解的值。你可以把已经传递给映射方法了的对象作为参数，"Mapper interface type" 和 "Mapper method" 会经过 ``ProviderContext`` （仅在MyBatis 3.4.5及以上支持）作为参数值。（MyBatis 3.4及以上的版本，支持多参数传入）属性有： ``type``, ``method``。``type`` 属性需填入类。method 需填入该类定义了的方法名。<br>注意 接下来的小节将会讨论类，能帮助你更轻松地构建动态 SQL。                                                                                                                                            |
| @Param                                                                   | 参数     | N/A                                                          | 如果你的映射方法的形参有多个，这个注解使用在映射方法的参数上就能为它们取自定义名字。若不给出自定义名字，多参数（不包括 RowBounds 参数）则先以 "param" 作前缀，再加上它们的参数位置作为参数别名。例如 ``#{param1}``, ``#{param2}``，这个是默认值。如果注解是 ``@Param("person")``，那么参数就会被命名为 ``#{person}``。                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
| @SelectKey                                                               | 方法     | ``<selectKey> ``                                             | 这个注解的功能与 ``<selectKey> ``标签完全一致，用在已经被 ``@Insert`` 或 ``@InsertProvider`` 或 ``@Update`` 或 ``@UpdateProvider`` 注解了的方法上。若在未被上述四个注解的方法上作 ``@SelectKey`` 注解则视为无效。如果你指定了 ``@SelectKey`` 注解，那么 MyBatis 就会忽略掉由 ``@Options`` 注解所设置的生成主键或设置（configuration）属性。属性有：``statement`` 填入将会被执行的 SQL 字符串数组，``keyProperty`` 填入将会被更新的参数对象的属性的值，``before`` 填入 ``true`` 或 ``false`` 以指明 SQL 语句应被在插入语句的之前还是之后执行。``resultType`` 填入 ``keyProperty`` 的 Java 类型和用 ``Statement``、 ``PreparedStatement`` 和 ``CallableStatement`` 中的 ``STATEMENT``、 ``PREPARED`` 或 ``CALLABLE`` 中任一值填入 ``statementType``。默认值是 ``PREPARED``。 |
| @ResultMap                                                               | 方法     | N/A                                                          | 这个注解给 ``@Select`` 或者 ``@SelectProvider`` 提供在 XML 映射中的 ``<resultMap>`` 的id。这使得注解的 select 可以复用那些定义在 XML 中的 ResultMap。如果同一 select 注解中还存在 ``@Results`` 或者 ``@ConstructorArgs``，那么这两个注解将被此注解覆盖。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
| @ResultType                                                              | 方法     | N/A                                                          | 此注解在使用了结果处理器的情况下使用。在这种情况下，返回类型为 void，所以 Mybatis 必须有一种方式决定对象的类型，用于构造每行数据。如果有 XML 的结果映射，请使用 ``@ResultMap`` 注解。如果结果类型在 XML 的 ``<select>`` 节点中指定了，就不需要其他的注解了。其他情况下则使用此注解。比如，如果 @Select 注解在一个将使用结果处理器的方法上，那么返回类型必须是 void 并且这个注解（或者@ResultMap）必选。这个注解仅在方法返回类型是 void 的情况下生效。                                                                                                                                                                                                                                                                                                                      |
| @Flush                                                                   | 方法     | N/A                                                          | 如果使用了这个注解，定义在 Mapper 接口中的方法能够调用 ``SqlSession#flushStatements()`` 方法。（Mybatis 3.3及以上）                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        |
## 4.2. 使用
这个例子展示了如何使用 @SelectKey 注解来在插入前读取数据库序列的值：
```java
@Insert("insert into table3 (id, name) values(#{nameId}, #{name})")
@SelectKey(statement="call next value for TestSequence", keyProperty="nameId", before=true, resultType=int.class)
int insertTable3(Name name);
```
这个例子展示了如何使用 @SelectKey 注解来在插入后读取数据库识别列的值：
```java
@Insert("insert into table2 (name) values(#{name})")
@SelectKey(statement="call identity()", keyProperty="nameId", before=false, resultType=int.class)
int insertTable2(Name name);
```

这个例子展示了如何使用 @Flush 注解去调用 SqlSession#flushStatements()：
```java
@Flush
List<BatchResult> flush();
```
这些例子展示了如何通过指定 @Result 的 id 属性来命名结果集：
```java
@Results(id = "userResult", value = {
  @Result(property = "id", column = "uid", id = true),
  @Result(property = "firstName", column = "first_name"),
  @Result(property = "lastName", column = "last_name")
})
@Select("select * from users where id = #{id}")
User getUserById(Integer id);

@Results(id = "companyResults")
@ConstructorArgs({
  @Arg(property = "id", column = "cid", id = true),
  @Arg(property = "name", column = "name")
})
@Select("select * from company where id = #{id}")
Company getCompanyById(Integer id);
```
这个例子展示了单一参数使用 @SqlProvider 注解：
```java
@SelectProvider(type = UserSqlBuilder.class, method = "buildGetUsersByName")
List<User> getUsersByName(String name);

class UserSqlBuilder {
  public static String buildGetUsersByName(final String name) {
    return new SQL(){{
      SELECT("*");
      FROM("users");
      if (name != null) {
        WHERE("name like #{value} || '%'");
      }
      ORDER_BY("id");
    }}.toString();
  }
}
```
这个例子展示了多参数使用 @SqlProvider 注解：
```java
@SelectProvider(type = UserSqlBuilder.class, method = "buildGetUsersByName")
List<User> getUsersByName(
    @Param("name") String name, @Param("orderByColumn") String orderByColumn);

class UserSqlBuilder {

  // If not use @Param, you should be define same arguments with mapper method
  public static String buildGetUsersByName(
      final String name, final String orderByColumn) {
    return new SQL(){{
      SELECT("*");
      FROM("users");
      WHERE("name like #{name} || '%'");
      ORDER_BY(orderByColumn);
    }}.toString();
  }

  // If use @Param, you can define only arguments to be used
  public static String buildGetUsersByName(@Param("orderByColumn") final String orderByColumn) {
    return new SQL(){{
      SELECT("*");
      FROM("users");
      WHERE("name like #{name} || '%'");
      ORDER_BY(orderByColumn);
    }}.toString();
  }
}
```


# 5. SQL语句构建器类
## 5.1. 问题
Java程序员面对的最痛苦的事情之一就是在Java代码中嵌入SQL语句。这么来做通常是由于SQL语句需要动态来生成-否则可以将它们放到外部文件或者存储过程中。正如你已经看到的那样，MyBatis在它的XML映射特性中有一个强大的动态SQL生成方案。但有时在Java代码内部创建SQL语句也是必要的。此时，MyBatis有另外一个特性可以帮到你，在减少典型的加号,引号,新行,格式化问题和嵌入条件来处理多余的逗号或 AND 连接词之前。事实上，在Java代码中来动态生成SQL代码就是一场噩梦。例如：
```java
String sql = "SELECT P.ID, P.USERNAME, P.PASSWORD, P.FULL_NAME, "
"P.LAST_NAME,P.CREATED_ON, P.UPDATED_ON " +
"FROM PERSON P, ACCOUNT A " +
"INNER JOIN DEPARTMENT D on D.ID = P.DEPARTMENT_ID " +
"INNER JOIN COMPANY C on D.COMPANY_ID = C.ID " +
"WHERE (P.ID = A.ID AND P.FIRST_NAME like ?) " +
"OR (P.LAST_NAME like ?) " +
"GROUP BY P.ID " +
"HAVING (P.LAST_NAME like ?) " +
"OR (P.FIRST_NAME like ?) " +
"ORDER BY P.ID, P.FULL_NAME";
```
## 5.2. The Solution
MyBatis 3提供了方便的工具类来帮助解决该问题。使用SQL类，简单地创建一个实例来调用方法生成SQL语句。上面示例中的问题就像重写SQL类那样：
```java
private String selectPersonSql() {
  return new SQL() {{
    SELECT("P.ID, P.USERNAME, P.PASSWORD, P.FULL_NAME");
    SELECT("P.LAST_NAME, P.CREATED_ON, P.UPDATED_ON");
    FROM("PERSON P");
    FROM("ACCOUNT A");
    INNER_JOIN("DEPARTMENT D on D.ID = P.DEPARTMENT_ID");
    INNER_JOIN("COMPANY C on D.COMPANY_ID = C.ID");
    WHERE("P.ID = A.ID");
    WHERE("P.FIRST_NAME like ?");
    OR();
    WHERE("P.LAST_NAME like ?");
    GROUP_BY("P.ID");
    HAVING("P.LAST_NAME like ?");
    OR();
    HAVING("P.FIRST_NAME like ?");
    ORDER_BY("P.ID");
    ORDER_BY("P.FULL_NAME");
  }}.toString();
}
```
该例中有什么特殊之处？当你仔细看时，那不用担心偶然间重复出现的"AND"关键字，或者在"WHERE"和"AND"之间的选择，抑或什么都不选。该SQL类非常注意"WHERE"应该出现在何处，哪里又应该使用"AND"，还有所有的字符串链接。
## 5.3. SQL类
这里给出一些示例：
```java
// Anonymous inner class
public String deletePersonSql() {
  return new SQL() {{
    DELETE_FROM("PERSON");
    WHERE("ID = #{id}");
  }}.toString();
}

// Builder / Fluent style
public String insertPersonSql() {
  String sql = new SQL()
    .INSERT_INTO("PERSON")
    .VALUES("ID, FIRST_NAME", "#{id}, #{firstName}")
    .VALUES("LAST_NAME", "#{lastName}")
    .toString();
  return sql;
}

// With conditionals (note the final parameters, required for the anonymous inner class to access them)
public String selectPersonLike(final String id, final String firstName, final String lastName) {
  return new SQL() {{
    SELECT("P.ID, P.USERNAME, P.PASSWORD, P.FIRST_NAME, P.LAST_NAME");
    FROM("PERSON P");
    if (id != null) {
      WHERE("P.ID like #{id}");
    }
    if (firstName != null) {
      WHERE("P.FIRST_NAME like #{firstName}");
    }
    if (lastName != null) {
      WHERE("P.LAST_NAME like #{lastName}");
    }
    ORDER_BY("P.LAST_NAME");
  }}.toString();
}

public String deletePersonSql() {
  return new SQL() {{
    DELETE_FROM("PERSON");
    WHERE("ID = #{id}");
  }}.toString();
}

public String insertPersonSql() {
  return new SQL() {{
    INSERT_INTO("PERSON");
    VALUES("ID, FIRST_NAME", "#{id}, #{firstName}");
    VALUES("LAST_NAME", "#{lastName}");
  }}.toString();
}Z

public String updatePersonSql() {
  return new SQL() {{
    UPDATE("PERSON");
    SET("FIRST_NAME = #{firstName}");
    WHERE("ID = #{id}");
  }}.toString();
}
```
| 方法                                                                                                                                                                                               | 描述                                                                                                                                                                                                       |
| :------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| SELECT(String) <br>SELECT(String...)                                                                                                                                                               | 开始或插入到 ``SELECT``子句。 可以被多次调用，参数也会添加到 ``SELECT``子句。 参数通常使用逗号分隔的列名和别名列表，但也可以是数据库驱动程序接受的任意类型。                                               |
| SELECT_DISTINCT(String)<br>SELECT_DISTINCT(String...)                                                                                                                                              | 开始或插入到 ``SELECT``子句， 也可以插入 DISTINCT关键字到生成的查询语句中。 可以被多次调用，参数也会添加到 ``SELECT``子句。 参数通常使用逗号分隔的列名和别名列表，但也可以是数据库驱动程序接受的任意类型。 |
| FROM(String)<br>FROM(String...)                                                                                                                                                                    | 开始或插入到 ``FROM``子句。 可以被多次调用，参数也会添加到 ``FROM``子句。 参数通常是表名或别名，也可以是数据库驱动程序接受的任意类型。                                                                     |
| JOIN(String)<br>JOIN(String...)<br>INNER_JOIN(String)<br>INNER_JOIN(String...)<br>LEFT_OUTER_JOIN(String)<br>LEFT_OUTER_JOIN(String...)<br>RIGHT_OUTER_JOIN(String)<br>RIGHT_OUTER_JOIN(String...) | 基于调用的方法，添加新的合适类型的 ``JOIN``子句。 参数可以包含由列命和join on条件组合成标准的join。                                                                                                        |
| WHERE(String)<br>WHERE(String...)                                                                                                                                                                  | 插入新的 ``WHERE``子句条件， 由``AND``链接。可以多次被调用，每次都由``AND``来链接新条件。使用 ``OR()`` 来分隔``OR``。                                                                                      |
| AND()                                                                                                                                                                                              | 使用``AND``来分隔当前的 ``WHERE``子句条件。 可以被多次调用，但在一行中多次调用或生成不稳定的``SQL``。因为 ``WHERE`` 和 ``HAVING`` 二者都会自动链接 ``AND``, 这是非常罕见的方法，只是为了完整性才被使用。   |
| GROUP_BY(String)<br>GROUP_BY(String...)                                                                                                                                                            | 插入新的 ``GROUP BY``子句元素，由逗号连接。 可以被多次调用，每次都由逗号连接新的条件。                                                                                                                     |
| HAVING(String)<br>HAVING(String...)                                                                                                                                                                | 插入新的 ``HAVING``子句条件。 由AND连接。可以被多次调用，每次都由``AND``来连接新的条件。使用 ``OR()`` 来分隔``OR``.                                                                                        |
| ORDER_BY(String)<br>ORDER_BY(String...)                                                                                                                                                            | 插入新的 ``ORDER BY``子句元素， 由逗号连接。可以多次被调用，每次由逗号连接新的条件。                                                                                                                       |
| DELETE_FROM(String)                                                                                                                                                                                | 开始一个delete语句并指定需要从哪个表删除的表名。通常它后面都会跟着WHERE语句！                                                                                                                              |
| INSERT_INTO(String)                                                                                                                                                                                | 开始一个insert语句并指定需要插入数据的表名。后面都会跟着一个或者多个VALUES() or INTO_COLUMNS() and INTO_VALUES()。                                                                                         |
| SET(String)<br>SET(String...)                                                                                                                                                                      | 针对update语句，插入到"set"列表中                                                                                                                                                                          |
| UPDATE(String)                                                                                                                                                                                     | 开始一个update语句并指定需要更新的表明。后面都会跟着一个或者多个SET()，通常也会有一个WHERE()。                                                                                                             |
| VALUES(String, String)                                                                                                                                                                             | 插入到insert语句中。第一个参数是要插入的列名，第二个参数则是该列的值。                                                                                                                                     |
| INTO_COLUMNS(String...)                                                                                                                                                                            | Appends columns phrase to an insert statement. This should be call INTO_VALUES() with together.                                                                                                            |
| INTO_VALUES(String...)                                                                                                                                                                             | Appends values phrase to an insert statement. This should be call INTO_COLUMNS() with together.                                                                                                            |
``Since version 3.4.2, you can use variable-length arguments as follows:``
```java
public String selectPersonSql() {
  return new SQL()
    .SELECT("P.ID", "A.USERNAME", "A.PASSWORD", "P.FULL_NAME", "D.DEPARTMENT_NAME", "C.COMPANY_NAME")
    .FROM("PERSON P", "ACCOUNT A")
    .INNER_JOIN("DEPARTMENT D on D.ID = P.DEPARTMENT_ID", "COMPANY C on D.COMPANY_ID = C.ID")
    .WHERE("P.ID = A.ID", "P.FULL_NAME like #{name}")
    .ORDER_BY("P.ID", "P.FULL_NAME")
    .toString();
}

public String insertPersonSql() {
  return new SQL()
    .INSERT_INTO("PERSON")
    .INTO_COLUMNS("ID", "FULL_NAME")
    .INTO_VALUES("#{id}", "#{fullName}")
    .toString();
}

public String updatePersonSql() {
  return new SQL()
    .UPDATE("PERSON")
    .SET("FULL_NAME = #{fullName}", "DATE_OF_BIRTH = #{dateOfBirth}")
    .WHERE("ID = #{id}")
    .toString();
}
```
## 5.4. SqlBuilder 和 SelectBuilder (已经废弃)
在3.2版本之前，我们使用了一点不同的做法，通过实现ThreadLocal变量来掩盖一些导致Java DSL麻烦的语言限制。但这种方式已经废弃了，现代的框架都欢迎人们使用构建器类型和匿名内部类的想法。因此，SelectBuilder 和 SqlBuilder 类都被废弃了。

下面的方法仅仅适用于废弃的SqlBuilder 和 SelectBuilder 类。
| 方法               | 描述                                                                                                                                                                                                              |
| :----------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| BEGIN()<br>RESET() | 这些方法清空SelectBuilder类的ThreadLocal状态，并且准备一个新的构建语句。开始新的语句时， BEGIN()读取得最好。 由于一些原因（在某些条件下，也许是逻辑需要一个完全不同的语句），在执行中清理语句 RESET()读取得最好。 |
| SQL()              | 返回生成的 SQL() 并重置 SelectBuilder 状态 (好像 BEGIN() 或 RESET() 被调用了). 因此，该方法只能被调用一次！                                                                                                       |

SelectBuilder 和 SqlBuilder 类并不神奇，但是知道它们如何工作也是很重要的。 SelectBuilder 使用 SqlBuilder 使用了静态导入和ThreadLocal变量的组合来开启整洁语法，可以很容易地和条件交错。使用它们，静态导入类的方法即可，就像这样(一个或其它，并非两者):

```java
import static org.apache.ibatis.jdbc.SelectBuilder.*;
```
```java
import static org.apache.ibatis.jdbc.SqlBuilder.*;
```
这就允许像下面这样来创建方法：
```java
/* DEPRECATED */
public String selectBlogsSql() {
  BEGIN(); // Clears ThreadLocal variable
  SELECT("*");
  FROM("BLOG");
  return SQL();
}
```
        
```java
/* DEPRECATED */
private String selectPersonSql() {
  BEGIN(); // Clears ThreadLocal variable
  SELECT("P.ID, P.USERNAME, P.PASSWORD, P.FULL_NAME");
  SELECT("P.LAST_NAME, P.CREATED_ON, P.UPDATED_ON");
  FROM("PERSON P");
  FROM("ACCOUNT A");
  INNER_JOIN("DEPARTMENT D on D.ID = P.DEPARTMENT_ID");
  INNER_JOIN("COMPANY C on D.COMPANY_ID = C.ID");
  WHERE("P.ID = A.ID");
  WHERE("P.FIRST_NAME like ?");
  OR();
  WHERE("P.LAST_NAME like ?");
  GROUP_BY("P.ID");
  HAVING("P.LAST_NAME like ?");
  OR();
  HAVING("P.FIRST_NAME like ?");
  ORDER_BY("P.ID");
  ORDER_BY("P.FULL_NAME");
  return SQL();
}
```
# 6. 关系映射

## 6.1. OneToOne
### 6.1.1. table & bean & dao
```sql
-- tb_card
CREATE TABLE tb_card(id INT PRIMARY KEY AUTO_INCREMENT,CODE VARCHAR(18));
INSERT INTO tb_card(CODE) VALUES('432801198009191038');
-- tb_person
CREATE TABLE tb_person(id INT PRIMARY KEY AUTO_INCREMENT,NAME VARCHAR(18),sex VARCHAR(18),age INT,card_id INT UNIQUE,FOREIGN KEY (card_id) REFERENCES tb_card(id));
INSERT INTO tb_person(NAME,sex,age,card_id) VALUES('jack','男',23,1)

```
```java
public class Card implements Serializable {

    private Integer id;  // 主键id
    private String code; // 身份证编号
    // get/set ...
}

public class Person implements Serializable {
    private Integer id;  // 主键id
    private String name; // 姓名
    private String sex;  // 性别
    private Integer age; // 年龄

    // 人和身份证是一对一的关系，即一个人只有一个身份证
    private Card card; 
     // get/set ...
}
```
```java
public interface PersonMapper {

    /**
     * 根据id查询Person
     * 方法名和参数必须和XML文件中的<select.../>元素的id属性和parameterType属性一致
     * @param id
     * @return Person对象
     * */
    Person selectPersonById(Integer id);
}
```

### 6.1.2. xml

>CardMapper
```xml
    <!-- 根据id查询Card，返回Card对象 -->
    <select id="selectCardById" parameterType="int"resultType="org.fkit.domain.Card">
        SELECT * from tb_card where id = #{id} 
    </select>
```
>personMapper
```xml
<!-- 根据id查询Person，返回resultMap -->
<select id="selectPersonById" parameterType="int" resultMap="personMapper">
    SELECT * from tb_person where id = #{id} 
</select>

<!-- 映射Peson对象的resultMap -->
<resultMap type="org.fkit.domain.Person" id="personMapper">
    <id property="id" column="id"/>
    <result property="name" column="name"/>
    <result property="sex" column="sex"/>
    <result property="age" column="age"/>
    <!-- 一对一关联映射:association   -->
    <association property="card" column="card_id" select="org.fkit.mapper.CardMapper.selectCardById"  javaType="org.fkit.domain.Card"/>
</resultMap>
```
### 6.1.3. 注解
>CardMapper
```java
public interface CardMapper {

    @Select("SELECT * FROM TB_CARD WHERE ID = #{id} ")
    Card selectCardById(Integer id);

}
```
>PersonMapper
```java
public interface PersonMapper {

@Select("SELECT * FROM TB_PERSON WHERE ID = #{id}")
@Results({
    @Result(id=true,column="id",property="id"),
    @Result(column="name",property="name"),
    @Result(column="sex",property="sex"),
    @Result(column="age",property="age"),
    @Result(column="card_id",property="card",
    one=@One(
            select="org.fkit.mapper.CardMapper.selectCardById",
            fetchType=FetchType.EAGER))
})
Person selectPersonById(Integer id);
```



## 6.2. OneToMany
### 6.2.1. table & bean &dao
```sql
-- tb_clazz
REATE TABLE tb_clazz(id INT PRIMARY KEY AUTO_INCREMENT,CODE VARCHAR(18),NAME VARCHAR(18));
INSERT INTO tb_clazz(CODE,NAME) VALUES('15gj2','15高计2');

-- tb_student
CREATE TABLE tb_student(id INT PRIMARY KEY AUTO_INCREMENT,NAME VARCHAR(18),sex VARCHAR(18),age INT,clazz_id INT,FOREIGN KEY (clazz_id) REFERENCES tb_clazz(id));
INSERT INTO tb_student(NAME,sex,age,clazz_id) VALUES('xiaoWei','男',21,1);
INSERT INTO tb_student(NAME,sex,age,clazz_id) VALUES('qiqi','女',18,1);
INSERT INTO tb_student(NAME,sex,age,clazz_id) VALUES('tuzaoGou','男',21,1);
INSERT INTO tb_student(NAME,sex,age,clazz_id) VALUES('ciDaBian','女',30,1);
```

```java
public class Clazz implements Serializable {

    private Integer id; // 班级id，主键
    private String code; // 班级编号
    private String name; // 班级名称

    // 班级和学生是一对多的关系，即一个班级可以有多个学生
    private List<Student> students;
}

public class Student implements Serializable {

    private Integer id; // 学生id，主键
    private String name; // 姓名
    private String sex;  // 性别
    private Integer age; // 年龄

    // 学生和班级是多对一的关系，即一个学生只属于一个班级
    private Clazz clazz;
}
```
### 6.2.2. xml
>ClazzMapper
```xml
<mapper namespace="org.fkit.mapper.ClazzMapper">

    <!-- 根据id查询班级信息，返回resultMap -->
    <select id="selectClazzById" parameterType="int" resultMap="clazzResultMap">
        SELECT * FROM tb_clazz  WHERE id = #{id}
    </select>

    <!-- 映射Clazz对象的resultMap -->
    <resultMap type="org.fkit.domain.Clazz" id="clazzResultMap">
        <id property="id" column="id"/>
        <result property="code" column="code"/>
        <result property="name" column="name"/>
        <!-- 一对多关联映射:collection fetchType="lazy"表示懒加载  -->
        <collection property="students" javaType="ArrayList" column="id" ofType="org.fkit.domain.Student" 
        select="org.fkit.mapper.StudentMapper.selectStudentByClazzId" fetchType="lazy">
            <id property="id" column="id"/>
            <result property="name" column="name"/>
            <result property="sex" column="sex"/>
            <result property="age" column="age"/>
        </collection>
    </resultMap>
</mapper>

```
>StudentMapper
```xml
<mapper namespace="org.fkit.mapper.StudentMapper">

    <!-- 根据id查询学生信息，多表连接，返回resultMap -->
    <select id="selectStudentById" parameterType="int" resultMap="studentResultMap">
        SELECT * FROM tb_clazz c,tb_student s
        WHERE c.id = s.clazz_id
        AND s.id = #{id}
    </select>

    <!-- 根据班级id查询学生信息，返回resultMap -->
    <select id="selectStudentByClazzId" parameterType="int" resultMap="studentResultMap">
        SELECT * FROM tb_student WHERE clazz_id = #{id}
    </select>

    <!-- 映射Student对象的resultMap -->
    <resultMap type="org.fkit.domain.Student" id="studentResultMap">
        <id property="id" column="id"/>
        <result property="name" column="name"/>
        <result property="sex" column="sex"/>
        <result property="age" column="age"/>
        <!-- 多对一关联映射:association   -->
        <association property="clazz" javaType="org.fkit.domain.Clazz">
            <id property="id" column="id"/>
            <result property="code" column="code"/>
            <result property="name" column="name"/>
        </association>
    </resultMap>

</mapper>
```
### 6.2.3. 注解

>ClazzMapper
```java
public interface ClazzMapper {

    // 根据id查询班级信息
    @Select("SELECT * FROM TB_CLAZZ  WHERE ID = #{id}")
    @Results({
        @Result(id=true,column="id",property="id"),
        @Result(column="code",property="code"),
        @Result(column="name",property="name"),
        @Result(column="id",property="students", many=@Many(select="org.fkit.mapper.StudentMapper.selectByClazzId",fetchType=FetchType.LAZY))
    })
    Clazz selectById(Integer id);   
}
```
> StudentMapper
```java
public interface StudentMapper {

    // 根据班级id查询班级所有学生
    @Select("SELECT * FROM TB_STUDENT WHERE CLAZZ_ID = #{id}")
    @Results({
        @Result(id=true,column="id",property="id"),
        @Result(column="name",property="name"),
        @Result(column="sex",property="sex"),
        @Result(column="age",property="age")
    })
    List<Student> selectByClazzId(Integer clazz_id);

}
``` 
## 6.3. ManyToMany
### 6.3.1. table & bean & dao
```sql
-- tb_article
CREATE TABLE tb_article(id INT PRIMARY KEY AUTO_INCREMENT,NAME VARCHAR(18),price DOUBLE,remark VARCHAR(18));
INSERT INTO tb_article(NAME,price,remark) VALUES('Java从入门到放弃',111.8,'我亲爱的陈令老师');
INSERT INTO tb_article(NAME,price,remark) VALUES('3天学会Android开发',99.9,'年轻的宋正江老师');
INSERT INTO tb_article(NAME,price,remark) VALUES('SQL server从删库到跑路',59.9,'快要秃头的方杰老师');
INSERT INTO tb_article(NAME,price,remark) VALUES('不使用MVC模式的jsp开发',69.9,'唯一的女教师韩冰射手');

-- tb_order
CREATE TABLE tb_order(id INT PRIMARY KEY AUTO_INCREMENT,CODE VARCHAR(32),total DOUBLE,user_id INT,FOREIGN KEY (user_id) REFERENCES tb_user(id));
INSERT INTO tb_order(CODE,total,user_id) VALUES('6aa3fa359ff14619b77fab5990940123',388.6,1);
INSERT INTO tb_order(CODE,total,user_id) VALUES('6aa3fa359ff14619b77fab5990940456',217.8,1);

-- tb_user
CREATE TABLE tb_user(id INT PRIMARY KEY AUTO_INCREMENT,username VARCHAR(18),loginname VARCHAR(18),PASSWORD VARCHAR(18),phone VARCHAR(18),address VARCHAR(18));
INSERT INTO tb_user(username,loginname,PASSWORD,phone,address) VALUES('小魏','xiaowei','111111','15395777777','杭州');

-- tb_item
CREATE TABLE tb_item(order_id INT,article_id INT,amount INT,PRIMARY KEY(order_id,article_id),FOREIGN KEY (order_id) REFERENCES tb_order(id),FOREIGN KEY (article_id) REFERENCES tb_article(id));

INSERT INTO tb_item(order_id,article_id,amount) VALUES(1,1,1);
INSERT INTO tb_item(order_id,article_id,amount) VALUES(1,2,1);
INSERT INTO tb_item(order_id,article_id,amount) VALUES(1,3,2);
INSERT INTO tb_item(order_id,article_id,amount) VALUES(2,4,2);
INSERT INTO tb_item(order_id,article_id,amount) VALUES(2,1,1);
```

```java
public class Article implements Serializable {

    private Integer id;     // 商品id，主键
    private String name;    // 商品名称
    private Double price;   // 商品价格
    private String remark;  // 商品描述

    // 商品和订单是多对多的关系，即一种商品可以包含在多个订单中
    private List<Order> orders;
}


public class Order implements Serializable {

    private Integer id;  // 订单id，主键
    private String code;  // 订单编号
    private Double total; // 订单总金额

    // 订单和用户是多对一的关系，即一个订单只属于一个用户
    private User user;

    // 订单和商品是多对多的关系，即一个订单可以包含多种商品
    private List<Article> articles;
}

public class User implements Serializable{

    private Integer id;  // 用户id，主键
    private String username;  // 用户名
    private String loginname; // 登录名
    private String password;  // 密码
    private String phone;    // 联系电话
    private String address;  // 收货地址

    // 用户和订单是一对多的关系，即一个用户可以有多个订单
    private List<Order> orders;
}

```
```java
public interface OrderMapper {

    Order selectOrderById(int id);

}

public interface UserMapper {

    User selectUserById(int id);

}
```

### 6.3.2. xml
>ArticleMapper
```xml
<mapper namespace="org.fkit.mapper.ArticleMapper">

    <select id="selectArticleByOrderId" parameterType="int" resultType="org.fkit.domain.Article">
    SELECT * FROM tb_article WHERE id IN ( 
          SELECT article_id FROM tb_item WHERE order_id = #{id} 
      ) 
    </select>

</mapper>
```
>OrderMapper
```xml
<mapper namespace="org.fkit.mapper.OrderMapper">

    <resultMap type="org.fkit.domain.Order" id="orderResultMap">
        <id property="id" column="oid"/>
        <result property="code" column="code"/>
        <result property="total" column="total"/>
        <!-- 多对一关联映射:association   -->
        <association property="user" javaType="org.fkit.domain.User">
            <id property="id" column="id"/>
            <result property="username" column="username"/>
            <result property="loginname" column="loginname"/>
            <result property="password" column="password"/>
            <result property="phone" column="phone"/>
            <result property="address" column="address"/>
        </association>
        <!-- 多对多映射的关键:collection   -->
        <collection property="articles" javaType="ArrayList"
                    column="oid" ofType="org.fkit.domain.Article"
                    select="org.fkit.mapper.ArticleMapper.selectArticleByOrderId"
                    fetchType="lazy"
                    >
            <id property="id" column="id"/>
            <result property="name" column="name"/>
            <result property="price" column="price"/>
            <result property="remark" column="remark"/>
        </collection>
    </resultMap>

    <!-- 注意，如果查询出来的列同名，例如tb_user表的id和tb_order表的id都是id，同名，需要使用别名区分 -->
    <select id="selectOrderById" parameterType="int" resultMap="orderResultMap">
        SELECT u.*,o.id AS oid,CODE,total,user_id
        FROM tb_user u,tb_order o
        WHERE u.id = o.user_id
        AND o.id = #{id}
    </select>

    <!-- 根据userid查询订单 -->
    <select id="selectOrderByUserId" parameterType="int" resultType="org.fkit.domain.Order">
        SELECT * FROM tb_order WHERE user_id = #{id}
    </select>

</mapper>
```
>UserMapper
```xml
<mapper namespace="org.fkit.mapper.UserMapper">

  <resultMap type="org.fkit.domain.User" id="userResultMap">
        <id property="id" column="id"/>
        <result property="username" column="username"/>
        <result property="loginname" column="loginname"/>
        <result property="password" column="password"/>
        <result property="phone" column="phone"/>
        <result property="address" column="address"/>
        <!-- 一对多关联映射:collection   -->
        <collection property="orders" javaType="ArrayList" column="id" ofType="org.fkit.domain.User"
                    select="org.fkit.mapper.OrderMapper.selectOrderByUserId"
                    fetchType="lazy"
                    >
            <id property="id" column="id"/>
            <result property="code" column="code"/>
            <result property="total" column="total"/>
      </collection>
    </resultMap>

  <select id="selectUserById" parameterType="int" resultMap="userResultMap">
      SELECT * FROM tb_user  WHERE id = #{id}
  </select>

</mapper>
```
### 6.3.3. 注解
>ArticleMapper
```java
public interface ArticleMapper {

    @Select("SELECT * FROM tb_article WHERE id IN (SELECT article_id FROM tb_item WHERE order_id = #{id} ) ")
    List<Article> selectByOrderId(Integer order_id);

}
```
>OrderMapper
```java
public interface OrderMapper {

    @Select("SELECT * FROM TB_ORDER WHERE ID = #{id}")
    @Results({
        @Result(id=true,column="id",property="id"),
        @Result(column="code",property="code"),
        @Result(column="total",property="total"),
        @Result(column="user_id",property="user", one=@One(select="org.fkit.mapper.UserMapper.selectById", fetchType=FetchType.EAGER)),
        @Result(column="id",property="articles", many=@Many(select="org.fkit.mapper.ArticleMapper.selectByOrderId", fetchType=FetchType.LAZY))
    })
    Order selectById(Integer id);

}
```
>UserMapper
```java
public interface UserMapper {

    @Select("SELECT * FROM TB_USER WHERE ID = #{id} ")
    User selectById(Integer id);

}
```
# 7. 插件 // TODO
## 7.1. 插件接口
## 7.2. 插件初始化
## 7.3. 插件的的代理和反射技术
## 7.4. 插件的工具类--MetaObject
## 7.5. 插件开发的过程和实例
### 7.5.1. 需要拦截的签名
### 7.5.2. 实现拦截的方法
### 7.5.3. 配置和运行
### 7.5.4. 插件的实例
## 7.6. 分页插件

# 8. MyBatis-Plus

| 润物无声                                                     | 效率至上                                                 | 丰富功能                                         |
| :----------------------------------------------------------- | -------------------------------------------------------- | ------------------------------------------------ |
| 只做增强不做改变，引入它不会对现有工程产生影响，如丝般顺滑。 | 只需简单配置，即可快速进行 CRUD 操作，从而节省大量时间。 | 热加载、代码生成、分页、性能分析等功能一应俱全。 |
## 8.1. 安装
### 8.1.1. Spring Boot
Maven：
```xml
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.0.3</version>
</dependency>
```

Gradle：
```java
compile group: 'com.baomidou', name: 'mybatis-plus-boot-starter', version: '3.0.3'
```

### 8.1.2. Spring MVC
Maven:
```xml
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus</artifactId>
    <version>3.0.3</version>
</dependency>
```
Gradle：

```java
compile group: 'com.baomidou', name: 'mybatis-plus', version: '3.0.3'
```
>WARNING<p>
>- Spring Boot和spring MVC版本的差异
>- 引入 MyBatis-Plus 之后请不要再次引入 MyBatis 以及 MyBatis-Spring，以避免因版本差异导致的问题。坑壁之一😭


## 8.2. 快速开始
我们将通过一个简单的 Demo 来阐述 MyBatis-Plus 的强大功能，在此之前，我们假设您已经：

- 拥有 Java 开发环境以及相应 IDE
- 熟悉 Spring Boot
- 熟悉 Maven
- MySQL数据库
  
现有一张 User 表：
```sql
DROP TABLE IF EXISTS user;

CREATE TABLE user
(
	id BIGINT(20) NOT NULL COMMENT '主键ID',
	name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
	age INT(11) NULL DEFAULT NULL COMMENT '年龄',
	email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
	PRIMARY KEY (id)
);


DELETE FROM user;

INSERT INTO user (id, name, age, email) VALUES
(1, 'Jone', 18, 'test1@baomidou.com'),
(2, 'Jack', 20, 'test2@baomidou.com'),
(3, 'Tom', 28, 'test3@baomidou.com'),
(4, 'Sandy', 21, 'test4@baomidou.com'),
(5, 'Billie', 24, 'test5@baomidou.com');
```
### 8.2.1. 初始化工程
创建一个空的 Spring Boot 工程（工程将以 MySQL 作为默认数据库进行演示）
>TIP<p>可以使用 [Spring Initializr](https://start.spring.io/). 快速初始化一个 Spring Boot 工程

### 8.2.2. 添加依赖
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.0.1</version>
        </dependency>
```
### 8.2.3. 配置
在 application.properties 配置文件中添加 MySQL 数据库的相关配置：
```java
##MySQL##
spring.datasource.url=jdbc:mysql://dev.test.internal.muqian.com:3306/smallroutine?characterEncoding=utf8
spring.datasource.username=sruser
spring.datasource.password=muqian6325
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
##MySQL##
```
在 Spring Boot 启动类中添加 ``@MapperScan`` 注解，扫描 Mapper 文件夹：
```java
@SpringBootApplication
@MapperScan("com.baomidou.mybatisplus.samples.quickstart.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(QuickStartApplication.class, args);
    }

}
```
### 8.2.4. 编码

编写实体类 User.java（此处使用了 [Lombok](https://www.projectlombok.org/) 简化代码）
```Java
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
```
编写Mapper类 UserMapper.java
```java
public interface UserMapper extends BaseMapper<User> {

}
```
### 8.2.5. 测试
添加测试类，进行功能测试：
```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }

}
```
>TIP<p>UserMapper 中的 selectList() 方法的参数为 MP 内置的条件封装器 Wrapper，所以不填写就是无任何条件

控制台输出：
```
User(id=1, name=Jone, age=18, email=test1@baomidou.com)
User(id=2, name=Jack, age=20, email=test2@baomidou.com)
User(id=3, name=Tom, age=28, email=test3@baomidou.com)
User(id=4, name=Sandy, age=21, email=test4@baomidou.com)
User(id=5, name=Billie, age=24, email=test5@baomidou.com)
```
通过以上几个简单的步骤，我们就实现了 User 表的 CRUD 功能，甚至连 XML 文件都不用编写！

但 MyBatis-Plus 的强大远不止这些功能，想要详细了解 MyBatis-Plus 的强大功能？那就继续往下看吧！


# 9. 代码生成器
AutoGenerator 是 MyBatis-Plus 的代码生成器，通过 AutoGenerator 可以快速生成 Entity、Mapper、Mapper XML、Service、Controller 等各个模块的代码，极大的提升了开发效率。
```java
// 演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
public class CodeGenerator {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("jobob");
        gc.setOpen(false);
        gc.setFileOverride(true);   // 是否覆盖文件
        gc.setActiveRecord(true);   // 开启 activeRecord 模式
        gc.setEnableCache(false);   // XML 二级缓存
        gc.setBaseResultMap(true);  // XML ResultMap
        gc.setBaseColumnList(true); // XML columList
//        gc.setKotlin(true)        //是否生成 kotlin 代码
        gc.setAuthor("xiaowei");    //作者名字


        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sDao");
        gc.setXmlName("%sMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");

        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL); // 数据库类型
        dsc.setUrl("jdbc:mysql://110.110.110.110:3306/demo?characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        mpg.setDataSource(dsc);
        
        System.out.println(dsc.getUrl());
        System.out.println(dsc.getUsername());
        System.out.println(dsc.getPassword());

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(scanner("模块名"));
        pc.setParent("com.wei");
        pc.setController("controller");
        pc.setEntity("bean");
        pc.setMapper("dao");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setXml("sqlmap");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setTemplate(new TemplateConfig().setXml(null));

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude(scanner("表名"));  // 需要生成的表
        // strategy.setExclude(new String[]{"test"}); // 排除生成的表

        // strategy.setTablePrefix(pc.getModuleName() + "_");// 此处可以修改为您的表前缀
        strategy.setTablePrefix(new String[]{"t_"});// 此处可以修改为您的表前缀

        // strategy.setCapitalMode(true);// 全局大写命名
        // strategy.setDbColumnUnderline(true)//全局下划线命名

        strategy.setNaming(NamingStrategy.underline_to_camel);      //类生成策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);// 表名生成策略


        // 自定义 Entity 父类
        // strategy.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");
        // 自定义 mapper 父类
        // strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
        // 自定义 service 父类
        // strategy.setSuperServiceClass("com.baomidou.demo.TestService");
        // 自定义 service 实现类父类
        // strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
        // 自定义 controller 父类
        // strategy.setSuperControllerClass("com.baomidou.demo.TestController");


        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        // strategy.setEntityColumnConstant(true)

        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
        // strategy.setEntityBuilderModel(true)

        // 【实体】是否为lombok模型（默认 false）<a href="https://projectlombok.org/">document</a>
        strategy.setEntityLombokModel(true);

        strategy.setRestControllerStyle(true);

        // Boolean类型字段是否移除is前缀处理
        // .setEntityBooleanColumnRemoveIsPrefix(true)


        // 自定义实体，公共字段
        // strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true);

        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        mpg.execute();
    }
}
```
![Alt](http://mp.baomidou.com/img/generator.gif)

# 10. CRUD 接口
- bean对象继承``Model<User extends Model>``
```java
public class User extends Model<User> {}
```
- Service层继承``IService<User>``
- ServiceImpl层继承``ServiceImpl<UserDao, User>``

```java
public interface UserService extends IService<User> {}
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {}
```
- dao层接口继承``BaseMapper<T>``
```java
public interface UserDao extends BaseMapper<User> {}
```
> 程序员为偷懒而活:<p>哈哈是不是很多的接口及类要继承和实现看着很晕是不是呀,建议使用代码生成工具就可以简化开发的流程了吼吼吼    
## 10.1. Model CRUD 接口 // TODO 未实践
## 10.2. Mapper CRUD 接口
>说明:
>- 通用 CRUD 封装BaseMapper接口，为 Mybatis-Plus 启动时自动解析实体表关系映射转换为 Mybatis 内部对象注入容器
>- 泛型 T 为任意实体对象
>- 参数 Serializable 为任意类型主键 Mybatis-Plus 不推荐使用复合主键约定每一张表都有自己的唯一 id 主键
>- 对象 Wrapper 为 条件构造器
### 10.2.1. insert
```java
  /**
    * <p>
    * 插入一条记录
    * </p>
    *
    * @param entity 实体对象
    */
  int insert(T entity);
```
### 10.2.2. deleteById
```java
  /**
    * <p>
    * 根据 ID 删除
    * </p>
    *
    * @param id 主键ID
    */
  int deleteById(Serializable id);
```
### 10.2.3. deleteByMap
```java
  /**
    * <p>
    * 根据 columnMap 条件，删除记录
    * </p>
    *
    * @param columnMap 表字段 map 对象
    */
  int deleteByMap(@Param(Constants.COLUMN_MAP) Map<String, Object> columnMap);
```
### 10.2.4. delete
```java
  /**
    * <p>
    * 根据 entity 条件，删除记录
    * </p>
    *
    * @param queryWrapper 实体对象封装操作类（可以为 null）
    */
  int delete(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
```
### 10.2.5. deleteBatchIds
```java
  /**
    * <p>
    * 删除（根据ID 批量删除）
    * </p>
    *
    * @param idList 主键ID列表(不能为 null 以及 empty)
    */
  int deleteBatchIds(@Param(Constants.COLLECTION) Collection<? extends Serializable> idList);
```
### 10.2.6. updateById
```java
  /**
    * <p>
    * 根据 ID 修改
    * </p>
    *
    * @param entity 实体对象
    */
  int updateById(@Param(Constants.ENTITY) T entity);
```
### 10.2.7. update
```java
  /**
    * <p>
    * 根据 whereEntity 条件，更新记录
    * </p>
    *
    * @param entity        实体对象 (set 条件值,不能为 null)
    * @param updateWrapper 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）
    */
  int update(@Param(Constants.ENTITY) T entity, @Param(Constants.WRAPPER) Wrapper<T> updateWrapper);
```
### 10.2.8. selectById
```java
  /**
    * <p>
    * 根据 ID 查询
    * </p>
    *
    * @param id 主键ID
    */
  T selectById(Serializable id);
```
### 10.2.9. selectBatchIds
```java
  /**
    * <p>
    * 查询（根据ID 批量查询）
    * </p>
    *
    * @param idList 主键ID列表(不能为 null 以及 empty)
    */
  List<T> selectBatchIds(@Param(Constants.COLLECTION) Collection<? extends Serializable> idList);
```
### 10.2.10. selectByMap
```java
  /**
    * <p>
    * 查询（根据 columnMap 条件）
    * </p>
    *
    * @param columnMap 表字段 map 对象
    */
  List<T> selectByMap(@Param(Constants.COLUMN_MAP) Map<String, Object> columnMap);
```
### 10.2.11. selectOne
```java
  /**
    * <p>
    * 根据 entity 条件，查询一条记录
    * </p>
    *
    * @param queryWrapper 实体对象
    */
  T selectOne(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
```
### 10.2.12. selectCount
```java
  /**
    * <p>
    * 根据 Wrapper 条件，查询总记录数
    * </p>
    *
    * @param queryWrapper 实体对象
    */
  Integer selectCount(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
```
### 10.2.13. selectList
```java
  /**
    * <p>
    * 根据 entity 条件，查询全部记录
    * </p>
    *
    * @param queryWrapper 实体对象封装操作类（可以为 null）
    */
  List<T> selectList(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
```
### 10.2.14. selectMaps
```java
  /**
    * <p>
    * 根据 Wrapper 条件，查询全部记录
    * </p>
    *
    * @param queryWrapper 实体对象封装操作类（可以为 null）
    */
  List<Map<String, Object>> selectMaps(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
```
### 10.2.15. selectObjs
```java
  /**
    * <p>
    * 根据 Wrapper 条件，查询全部记录
    * 注意： 只返回第一个字段的值
    * </p>
    *
    * @param queryWrapper 实体对象封装操作类（可以为 null）
    */
  List<Object> selectObjs(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
```
### 10.2.16. selectPage
```java
  /**
    * <p>
    * 根据 entity 条件，查询全部记录（并翻页）
    * </p>
    *
    * @param page         分页查询条件（可以为 RowBounds.DEFAULT）
    * @param queryWrapper 实体对象封装操作类（可以为 null）
    */
  IPage<T> selectPage(IPage<T> page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
```
### 10.2.17. selectMapsPage
```java
  /**
    * <p>
    * 根据 Wrapper 条件，查询全部记录（并翻页）
    * </p>
    *
    * @param page         分页查询条件
    * @param queryWrapper 实体对象封装操作类
    */
  IPage<Map<String, Object>> selectMapsPage(IPage<T> page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
```

## 10.3. Service CRUD 接口
>说明:
- 通用 Service CRUD 封装IService接口，进一步封装 CRUD 采用 ``get 查询单行`` ``remove 删除`` ``list 查询集合`` ``page 分页`` 前缀命名方式区分 ``Mapper`` 层避免混淆，
- 泛型 T 为任意实体对象
- 建议如果存在自定义通用 Service 方法的可能，请创建自己的 ``IBaseService`` 继承 ``Mybatis-Plus`` 提供的基类
- 对象 ``Wrapper`` 为 条件构造器

### 10.3.1. save
```java
  /**
    * <p>
    * 插入一条记录（选择字段，策略插入）
    * </p>
    *
    * @param entity 实体对象
    */
  boolean save(T entity);
```
### 10.3.2. saveBatch
```java
  /**
    * <p>
    * 插入（批量），该方法不适合 Oracle
    * </p>
    *
    * @param entityList 实体对象集合
    */
  boolean saveBatch(Collection<T> entityList);

  /**
    * <p>
    * 插入（批量）
    * </p>
    *
    * @param entityList 实体对象集合
    * @param batchSize  插入批次数量
    */
  boolean saveBatch(Collection<T> entityList, int batchSize);
```
### 10.3.3. saveOrUpdateBatch
```java
  /**
    * <p>
    * 批量修改插入
    * </p>
    *
    * @param entityList 实体对象集合
    */
  boolean saveOrUpdateBatch(Collection<T> entityList);
    
  /**
    * <p>
    * 批量修改插入
    * </p>
    *
    * @param entityList 实体对象集合
    * @param batchSize  每次的数量
    */
  boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize);
```
### 10.3.4. removeById
```java
  /**
    * <p>
    * 根据 ID 删除
    * </p>
    *
    * @param id 主键ID
    */
  boolean removeById(Serializable id);
```
### 10.3.5. removeByMap
```java
  /**
    * <p>
    * 根据 columnMap 条件，删除记录
    * </p>
    *
    * @param columnMap 表字段 map 对象
    */
  boolean removeByMap(Map<String, Object> columnMap);
```
### 10.3.6. remove
```java
  /**
    * <p>
    * 根据 entity 条件，删除记录
    * </p>
    *
    * @param queryWrapper 实体包装类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
    */
  boolean remove(Wrapper<T> queryWrapper);
```
### 10.3.7. removeByIds
```java
  /**
    * <p>
    * 删除（根据ID 批量删除）
    * </p>
    *
    * @param idList 主键ID列表
    */
  boolean removeByIds(Collection<? extends Serializable> idList);
```
### 10.3.8. updateById
```java
  /**
    * <p>
    * 根据 ID 选择修改
    * </p>
    *
    * @param entity 实体对象
    */
  boolean updateById(T entity);
```
### 10.3.9. update
```java
  /**
    * <p>
    * 根据 whereEntity 条件，更新记录
    * </p>
    *
    * @param entity        实体对象
    * @param updateWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper}
    */
  boolean update(T entity, Wrapper<T> updateWrapper);
```
### 10.3.10. updateBatchById
```java
  /**
    * <p>
    * 根据ID 批量更新
    * </p>
    *
    * @param entityList 实体对象集合
    */
  boolean updateBatchById(Collection<T> entityList);

  /**
    * <p>
    * 根据ID 批量更新
    * </p>
    *
    * @param entityList 实体对象集合
    * @param batchSize  更新批次数量
    */
  boolean updateBatchById(Collection<T> entityList, int batchSize);
```
### 10.3.11. saveOrUpdate
```java
  /**
    * <p>
    * TableId 注解存在更新记录，否插入一条记录
    * </p>
    *
    * @param entity 实体对象
    */
  boolean saveOrUpdate(T entity);
```
### 10.3.12. getById
```java
  /**
    * <p>
    * 根据 ID 查询
    * </p>
    *
    * @param id 主键ID
    */
  T getById(Serializable id);
```
### 10.3.13. listByIds
```java
  /**
    * <p>
    * 查询（根据ID 批量查询）
    * </p>
    *
    * @param idList 主键ID列表
    */
  Collection<T> listByIds(Collection<? extends Serializable> idList);
```
### 10.3.14. listByMap
```java
  /**
    * <p>
    * 查询（根据 columnMap 条件）
    * </p>
    *
    * @param columnMap 表字段 map 对象
    */
  Collection<T> listByMap(Map<String, Object> columnMap);
```
### 10.3.15. getOne
```java
  /**
    * <p>
    * 根据 Wrapper，查询一条记录
    * </p>
    *
    * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
    */
  T getOne(Wrapper<T> queryWrapper);
```
### 10.3.16. getMap
```java
  /**
    * <p>
    * 根据 Wrapper，查询一条记录
    * </p>
    *
    * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
    */
  Map<String, Object> getMap(Wrapper<T> queryWrapper);
```
### 10.3.17. getObj
```java
  /**
    * <p>
    * 根据 Wrapper，查询一条记录
    * </p>
    *
    * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
    */
  Object getObj(Wrapper<T> queryWrapper);
```
### 10.3.18. count
```java
  /**
    * <p>
    * 根据 Wrapper 条件，查询总记录数
    * </p>
    *
    * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
    */
  int count(Wrapper<T> queryWrapper);
```
### 10.3.19. list
```java
  /**
    * <p>
    * 查询列表
    * </p>
    *
    * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
    */
  List<T> list(Wrapper<T> queryWrapper);
```
### 10.3.20. page
```java
  /**
    * <p>
    * 翻页查询
    * </p>
    *
    * @param page         翻页对象
    * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
    */
  IPage<T> page(IPage<T> page, Wrapper<T> queryWrapper);
```
### 10.3.21. listMaps
```java
  /**
    * <p>
    * 查询列表
    * </p>
    *
    * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
    */
  List<Map<String, Object>> listMaps(Wrapper<T> queryWrapper);
```
### 10.3.22. listObjs
```java
  /**
    * <p>
    * 根据 Wrapper 条件，查询全部记录
    * </p>
    *
    * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
    */
  List<Object> listObjs(Wrapper<T> queryWrapper);
```
### 10.3.23. pageMaps
```java
  /**
    * <p>
    * 翻页查询
    * </p>
    *
    * @param page         翻页对象
    * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
    */
  IPage<Map<String, Object>> pageMaps(IPage<T> page, Wrapper<T> queryWrapper);
```


# 11. 条件构造器
> 说明:
>- 以下出现的第一个入参boolean condition表示该条件是否加入最后生成的sql中
>- 以下代码块内的多个方法均为从上往下补全个别boolean类型的入参,默认为true
>- 以下出现的泛型This均为具体使用的Wrapper的实例
>- 以下方法在入参中出现的R为泛型,在普通wrapper中是String,在LambdaWrapper中是函数(例:Entity::getId,Entity为实体类,getId为字段id的getMethod)
>- 以下方法入参中的R column均表示数据库字段,当R为String时则为数据库字段名(字段名是数据库关键字的自己用转义符包裹!)!而不是实体类数据字段名!!!
>- 以下举例均为使用普通wrapper,入参为Map和List的均以json形式表现!
>- 使用中如果入参的Map或者List为空,则不会加入最后生成的sql中!!!
>- 有任何疑问就点开源码看,看不懂函数的[点击我学习新知识](https://www.jianshu.com/p/613a6118e2e0)
## 11.1. AbstractWrapper
>说明:<P>QueryWrapper(LambdaQueryWrapper) 和 UpdateWrapper(LambdaUpdateWrapper) 的父类
用于生成 sql 的 where 条件, entity 属性也用于生成 sql 的 where 条件
### 11.1.1. allEq
```java
  /**
    * map 所有非空属性等于 =
    *
    * @param condition   执行条件
    * @param params      map 类型的参数, key 是字段名, value 是字段值
    * @param null2IsNull 是否参数为 null 自动执行 isNull 方法, false 则忽略这个字段
    */
  allEq(Map<R, V> params)
  allEq(Map<R, V> params, boolean null2IsNull)
  allEq(boolean condition, Map<R, V> params, boolean null2IsNull)
```
- 例1: allEq({id:1,name:"老王",age:null})--->id = 1 and name = '老王' and age is null
- 例2: allEq({id:1,name:"老王",age:null}, false)--->id = 1 and name = '老王'

```java
  /**
    * 字段过滤接口，传入多参数时允许对参数进行过滤
    *
    * @param condition   执行条件
    * @param filter      返回 true 来允许字段传入比对条件中
    * @param params      map 类型的参数, key 是字段名, value 是字段值
    * @param null2IsNull 是否参数为 null 自动执行 isNull 方法, false 则忽略这个字段
    */
  allEq(BiPredicate<R, V> filter, Map<R, V> params)
  allEq(BiPredicate<R, V> filter, Map<R, V> params, boolean null2IsNull)
  allEq(boolean condition, BiPredicate<R, V> filter, Map<R, V> params, boolean null2IsNull) 
```
- 例1: allEq((k,v) -> k.indexOf("a") > 0, {id:1,name:"老王",age:null})--->name = '老王' and age is null
- 例2: allEq((k,v) -> k.indexOf("a") > 0, {id:1,name:"老王",age:null}, false)--->name = '老王'
### 11.1.2. eq
```Java
  eq(R column, Object val)
  eq(boolean condition, R column, Object val)
```
- 等于 =
- 例: eq("name", "老王")--->name = '老王'
### 11.1.3. ne
```Java
  ne(R column, Object val)
  ne(boolean condition, R column, Object val)
```
- 不等于 <>
- 例: ne("name", "老王")--->name <> '老王'
### 11.1.4. gt
```Java
  gt(R column, Object val)
  gt(boolean condition, R column, Object val)
```
- 大于 >
- 例: gt("age", 18)--->age > 18
### 11.1.5. ge
```Java
  ge(R column, Object val)
  ge(boolean condition, R column, Object val)
```
- 大于等于 >=
- 例: ge("age", 18)--->age >= 18
### 11.1.6. lt
```Java
  lt(R column, Object val)
  lt(boolean condition, R column, Object val)
```
- 小于 <
- 例: lt("age", 18)--->age < 18
### 11.1.7. le
```Java
  le(R column, Object val)
  le(boolean condition, R column, Object val)
```
- 小于等于 <=
- 例: le("age", 18)--->age <= 18
### 11.1.8. between
```Java
  between(R column, Object val1, Object val2)
  between(boolean condition, R column, Object val1, Object val2)
```
- BETWEEN 值1 AND 值2
- 例: between("age", 18, 30)--->age between 18 and 30
### 11.1.9. notBetween
```Java
  notBetween(R column, Object val1, Object val2)
  notBetween(boolean condition, R column, Object val1, Object val2)
```
- NOT BETWEEN 值1 AND 值2
- 例: notBetween("age", 18, 30)--->age not between 18 and 30
### 11.1.10. like
```Java
  like(R column, Object val)
  like(boolean condition, R column, Object val)
```
- LIKE '%值%'
- 例: like("name", "王")--->name like '%王%'
### 11.1.11. notLike
```Java
  notLike(R column, Object val)
  notLike(boolean condition, R column, Object val)
```
- NOT LIKE '%值%'
- 例: notLike("name", "王")--->name not like '%王%'
### 11.1.12. likeLeft
```Java
  likeLeft(R column, Object val)
  likeLeft(boolean condition, R column, Object val)
```
- LIKE '%值'
- 例: likeLeft("name", "王")--->name like '%王'
### 11.1.13. likeRight
```Java
  likeRight(R column, Object val)
  likeRight(boolean condition, R column, Object val)
```
- LIKE '值%'
- 例: likeRight("name", "王")--->name like '王%'
### 11.1.14. isNull
```Java
  isNull(R column)
  isNull(boolean condition, R column)
```
- 字段 IS NULL
- 例: isNull("name")--->name is null
### 11.1.15. isNotNull
```Java
  isNotNull(R column)
  isNotNull(boolean condition, R column)
```
- 字段 IS NULL
- 例: isNotNull("name")--->name is not null
### 11.1.16. in
```Java
  in(R column, Collection<?> value)
  in(boolean condition, R column, Collection<?> value)
```
- 字段 IN (value.get(0), value.get(1), ...)
- 例: in("age",{1,2,3})--->age in (1,2,3)

```JAVA
  字段 IN (value.get(0), value.get(1), ...)
  例: in("age",{1,2,3})--->age in (1,2,3)
```
- 字段 IN (v0, v1, ...)
- 例: in("age", 1, 2, 3)--->age in (1,2,3)
### 11.1.17. notIn
```Java
  notIn(R column, Collection<?> value)
  notIn(boolean condition, R column, Collection<?> value)
```
- 字段 IN (value.get(0), value.get(1), ...)
- 例: notIn("age",{1,2,3})--->age not in (1,2,3)

```JAVA
  notIn(R column, Object... values)
  notIn(boolean condition, R column, Object... values)
```
- 字段 NOT IN (v0, v1, ...)
- 例: notIn("age", 1, 2, 3)--->age not in (1,2,3)
### 11.1.18. inSql
```Java
  inSql(R column, String inValue)
  inSql(boolean condition, R column, String inValue)
```
- 字段 IN ( sql语句 )
- 例: inSql("age", "1,2,3,4,5,6")--->age in (1,2,3,4,5,6)
- 例: inSql("id", "select id from table where id < 3")--->id in (select id from table where id < 3)


### 11.1.19. notInSql
```Java
  notInSql(R column, String inValue)
  notInSql(boolean condition, R column, String inValue)
```
- 字段 NOT IN ( sql语句 )
- 例: notInSql("age", "1,2,3,4,5,6")--->age not in (1,2,3,4,5,6)
- 例: notInSql("id", "select id from table where id < 3")--->age not in (select id from table where id < 3)

### 11.1.20. groupBy
```Java
  groupBy(R... columns)
  groupBy(boolean condition, R... columns)
```
- 分组：GROUP BY 字段, ...
- 例: groupBy("id", "name")--->group by id,name

### 11.1.21. orderByAsc
```Java
  orderByAsc(R... columns)
  orderByAsc(boolean condition, R... columns)
```
- 排序：ORDER BY 字段, ... ASC
- 例: orderByAsc("id", "name")--->order by id ASC,name ASC

### 11.1.22. orderByDesc
```Java
  orderByDesc(R... columns)
  orderByDesc(boolean condition, R... columns)
```
- 排序：ORDER BY 字段, ... DESC
- 例: orderByDesc("id", "name")--->order by id DESC,name DESC
### 11.1.23. orderBy
```Java
  orderBy(boolean condition, boolean isAsc, R... columns)
```
- 排序：ORDER BY 字段, ...
- 例: orderBy(true, true, "id", "name")--->order by id ASC,name ASC
### 11.1.24. having
```Java
  having(String sqlHaving, Object... params)
  having(boolean condition, String sqlHaving, Object... params)
```
- HAVING ( sql语句 )
- 例: having("sum(age) > 10")--->having sum(age) > 10
- 例: having("sum(age) > {0}", 11)--->having sum(age) > 11
### 11.1.25. or
```Java
  or()
  or(boolean condition)
```
- 拼接 OR
>注意事项:<p>主动调用or表示紧接着下一个方法不是用and连接!(不调用or则默认为使用and连接)
- 例: eq("id",1).or().eq("name","老王")--->id = 1 or name = '老王'

```java
  or(Function<This, This> func)
  or(boolean condition, Function<This, This> func)
```
- OR 嵌套
- 例: or(i -> i.eq("name", "李白").ne("status", "活着"))--->or (name = '李白' and status <> '活着')
### 11.1.26. and
```Java
  and(Function<This, This> func)
  and(boolean condition, Function<This, This> func)
```
- AND 嵌套
- 例: and(i -> i.eq("name", "李白").ne("status", "活着"))--->and (name = '李白' and status <> '活着')
### 11.1.27. apply
```Java
  apply(String sqlHaving, Object... params)
  apply(boolean condition, String sqlHaving, Object... params)
```
- 拼接 sql
>注意事项:<p>
该方法可用于数据库函数 动态入参的params对应前面sqlHaving内部的{index}部分.这样是不会有sql注入风险的,反之会有!
- 例: apply("id = 1")--->having sum(age) > 10
- 例: apply("date_format(dateColumn,'%Y-%m-%d') = '2008-08-08'")--->date_format(dateColumn,'%Y-%m-%d') = '2008-08-08'")
- 例: apply("date_format(dateColumn,'%Y-%m-%d') = {0}", "2008-08-08")--->date_format(dateColumn,'%Y-%m-%d') = '2008-08-08'")
### 11.1.28. last
```Java
  last(String lastSql)
  last(boolean condition, String lastSql)
```
- 无视优化规则直接拼接到 sql 的最后
- 例: last("limit 1")
>注意事项:<p>
只能调用一次,多次调用以最后一次为准 有sql注入的风险,请谨慎使用

### 11.1.29. exists
```Java
  exists(String existsSql)
  exists(boolean condition, String existsSql)
```
- 拼接 EXISTS ( sql语句 )
- 例: exists("select id from table where age = 1")--->exists (select id from table where age = 1)
### 11.1.30. notExists
```Java
  notExists(String existsSql)
  notExists(boolean condition, String existsSql)
```
- 拼接 NOT EXISTS ( sql语句 )
- 例: notExists("select id from table where age = 1")--->not exists (select id from table where age = 1)
### 11.1.31. nested
```Java
  nested(Function<This, This> func)
  nested(boolean condition, Function<This, This> func)
```
- 正常嵌套 不带 AND 或者 OR
- 例: nested(i -> i.eq("name", "李白").ne("status", "活着"))--->(name = '李白' and status <> '活着')

## 11.2. QueryWrapper
>说明:<p>
继承自 AbstractWrapper ,自身的内部属性 entity 也用于生成 where 条件
及 LambdaQueryWrapper, LambdaQueryWrapper 不能 new 出来,只能通过 new QueryWrapper().lambda() 方法获取!

### 11.2.1. select
```java
  select(String... sqlSelect)
  select(Predicate<TableFieldInfo> predicate)
```
>说明:<p>
以上方法为2个方法.
第二个方法为:过滤查询字段(主键除外),调用前需要wrapper内的entity属性有值! 3.0.3开始,使用第二个方法优先级最高,同一方法重复调用只有最后一次有效!
两个方法都调用以第二个方法为准!
预计3.0.8及之后,两个方法重复调用只有最后一次有效!
- 设置查询字段
- 例: select("id", "name", "age")
- 例: select("id", "name", "age")
### 11.2.2. excludeColumns``@Deprecated``
排除查询字段
> 警告
调用前同样需要wrapper内的entity属性有值!
从``3.0.3``版本开始打上了``@Deprecated``标记,预计在``3.0.5``版本上移除此方法,请谨慎使用!
推荐使用select方法

## 11.3. UpdateWrapper
说明:<p>
继承自 AbstractWrapper ,自身的内部属性 entity 也用于生成 where 条件
及 LambdaUpdateWrapper, LambdaUpdateWrapper 不能 new 出来,只能通过 new UpdateWrapper().lambda() 方法获取!
### 11.3.1. set
```Java
  set(String column, Object val)
  set(boolean condition, String column, Object val)
```
- SQL SET 字段
- 例: set("name", "老李头")
- 例: set("name", "")--->数据库字段值变为空字符串
- 例: set("name", null)--->数据库字段值变为null

### 11.3.2. setSql
```Java
  setSql(String sql)
```
- 设置 SET 部分 SQL
- 例: set("name = '老李头')

### 11.3.3. lambda
```Java
获取 LambdaWrapper
在QueryWrapper中是获取LambdaQueryWrapper
在UpdateWrapper中是获取LambdaUpdateWrapper
```

# 12. 分页插件
## 12.1. 分页插件配置
```xml
<!-- spring xml 方式 -->
<plugins>
    <plugin interceptor="com.baomidou.mybatisplus.plugins.PaginationInterceptor">
        <property name="sqlParser" ref="自定义解析类、可以没有" />
        <property name="dialectClazz" value="自定义方言类、可以没有" />
    </plugin>
</plugins>
```

```java
//Spring boot方式
@EnableTransactionManagement
@Configuration
@MapperScan("com.baomidou.cloud.service.*.mapper*")
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
```
## 12.2. 分页demo
- UserMapper.java 方法内容

```Java
public interface UserMapper{//可以继承或者不继承BaseMapper
    /**
     * <p>
     * 查询 : 根据state状态查询用户列表，分页显示
     * 注意!!: 如果入参是有多个,需要加注解指定参数名才能在xml中取值
     * </p>
     *
     * @param page 分页对象,xml中可以从里面进行取值,传递参数 Page 即自动分页,必须放在第一位(你可以继承Page实现自己的分页对象)
     * @param state 状态
     * @return 分页对象
     */
    IPage<User> selectPageVo(Page page, @Param("state") Integer state);
}
```
- UserMapper.xml 等同于编写一个普通 list 查询，mybatis-plus 自动替你分页
```xml
<select id="selectPageVo" resultType="com.baomidou.cloud.entity.UserVo">
    SELECT id,name FROM user WHERE state=#{state}
</select>
```
- UserServiceImpl.java 调用分页方法
```java
public IPage<User> selectUserPage(Page<User> page, Integer state) {
    // 不进行 count sql 优化，解决 MP 无法自动优化 SQL 问题，这时候你需要自己查询 count 部分
    // page.setOptimizeCountSql(false);
    // 当 total 为非 0 时(默认为 0),分页插件不会进行 count 查询
    // 要点!! 分页返回的对象与传入的对象是同一个
    return userMapper.selectPageVo(page, state));
}
```
>注意:<p>
实际上它还是继承了mybatis的``RowBounds``类,还是不建议在数据量较大的情况下使用这个类,很容易造成``OOM``的



参考资料:
[http://www.mybatis.org/mybatis-3/zh/index.html](http://www.mybatis.org/mybatis-3/zh/index.html)
[http://mp.baomidou.com/](http://mp.baomidou.com/)
《spring+mybatis企业应用实战》疯狂软件
《Spring Boot 2 企业应用实战》疯狂软件
《Java EE互联网轻量级框架整合开发》