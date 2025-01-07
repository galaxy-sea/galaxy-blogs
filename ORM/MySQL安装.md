<!-- TOC -->

- [1. CentOS安装MySQL教程](#1-centos安装mysql教程)
    - [1.2. 卸载](#12-卸载)
    - [1.3. 安装](#13-安装)
    - [1.4. 设置远程root](#14-设置远程root)
    - [1.5. 设置编码](#15-设置编码)
- [2. 坑](#2-坑)
    - [2.1. 补坑](#21-补坑)
- [MySQL简介](#mysql简介)
- [MySQL常用语法](#mysql常用语法)
    - [连接MySQL](#连接mysql)
    - [库相关操作](#库相关操作)

<!-- /TOC -->
# 1. CentOS安装MySQL教程

## 1.2. 卸载
```shell
    # yum list installed | grep mysql
    # yum -y remove mysql-libs.x86_64
```
当然还有其它方法
```
    # rpm -qa | grep mysql
```

如果你系统已安装，可以选择进行卸载:
```
    # rpm -e mysql　　// 普通删除模式
    # rpm -e --nodeps mysql　　// 强力删除模式，如果使用上面命令删除时，提示有依赖的其它文件，则用该命令可以对其进行强力删除
```

## 1.3. 安装

安装及配置

``` 
    # wget http://repo.mysql.com/mysql-community-release-el6-5.noarch.rpm
    # rpm -ivh mysql-community-release-el6-5.noarch.rpm
    # yum repolist all | grep mysql
```

安装MYSQL数据库
``` 
    # yum install mysql-community-server -y
``` 
## 1.4. 设置远程root
启动MySQL
``` 
    # service mysqld start
``` 
设置root密码 `有坑注意`
``` 
    # mysql_secure_installation
``` 
登陆root账号
``` 
    # mysql -uroot -p
``` 
建立远程root用户
``` 
    mysql> GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY '密码' WITH GRANT OPTION;
    mysql> flush privileges;
``` 
## 1.5. 设置编码
查看编码
``` 
    mysql> show variables like 'character%';
``` 
打开配置文件
``` 
    # vi /etc/my.cnf
``` 
添加如下信息
``` 
    [mysqld]
    character-set-server=utf8 
    collation-server=utf8_general_ci 
    sql_mode='NO_ENGINE_SUBSTITUTION'

    [mysql]
    default-character-set = utf8

    [mysql.server]
    default-character-set = utf8


    [mysqld_safe]
    default-character-set = utf8


    [client]
    default-character-set = utf8
``` 
重启mysql
``` 
    # service mysqld restart
``` 
# 2. 坑
## 2.1. 补坑
``` 
    ERROR 1045 (28000): Access denied for user 'root'@'localhost' (using password: YES)
``` 
打开配置文档
``` 
    # vim /etc/my.cnf (注：windows下修改的是my.ini)  
``` 
配置文档添加
[mysqld]后面任意一行添加“skip-grant-tables”用来跳过密码验证的过程
``` 
    [mysqld]
    skip-grant-tables
``` 
重启MySQL
``` 
    /etc/init.d/mysql restart(有些用户可能需要使用/etc/init.d/mysqld restart)
``` 
重启后修改root密码
``` 
    # mysql

    mysql> use mysql;
    mysql> update user set password=password("你的新密码") where user="root";
    mysql> flush privileges;
    mysql> quit
``` 
到这里root账户就已经重置成新的密码了。

编辑my.cnf,去掉刚才添加的内容，然后重启MySQL。大功告成！

# MySQL简介
# MySQL常用语法
## 连接MySQL
```SQL
mysql -u[root] -p[root]
mysql -h110.110.110.110 -uroot -proot;
```
## 库相关操作
```sql
create database 库名;  (创建数据库)
create database 库名 character set utf8;  (创建数据库)

show databases;  (查看数据库)
show create databases 库名;  (查看数据库详细)

use 库名;  (进入数据库)

drop database 库名;  (删除数据库)
```


