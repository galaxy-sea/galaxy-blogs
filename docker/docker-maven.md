
<!-- TOC -->

- [1. docker 安装](#1-docker-安装)
  - [1.1. 本机安装](#11-本机安装)
    - [1.1.1. idea连接服务器](#111-idea连接服务器)
    - [1.1.2. 本机环境变量(可选)](#112-本机环境变量可选)
  - [1.2. 服务器安装](#12-服务器安装)
    - [1.2.1. 安装](#121-安装)
    - [1.2.2. 阿里云镜像加速](#122-阿里云镜像加速)
    - [1.2.3. 开启远程端口](#123-开启远程端口)
- [2. docker maven 插件](#2-docker-maven-插件)
  - [2.1. spring-boot-maven-plugin 使用](#21-spring-boot-maven-plugin-使用)
  - [2.2. dockerfile-maven-plugin](#22-dockerfile-maven-plugin)
- [3. 常用中间件部署](#3-常用中间件部署)
  - [3.1. redis](#31-redis)
  - [3.2. nacos](#32-nacos)
  - [3.3. mysql](#33-mysql)
  - [3.4. rocketmq  -- 不好用](#34-rocketmq-----不好用)
  - [3.5. rabbitmq](#35-rabbitmq)
  - [3.6. seata](#36-seata)
  - [3.7. zipkin](#37-zipkin)
  - [3.8. neo4j](#38-neo4j)

<!-- /TOC -->


# 1. docker 安装

## 1.1. 本机安装
放弃吧，Linux使用才是王道，mac和windows太占用资源了

### 1.1.1. idea连接服务器

1. ssh 推荐
2. http 很不推荐呀

idea 下载docker插件,百度一下教程很多的


### 1.1.2. 本机环境变量(可选)

因为环境变量只能配置一个,但是有多台docker服务器的时候,用host来替换掉真实的IP,``host保存就刷新但是环境变量会有缓存``

> 环境变量 配置
```
指向docker服务器
export DOCKER_HOST="tcp://docker.host:2375"
```

> host配置

```
用host替换掉环境变量真实地址
192.168.31.112	docker.host
```




## 1.2. 服务器安装


### 1.2.1. 安装

1. [docker安装](https://docs.docker.com/engine/install/)
2. [docker-compose安装](https://docs.docker.com/compose/install/#install-compose)
3. https://blog.csdn.net/weixin_42988176/article/details/124963314
### 1.2.2. 阿里云镜像加速

阿里云开通``容器镜像服务``按照[镜像加速器](https://cr.console.aliyun.com/cn-hangzhou/instances/mirrors)进行配置就可以了


### 1.2.3. 开启远程端口
这里使用CentOS进行演示

```shell
vim /usr/lib/systemd/system/docker.service


在ExecStart=/usr/bin/dockerd追加
-H tcp://0.0.0.0:2375 -H unix://var/run/docker.sock

```


```shell
重新配置以及重启docker
systemctl daemon-reload
systemctl restart docker


防火墙开放2375端口 
firewall-cmd --zone=public --add-port=2375/tcp --permanent
firewall-cmd --reload

查看端口监听是否开启
netstat -nlpt
curl测试是否生效
curl http://127.0.0.1:2375/info 
```




# 2. docker maven 插件

1. spring-boot-maven-plugin Spring官方的但是我觉得不好用
2. dockerfile-maven-plugin 这个好用,很好用
3. jib-maven-plugin 没有用过


## 2.1. spring-boot-maven-plugin 使用

```xml
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <image>
                        <name>registry.cn-hangzhou.aliyuncs.com/${Namespace}/${project.artifactId}:${project.version}</name>
                        <!--镜像打包完成后自动推送到镜像仓库-->
                        <publish>true</publish>
                    </image>
                    <docker>
                        <!--包含 Docker 守护程序的主机和端口的 URL - 例如tcp://192.168.99.100:2376-->
                        <!--suppress HttpUrlsUsage -->
                        <host>http://nuc8i7.wcj.plus:2375</host>
                        <!--true设置为（可选）时启用安全 HTTPS 协议-->
                        <tlsVerify>false</tlsVerify>
                        <!--HTTPS 证书和密钥文件的路径（如果tlsVerify是 则需要true，否则忽略）-->
                        <!--<certPath>/home/user/.minikube/certs</certPath>-->
                        <publishRegistry>
                            <username>username</username>
                            <password>password</password>
                            <url>https://registry.cn-hangzhou.aliyuncs.com</url>
                            <!--<email>email@email.com</email>-->
                        </publishRegistry>
                    </docker>
                </configuration>
            </plugin>
```

## 2.2. dockerfile-maven-plugin

使用远程docker服务,需要本地配置环境变量哦

dockerfile-maven-plugin需要编写pom插件配置已经Dockerfile配置

> xml

``` xml
            <plugin>
                <groupId>com.spotify</groupId>
                <artifactId>dockerfile-maven-plugin</artifactId>
                <version>1.4.13</version>

                <executions>
                    <execution>
                        <id>default</id>
                        <goals>
                            <goal>build</goal>
                            <goal>push</goal>
                        </goals>
                    </execution>
                </executions>

                <configuration>
                    <repository>registry.cn-hangzhou.aliyuncs.com/${Namespace}/${project.artifactId}</repository>
                    <username>username</username>
                    <password>password</password>

                    <tag>${project.version}</tag>
                    <buildArgs>
                        <!-- dockerfile的arg变量 -->
                        <JAR_PATH>target/${project.build.finalName}.jar</JAR_PATH>
                    </buildArgs>
                </configuration>
            </plugin>
```

> Dockerfile 

```
FROM openjdk:8-jre
MAINTAINER you name <gmail@gmail.com>

ARG JAR_PATH
ADD ${JAR_PATH} /usr/local/app.jar

ENTRYPOINT ["java", "-jar", "/usr/local/app.jar"]

```

好了,这样子就结束了, 


# 3. 常用中间件部署

## 3.1. redis

```shell
# redis
docker run --name ${name} --restart=always -itd \
-p 6379:6379 \
redis:alpine \
--requirepass ${password}
```


## 3.2. nacos

```shell
# nacos 单机
docker run --name ${name} --restart=always -itd \
-p 8848:8848 \
-e MODE=standalone \
nacos/nacos-server
```


## 3.3. mysql

```shell

# mysql 简单
docker run --name ${name} --restart=always -itd \
-p 3306:3306 \
-e MYSQL_ROOT_PASSWORD=${password} \
mysql:5.7.37-debian \
--character-set-server=utf8mb4 \
--collation-server=utf8mb4_unicode_ci

# mysql 持久化
# 配置目录 -v /etc/mysql:/etc/mysql/conf.d
# 数据目录 -v /var/lib/mysql:/var/lib/mysql
# 日志目录 -v /log:/log 

mkdir /etc/mysql /var/lib/mysql /log  

docker run --name ${name} --restart=always -itd \
-p 3306:3306 \
-v /etc/mysql:/etc/mysql/conf.d \
-v /var/lib/mysql:/var/lib/mysql \
-v /log:/log \
-e MYSQL_ROOT_PASSWORD=${password} \
mysql:5.7.37-debian \
--character-set-server=utf8mb4 \
--collation-server=utf8mb4_unicode_ci
```

## 3.4. rocketmq  -- 不好用


```shell
mkdir /home/namesrv/logs /home/broker/logs /home/rocketmq/store

# Start nameserver
docker run --name ${rmqnamesrv} --restart=always -itd \
-v /home/namesrv/logs:/home/rocketmq/logs \
-p 9876:9876 \
apache/rocketmq:4.9.2 sh mqnamesrv \

# Start Broker
docker run --name ${rmqbroker} --restart=always -itd \
--link ${rmqnamesrv}:namesrv \
-p 10909:10909 \
-p 10911:10911 \
-p 10912:10912 \
-v /home/broker/logs:/home/rocketmq/logs \
-v /home/rocketmq/store:/home/rocketmq/store \
-e "NAMESRV_ADDR=namesrv:9876" \
apache/rocketmq:4.9.2 sh mqbroker \

# rocketmq-dashboard
docker run --name ${rocketmq-dashboard} --restart=always -itd \
-e "JAVA_OPTS=-Drocketmq.namesrv.addr=127.0.0.1:9876" \
-p 9877:8080 \
apacherocketmq/rocketmq-dashboard:latest
```

## 3.5. rabbitmq

```shell
docker run --name #{rabbitmq} --restart=always -itd \
-p 5672:5672 \
-p 15672:15672 \
rabbitmq:3.9-management-alpine
```

## 3.6. seata
```shell
docker run --name #{seata-server} --restart=always -itd \
-p 8091:8091 \
-p 7091:7091 \
-e SEATA_IP=#{ip} \
-e SEATA_PORT=8091 \
seataio/seata-server:1.5.1
```


## 3.7. zipkin

```shell

# 简单部署

docker run --name ${zipkin} --restart=always -itd \
-p 9411:9411 \
openzipkin/zipkin:latest


# 使用mysql
docker run --name zipkin-server --restart=always -itd \
-p 9411:9411 \
-e MYSQL_USER=${username} \
-e MYSQL_PASS=${password} \
-e MYSQL_HOST=${host} \
-e STORAGE_TYPE=mysql \
-e MYSQL_DB=${database} \
-e MYSQL_TCP_PORT=${port} \
openzipkin/zipkin:latest

# 使用es
。。。

```

## 3.8. neo4j
```
docker run --name neo4j --restart=always -itd \
--publish=7474:7474 \
--publish=7687:7687 \
-e 'NEO4J_AUTH=neo4j/secret' \
neo4j:4.4.8
```
