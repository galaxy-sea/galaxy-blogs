
<!-- TOC -->

- [1. CentOS安装Nginx](#1-centos安装nginx)
  - [1.1. CentOS安装Nginx](#11-centos安装nginx)
  - [1.2. systemctl 管理 Nginx](#12-systemctl-管理-nginx)
  - [1.3. Nginx 的配置文件和最佳实践](#13-nginx-的配置文件和最佳实践)
- [2. Nginx](#2-nginx)

<!-- /TOC -->
# 1. CentOS安装Nginx

## 1.1. CentOS安装Nginx

- CentOS
- yum

1. 安装命令
```shell
sudo yum install epel-release
sudo yum install nginx
```
2. 启动命令
```shell
sudo systemctl start nginx
```
3. 检查命令
```shell
sudo systemctl status nginx
```

3. 开放端口
```shell
sudo firewall-cmd --permanent --zone=public --add-service=http
sudo firewall-cmd --permanent --zone=public --add-service=https
sudo firewall-cmd --reload
```
5. 验证启动
   
在浏览器中打开 http://YOUR_IP，您将看到默认的 Nginx 欢迎页

## 1.2. systemctl 管理 Nginx

启动 Nginx
```shell
sudo systemctl start nginx
```
停止 Nginx
```shell
sudo systemctl stop nginx
```
重启 Nginx
```shell
sudo systemctl restart nginx
```
修改 Nginx 配置后，重新加载
```shell
sudo systemctl reload nginx
```
设置开机启动 Nginx
```shell
sudo systemctl enable nginx
```
关闭开机启动 Nginx
```shell
sudo systemctl disable nginx
```

## 1.3. Nginx 的配置文件和最佳实践
- 通过以上方式安装的 Nginx，所有相关的配置文件都在 ``/etc/nginx/`` 目录中
- Nginx 的主配置文件是 ``/etc/nginx/nginx.conf``
- 为了使 Nginx 配置更易于维护，建议为每个服务（域名）创建一个单独的配置文件
- 每一个独立的 Nginx 服务配置文件都必须以 .conf 结尾，并存储在`` /etc/nginx/conf.d`` 目录中。您可以根据需求，创建任意多个独立的配置文件。
- 独立的配置文件，建议遵循以下命名约定，比如你的域名是 ``kaifazhinan.com``，那么你的配置文件的应该是这样的 ``/etc/nginx/conf.d/kaifazhinan.com.conf``，如果你在一个服务器中部署多个服务，当然你也可以在文件名中加上 Nginx 转发的端口号，比如 ``kaifazhinan.com.3000.conf``，这样做看起来会更加友好
- 如果你的配置中有很多重复的代码，那么建议你创建一个 ``/etc/nginx/snippets`` 文件夹，在这里面存放所有会被复用的代码块，然后在各个需要用到的 Nginx 的配置文件中引用进去，这样可以更方便管理和修改。
- Nginx 日志文件（``access.log`` 和 ``error.log`` ）位于 ``/var/log/nginx/`` 目录中。建议为每个独立的服务配置不同的访问权限和错误日志文件，这样查找错误时，会更加方便快捷。

# 2. Nginx


