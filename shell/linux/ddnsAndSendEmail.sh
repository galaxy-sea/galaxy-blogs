#!/bin/bash

# sh 开机启动 https://blog.csdn.net/github_38336924/article/details/112304663
# ddns文档 https://help.aliyun.com/document_detail/29776.htm?spm=a2c4g.11186623.0.0.118312d4pB1jdZ#doc-api-Alidns-DescribeDomainRecords
# ddns文档和Aliyun-cli下载 https://help.aliyun.com/document_detail/124923.html
# mail安装 https://blog.csdn.net/weixin_43736596/article/details/115202681
# 获取IP https://www.mebi.me/1766.html

Obtain_IP_ident="curl -s myip.ipip.net/s"
Obtain_IP_myip="curl -s ip.3322.net"
Old_LocalIP="192.168.0.1"

while true
do
    # 第一次检查IP
    New_LocalIP=`${Obtain_IP_ident}`
    if [ "${Old_LocalIP}" != "${New_LocalIP}" ]
    then
        # 第二次检查IP
        check_ip=`${Obtain_IP_myip}`
        if [ "${check_ip}" == "${New_LocalIP}" ]
        then
            Old_LocalIP=${New_LocalIP}
            aliyun alidns UpdateDomainRecord --region cn-hangzhou --RecordId 770712821778851840 --RR *.nuc8i7 --Type A --Value "${New_LocalIP}"
            echo "${New_LocalIP}" | mail -s "nuc8i7 ip change" 469753862@qq.com
        fi
    fi
    sleep 60
done
