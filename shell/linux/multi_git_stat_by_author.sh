#!/bin/bash

# -----------------------------
# 参数解析
# -----------------------------
DAYS=1
POST_URL=""

while [[ $# -gt 0 ]]; do
  case "$1" in
    -day)
      DAYS="$2"
      shift 2
      ;;
    -url)
      POST_URL="$2"
      shift 2
      ;;
    *)
      echo "❌ 未知参数: $1"
      echo "用法: $0 [-day N] [-url POST地址]"
      exit 1
      ;;
  esac
done

# -----------------------------
# 仓库列表（两个平行数组）
# -----------------------------
REPO_NAMES=("测试1" "测试2")
REPO_PATHS=(
  "/Users/changjinwei/my-workbench/my-github/m"
  "/Users/changjinwei/my-workbench/my-github/b"
)

# -----------------------------
# 作者映射表（原名=统一名）
# -----------------------------
AUTHOR_MAP_FILE=$(mktemp)
cat > "$AUTHOR_MAP_FILE" <<EOF
changjin wei(魏昌进)=Changjin Wei
ChangJin Wei=Changjin Wei
EOF

# -----------------------------
# 作者名统一映射函数
# -----------------------------
map_author() {
  local input="$1"
  local mapped
  mapped=$(grep -F "$input=" "$AUTHOR_MAP_FILE" | head -n1 | cut -d= -f2)
  if [ -z "$mapped" ]; then echo "$input"; else echo "$mapped"; fi
}

# -----------------------------
# 重定向统计结果到临时文件
# -----------------------------
RESULT_FILE=$(mktemp)
exec > "$RESULT_FILE"

# -----------------------------
# 遍历仓库进行统计
# -----------------------------
for i in "${!REPO_NAMES[@]}"; do
  repo_name="${REPO_NAMES[$i]}"
  repo_path="${REPO_PATHS[$i]}"

  if [ ! -d "$repo_path/.git" ]; then
    echo "**⚠️ 跳过 $repo_name**：不是有效的 Git 仓库"
    continue
  fi

  echo "### 📁 仓库: $repo_name"
  echo "### 🕒 统计范围: 最近 $DAYS 天（排除合并提交）"
  echo "----------------------------------------"

  cd "$repo_path" || continue

  # ✅ 拉取最新代码
  git pull --rebase --quiet

  TMP_FILE=$(mktemp)

  # 获取提交列表
  git log --all --no-merges --since="$DAYS days ago" --format="%H|%an" |
  while IFS='|' read -r commit_hash raw_author; do
    author=$(map_author "$raw_author")
    git show --numstat --format="" "$commit_hash" | awk -v author="$author" '
      NF==3 && $1 ~ /^[0-9]+$/ {
        added[author] += $1;
        deleted[author] += $2;
        changed = 1;
      }
      END {
        if (changed == 1) {
          print author "|" added[author] "|" deleted[author] "|1"
        }
      }'
  done > "$TMP_FILE"

  if [ ! -s "$TMP_FILE" ]; then
    echo "（无变更记录）"
    echo ""
    continue
  fi

  # 汇总每位作者数据
  awk -F"|" '
    {
      added[$1] += $2;
      deleted[$1] += $3;
      total[$1] += $2 + $3;
      commits[$1] += $4;
    }
    END {
      for (a in added) {
        printf "%s|%d|%d|%d|%d\n", a, commits[a], added[a], deleted[a], total[a]
      }
    }
  ' "$TMP_FILE" | sort -t'|' -k2,2nr > "${TMP_FILE}_sorted"

  # 输出结果
  printf "\n%-4s %-12s %-8s %-8s %-8s\n" "作者" "提交次数" "新增" "删除" "总变更"

  while IFS='|' read -r author commit add del total; do
    short_author=$(echo "$author" | LC_CTYPE=UTF-8 cut -c1-4)
    printf "%-4s %-12d %-8d %-8d %-8d\n" "$short_author" "$commit" "$add" "$del" "$total"
  done < "${TMP_FILE}_sorted"

  echo ""

  rm -f "$TMP_FILE" "${TMP_FILE}_sorted"
done

rm -f "$AUTHOR_MAP_FILE"
exec 1>&2  # 恢复 stdout

# -----------------------------
# 如果提供 URL，就 POST 推送
# -----------------------------
if [[ -n "$POST_URL" ]]; then
  JSON_CONTENT=$(sed ':a;N;$!ba;s/\n/\\n/g' "$RESULT_FILE" | sed 's/"/\\"/g')
  PAYLOAD=$(cat <<EOF
{
  "msgtype": "markdown",
  "markdown": {
    "content": "${JSON_CONTENT}"
  }
}
EOF
)
  echo "📡 正在发送统计结果到: $POST_URL"
  curl -s -X POST -H "Content-Type: application/json" -d "$PAYLOAD" "$POST_URL"
  echo -e "\n✅ 发送完成"
fi

rm -f "$RESULT_FILE"
