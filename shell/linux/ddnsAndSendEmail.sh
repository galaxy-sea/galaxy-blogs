#!/bin/bash

# sh 开机启动 https://blog.csdn.net/github_38336924/article/details/112304663
# 定时任务 https://blog.csdn.net/qianxing111/article/details/80091187
# 定时任务 https://zhuanlan.zhihu.com/p/115082330
# ddns文档 https://help.aliyun.com/document_detail/29776.htm?spm=a2c4g.11186623.0.0.118312d4pB1jdZ#doc-api-Alidns-DescribeDomainRecords
# ddns文档和Aliyun-cli下载 https://help.aliyun.com/document_detail/124923.html
# mail安装 https://blog.csdn.net/weixin_43736596/article/details/115202681
# 获取IP https://www.mebi.me/1766.html

Obtain_IP_ident="curl -s myip.ipip.net/s"
Obtain_IP_myip="curl -s ip.3322.net"
Old_LocalIP=$(head -n +1 /home/changjinwei/Old_LocalIP.txt)

# 第一次检查IP
New_LocalIP=`${Obtain_IP_ident}`
if [ "${Old_LocalIP}" != "${New_LocalIP}" ]
then
    # 第二次检查IP
    check_ip=`${Obtain_IP_myip}`
    if [ "${check_ip}" = "${New_LocalIP}" ]
    then
        Old_LocalIP=${New_LocalIP}
        # aliyun ddns
        # /usr/local/bin/aliyun alidns UpdateDomainRecord --region cn-hangzhou --RecordId 770712821778851840 --RR *.nuc8i7 --Type A --Value "${New_LocalIP}"

        # centOS send mail
        # echo "${New_LocalIP}" | mail -s "nuc8i7 ip change" 469753862@qq.com

        # ubuntu send mail
        echo "${New_LocalIP}" | s-nail -s "nuc8i7 ip change" 469753862@qq.com
        
        # write ip
        echo "${New_LocalIP}" > /home/changjinwei/Old_LocalIP.txt
    fi
fi


# read -p "please input a number: " number

# if [[ $number =~ ^([0-9]{1,2}|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\.([0-9]{1,2}|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\.([0-9]{1,2}|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\.([0-9]{1,2}|1[0-9][0-9]|2[0-4][0-9]|25[0-5])$ ]];then
#     echo "correct"
# else
#     echo "error"
# fi

