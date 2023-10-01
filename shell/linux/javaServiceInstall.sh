#!/bin/bash

# 初始化变量
# jar的路径 ～/xxx.jar
jarPath=""
# 服务名称 xxx.service
serviceName=""
# Java路径
javaPath="java"
# jvmAgrs 参数
jvmAgrs="-Xmx512m -Xms512m"
#jvmAgrs=""


current_user=$(whoami)

# 写书的文本
serviceText="
# mail@wcj.plus
[Unit]
Description=Spring Boot For #serviceName Application
After=syslog.target network.target

[Service]
User=#current_user
WorkingDirectory=#WorkingDirectory
ExecStart=#javaPath -jar #jvmAgrs #jarPath
SuccessExitStatus=143
TimeoutStopSec=10
Restart=on-failure
RestartSec=30
[Install]
WantedBy=multi-user.target
"


sehllText="
#!/bin/bash
# mail@wcj.plus
case \"\$1\" in
\"log\")
    sudo journalctl -n 1000 -f -u docker.service
    ;;
\"restart\")
    sudo systemctl restart #serviceName.service
    ;;
*)
    echo \"Unknown command: \$1\"
    echo \"Usage: \$0 <command>\"
    exit 1
    ;;
esac
exit 0
"

# 获取参数
process_args() {
    while [ $# -gt 0 ]; do
        case "$1" in
        -jarPath)
            shift
            jarPath="$1"
            ;;
        -serviceName)
            shift
            serviceName="$1"
            ;;
        -javaPath)
            shift
            javaPath="$1"
            ;;
        -jvmAgrs)
            shift
            jvmAgrs="$1"
            ;;
        *)
            # 如果传递了未知的参数，你可以选择在这里处理或报错
            echo "未知的参数: $1"
            exit 1
            ;;
        esac
        shift
    done
}

# 相对路径转换为绝对路径
convert_to_absolute_path() {
    local relative_path="$1"
    local absolute_path=""

    # 使用readlink或realpath来获取绝对路径
    if [ -x "$(command -v readlink)" ]; then
        absolute_path="$(readlink -f "$relative_path")"
    elif [ -x "$(command -v realpath)" ]; then
        absolute_path="$(realpath "$relative_path")"
    else
        echo "无法找到readlink或realpath命令，请安装其中一个工具。"
        exit 1
    fi
    echo "$absolute_path"
}

replace_keywords() {
    serviceText="${serviceText//#javaPath/$javaPath}"
    serviceText="${serviceText//#jarPath/$jarPath}"
    serviceText="${serviceText//#jvmAgrs/$jvmAgrs}"
    serviceText="${serviceText//#serviceName/$serviceName}"
    serviceText="${serviceText//#current_user/$current_user}"
    serviceText="${serviceText//#WorkingDirectory/"$(dirname "$jarPath")"}"
}

systemctl_enable() {
    sudo echo "$serviceText" | sudo tee "/etc/systemd/system/$serviceName.service" > /dev/null
    sudo systemctl daemon-reload
    sudo systemctl enable "/etc/systemd/system/$serviceName.service"
    sudo systemctl restart "$serviceName.service"
}

systemctl_status() {
    if [ "$(sudo systemctl is-active "$serviceName")" == "active" ]; then
        echo "服务 $serviceName 启动成功！"
    else
        echo "服务 $serviceName 启动失败或状态不明确。"
    fi
}

write_shell() {
    sehllText="${sehllText//#serviceName/$serviceName}"
    sudo echo "$serviceText" | sudo tee "$(dirname "$jarPath")/$serviceName.sh" > /dev/null
}

process_args "$@"

jarPath=$(convert_to_absolute_path "$jarPath")
serviceName=$(basename "${jarPath//.jar/}")

replace_keywords
write_shell
systemctl_enable
