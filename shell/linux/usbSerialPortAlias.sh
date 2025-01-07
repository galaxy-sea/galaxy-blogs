#!/bin/bash
# mail@wcj.plus




kernelText="ACTION==\"add\", KERNEL==\"ttyUSB*\", KERNELS==\"#KERNELS\", MODE:=\"0777\", SYMLINK+=\"#customName\""



# 使用find命令搜索/sys/class/tty/目录下以ttyUSB开头的设备，并排序
mapfile -t devices < <(sudo find /sys/class/tty/ -name 'ttyUSB*' | sort)
# 检查是否找到设备
if [ ${#devices[@]} -eq 0 ]; then
    echo "未找到符合条件的设备"
    exit 1
fi

# 输出设备列表
echo "请选择一个设备："
PS3="输入编号： "
select device in "${devices[@]}"; do
    if [ -n "$device" ]; then
        selected_device="$device"
        echo "你选择了设备: $selected_device"
        break
    else
        echo "请选择有效的选项。"
        exit 1
    fi
done

mapfile -t kernels < <(sudo udevadm info --attribute-walk --name=/dev/ttyUSB0 | grep KERNELS)

# KERNELS列表
echo "请选择一个KERNELS："
PS3="输入编号： "
select kernel in "${kernels[@]}"; do
    if [ -n "$kernel" ]; then
        selected_kernel="$kernel"
        echo "你选择了KERNELS: $kernel"
        break
    else
        echo "请选择有效的选项。"
        exit 1
    fi
done

selected_kernel=$(echo "$selected_kernel" | grep -o "\"[^\"]*\"")
selected_kernel="${selected_kernel//\"/}"


# 提示用户输入
echo "设备编号：$selected_device。KERNEL编号：$selected_kernel"
echo "为你的串口取一个别名吧，如：ttyUSBXXX"
read -r customName


kernelText="${kernelText//#KERNELS/$selected_kernel}"
kernelText="${kernelText//#customName/$customName}"

echo "$kernelText" | sudo tee -a "/etc/udev/rules.d/100-custom_rules.rules" > /dev/null

echo "串口名称：/dev/$customName"
echo "配置地址：/etc/udev/rules.d/100-custom_rules.rules"
echo "串口信息：$kernelText"

sudo service udev reload
sudo service udev restart


echo "USB设备插拔一下， USB设备插拔一下， USB设备插拔一下， USB设备插拔一下， USB设备插拔一下"
echo "USB设备插拔一下， USB设备插拔一下， USB设备插拔一下， USB设备插拔一下， USB设备插拔一下"
echo "USB设备插拔一下， USB设备插拔一下， USB设备插拔一下， USB设备插拔一下， USB设备插拔一下"
