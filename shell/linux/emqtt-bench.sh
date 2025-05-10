#!/bin/bash

# 参数解析
DURATION_MIN=$1       # 测试时长（分钟）
TOTAL_MESSAGES=$2     # 所有客户端加起来要发送的总消息数
CLIENTS=$3            # 并发客户端数
INPUT_BROKER=$4       # 可选：自定义 broker 地址
BROKER=${INPUT_BROKER:-"mqtt://broker.emqx.io"}

# 去除 mqtt:// 前缀
BROKER=${BROKER#"mqtt://"}

# 每个客户端平均要发多少消息（向上取整）
MESSAGES_PER_CLIENT=$(( (TOTAL_MESSAGES + CLIENTS - 1) / CLIENTS ))

# 计算发送间隔（毫秒）
TOTAL_DURATION_MS=$(( DURATION_MIN * 60 * 1000 ))
INTERVAL_MS=$(( TOTAL_DURATION_MS / MESSAGES_PER_CLIENT ))

# 输出测试信息
echo "开始压力测试："
echo "- 测试时长       ：$DURATION_MIN 分钟"
echo "- 总消息数       ：$TOTAL_MESSAGES"
echo "- 并发客户端     ：$CLIENTS"
echo "- 每客户端消息数 ：$MESSAGES_PER_CLIENT"
echo "- 消息间隔       ：$INTERVAL_MS 毫秒"
echo "- MQTT Broker    ：$BROKER"

# 执行测试
emqtt-bench pub \
  -h "$BROKER" \
  -t "test/topic" \
  -c "$CLIENTS" \
  -n "$MESSAGES_PER_CLIENT" \
  -I "$INTERVAL_MS"

