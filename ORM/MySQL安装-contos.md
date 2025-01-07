<!-- TOC -->

- [1. CentOS安装MySQL教程](#1-centos%E5%AE%89%E8%A3%85mysql%E6%95%99%E7%A8%8B)
    - [1.1. 安装条件](#11-%E5%AE%89%E8%A3%85%E6%9D%A1%E4%BB%B6)
    - [1.2. 卸载](#12-%E5%8D%B8%E8%BD%BD)
    - [1.3. 安装](#13-%E5%AE%89%E8%A3%85)
    - [1.4. 设置远程root](#14-%E8%AE%BE%E7%BD%AE%E8%BF%9C%E7%A8%8Broot)
    - [1.5. 设置编码](#15-%E8%AE%BE%E7%BD%AE%E7%BC%96%E7%A0%81)
- [2. 坑](#2-%E5%9D%91)
    - [2.1. 补坑](#21-%E8%A1%A5%E5%9D%91)

<!-- /TOC -->
# 1. CentOS安装MySQL教程

## 1.1. 安装条件
活人一个

Linux(centOS)

耳机

## 1.2. 卸载

    # yum list installed | grep mysql
    # yum -y remove mysql-libs.x86_64

当然还有其它方法

    # rpm -qa | grep mysql

如果你系统已安装，可以选择进行卸载:

    # rpm -e mysql　　// 普通删除模式
    # rpm -e --nodeps mysql　　// 强力删除模式，如果使用上面命令删除时，提示有依赖的其它文件，则用该命令可以对其进行强力删除

## 1.3. 安装

安装及配置

    # wget http://repo.mysql.com/mysql-community-release-el6-5.noarch.rpm
    # rpm -ivh mysql-community-release-el6-5.noarch.rpm
    # yum repolist all | grep mysql

安装MYSQL数据库

    # yum install mysql-community-server -y

设置开机启动

    # chkconfig --list | grep mysqld
    # chkconfig --level 345 mysqld on
重启
    
    # reboot

## 1.4. 设置远程root
启动MySQL

    # service mysqld start

设置root密码 `有坑注意`

    # mysql_secure_installation

登陆root账号

    # mysql -uroot -p

建立远程root用户

    mysql> GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY '密码' WITH GRANT OPTION;
    mysql> flush privileges;

## 1.5. 设置编码
查看编码

    mysql> show variables like 'character%';

打开配置文件

    # vi /etc/my.cnf

添加如下信息

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

重启mysql

    # service mysqld restart

# 2. 坑
## 2.1. 补坑
    ERROR 1045 (28000): Access denied for user 'root'@'localhost' (using password: YES)

打开配置文档

    # vim /etc/my.cnf (注：windows下修改的是my.ini)  
配置文档添加
[mysqld]后面任意一行添加“skip-grant-tables”用来跳过密码验证的过程

    [mysqld]
    skip-grant-tables

重启MySQL
    
    /etc/init.d/mysql restart(有些用户可能需要使用/etc/init.d/mysqld restart)

重启后修改root密码

    # mysql

    mysql> use mysql;
    mysql> update user set password=password("你的新密码") where user="root";
    mysql> flush privileges;
    mysql> quit

到这里root账户就已经重置成新的密码了。

编辑my.cnf,去掉刚才添加的内容，然后重启MySQL。大功告成！
