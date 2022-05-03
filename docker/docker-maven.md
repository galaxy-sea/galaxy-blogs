
<!-- TOC -->

- [1. docker 安装](#1-docker-安装)
    - [1.1. 本机安装](#11-本机安装)
        - [idea连接服务器](#idea连接服务器)
        - [本机环境变量(可选)](#本机环境变量可选)
    - [1.2. 服务器安装](#12-服务器安装)
        - [1.2.1. 安装](#121-安装)
        - [1.2.2. 开启远程端口](#122-开启远程端口)
- [2. docker maven 插件](#2-docker-maven-插件)

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
### 阿里云镜像加速

阿里云开通``容器镜像服务``按照[镜像加速器](https://cr.console.aliyun.com/cn-hangzhou/instances/mirrors)进行配置就可以了


### 1.2.2. 开启远程端口
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


## spring-boot-maven-plugin 使用

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

## dockerfile-maven-plugin

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