

# 1. init DB 
>任何骚操作都不能少了很骚的表

```sql
CREATE TABLE `t_users` (
	`id` INT ( 11 ) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`username` VARCHAR ( 16 ) NOT NULL,
	`nickname` VARCHAR ( 16 ) DEFAULT NULL,
	`password` VARCHAR ( 16 ) NOT NULL,
	`create_time` datetime DEFAULT CURRENT_TIMESTAMP,
	`update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP 
) ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8;

INSERT INTO t_users ( `username`, `nickname`, `password`) VALUES('小花', 'xh', 'admin');
INSERT INTO t_users ( `username`, `nickname`, `password`) VALUES('大花', 'dh', 'admin');
INSERT INTO t_users ( `username`, `nickname`, `password`) VALUES('菊花', 'jh', 'admin');
INSERT INTO t_users ( `username`, `nickname`, `password`) VALUES('霸王花', 'bwh', 'admin');
INSERT INTO t_users ( `username`, `password`) VALUES('玫瑰花','admin');
INSERT INTO t_users ( `username`, `password`) VALUES('什么花','admin');
INSERT INTO t_users ( `username`, `password`) VALUES('有钱花','admin');
```
# 2. select骚操作
## 2.1. select
### 2.1.1. row_number, 列的id增长
> SQL server有row_number()来对select的视图进行列id填充, 可是那MySQL为什么没有这个骚操作呀,经过``老章``那如同日本某爱情动作片的骚操作教学,让我掌握了很多骚操作. 卧槽这个姿势太爽了,妈的简单高效呀
```SQL
SELECT
	@ROW := @ROW + 1 AS ROW,
	u.* 
FROM
	`t_users` u,
	( SELECT @ROW := 0 ) r
```
```sql
+-----+----+----------+----------+----------+---------------------+-------------+
| ROW | id | username | nickname | password | create_time         | update_time |
+-----+----+----------+----------+----------+---------------------+-------------+
|   1 |  1 | 小花     | xh       | admin    | 2019-01-14 12:09:30 | NULL        |
|   2 |  2 | 大花     | dh       | admin    | 2019-01-14 12:09:30 | NULL        |
|   3 |  3 | 菊花     | jh       | admin    | 2019-01-14 12:09:30 | NULL        |
|   4 |  4 | 霸王花   | bwh      | admin    | 2019-01-14 12:09:30 | NULL        |
|   5 |  5 | 玫瑰花   | NULL     | admin    | 2019-01-14 12:09:30 | NULL        |
|   6 |  6 | 什么花   | NULL     | admin    | 2019-01-14 12:09:30 | NULL        |
|   7 |  7 | 有钱花   | NULL     | admin    | 2019-01-14 12:09:31 | NULL        |
+-----+----+----------+----------+----------+---------------------+-------------+
```

## 2.2. order的空字段
### 2.2.1. order空值排在最后面
>卧槽又是一个骚操作...  今天要对注册进行倒叙排序但是昵称为null的要放在最后,卧槽!!!, 没学过这个姿势呀, 赶紧看看av.com的教学, <br>
>思路, 先对昵称进行排序,在对注册时间进行排序,  😭很骚呀<p>
>经过很多的时间和MySQL进进出出的磨合, 发现一下两段SQL函数都能达到效果
```SQL
IF(expr1,expr2,expr3)
```
```SQL
CASE 
	WHEN when_value THEN
		statement_list
	WHEN when_value THEN
		statement_list
	ELSE
		statement_list
END
```

>骚操作开始
```SQL
SELECT
	* 
FROM
	t_users 
ORDER BY
	IF( nickname IS NULL, 1, 0 ),
	create_time DESC
```
```SLQ
+----+----------+----------+----------+---------------------+-------------+
| id | username | nickname | password | create_time         | update_time |
+----+----------+----------+----------+---------------------+-------------+
|  1 | 小花     | xh       | admin    | 2019-01-14 12:09:30 | NULL        |
|  2 | 大花     | dh       | admin    | 2019-01-14 12:09:30 | NULL        |
|  3 | 菊花     | jh       | admin    | 2019-01-14 12:09:30 | NULL        |
|  4 | 霸王花   | bwh      | admin    | 2019-01-14 12:09:30 | NULL        |
|  7 | 有钱花   | NULL     | admin    | 2019-01-14 12:09:31 | NULL        |
|  5 | 玫瑰花   | NULL     | admin    | 2019-01-14 12:09:30 | NULL        |
|  6 | 什么花   | NULL     | admin    | 2019-01-14 12:09:30 | NULL        |
+----+----------+----------+----------+---------------------+-------------+
```
>骚操作2
```SQL
SELECT
	* 
FROM
	t_users 
ORDER BY
CASE
	WHEN nickname = 'bwh' THEN
		1 
	WHEN nickname IS NULL THEN
		2 
	ELSE 
		3 
END
```
```SQL
+----+----------+----------+----------+---------------------+-------------+
| id | username | nickname | password | create_time         | update_time |
+----+----------+----------+----------+---------------------+-------------+
|  4 | 霸王花   | bwh      | admin    | 2019-01-14 12:09:30 | NULL        |
|  5 | 玫瑰花   | NULL     | admin    | 2019-01-14 12:09:30 | NULL        |
|  6 | 什么花   | NULL     | admin    | 2019-01-14 12:09:30 | NULL        |
|  7 | 有钱花   | NULL     | admin    | 2019-01-14 12:09:31 | NULL        |
|  1 | 小花     | xh       | admin    | 2019-01-14 12:09:30 | NULL        |
|  2 | 大花     | dh       | admin    | 2019-01-14 12:09:30 | NULL        |
|  3 | 菊花     | jh       | admin    | 2019-01-14 12:09:30 | NULL        |
+----+----------+----------+----------+---------------------+-------------+
```




