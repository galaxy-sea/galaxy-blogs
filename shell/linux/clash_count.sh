#!/bin/bash

log_dir="/Users/changjinwei/Library/Application Support/io.github.clash-verge-rev.clash-verge-rev/logs/service"
output_file="./domain_stats.txt"
temp_file="./domain_stats_new.txt"

# 自动创建空文件
[ -f "$output_file" ] || touch "$output_file"

# Step 1: 生成新的统计数据
find "$log_dir" -name "*.log" -print0 | xargs -0 awk '
  {
    for (i = 1; i <= NF; i++) {
      if ($i == "-->") {
        split($(i+1), hp, ":")
        host = hp[1]
        split(host, parts, ".")
        n = length(parts)
        if (n >= 2) {
          domain = parts[n-1] "." parts[n]
          freq[domain]++
        }
      }
    }
  }
  END {
    freq["chatgpt.com"] = 9999999
    freq["openai.com"] = 9999999

    note["chatgpt.com"] = "+ChatGPT FlClash proxy"
    note["openai.com"] = "+ChatGPT FlClash proxy"

    for (d in freq) {
      if (!(d in note)) {
        note[d] = "+Free Clash Verge proxy"
      }
      printf "%s\t%d\t%s\n", d, freq[d], note[d]
    }
  }
' > "$temp_file"

# Step 2: 合并旧数据
awk '
  BEGIN {
    fixed["chatgpt.com"] = 1
    fixed["openai.com"] = 1
  }

  # 旧数据
  FILENAME == "'"$output_file"'" {
    if ($1 == "@note") {
      count = $2
      getline
      split($1, parts, "\\.")
      domain = parts[2] "." parts[3]
      gsub(/^\*\./, "", domain)
      old_count[domain] = count
      old_note[domain] = substr($0, index($0, parts[2]))
    }
    next
  }

  # 新数据
  FILENAME == "'"$temp_file"'" {
    domain = $1
    count = $2
    comment = $3
    for (i = 4; i <= NF; i++) {
      comment = comment " " $i
    }
    new_count[domain] = count
    new_note[domain] = comment
  }

  END {
    for (d in new_count) {
      if (d in fixed) {
        total = 9999999
        note = new_note[d]
      } else {
        total = (d in old_count ? old_count[d] : 0) + new_count[d]
        note = new_note[d]
      }
      data[d] = total "\t*." d "\t" note
      processed[d] = 1
    }

    for (d in old_count) {
      if (!(d in processed)) {
        data[d] = old_count[d] "\t*." d "\t" old_note[d]
      }
    }

    for (d in data) {
      print data[d]
    }
  }
' "$output_file" "$temp_file" | sort -nr -k1,1 > "${output_file}.tmp"

# Step 3: 写入完整格式，包括头部和尾部
{
  echo "[SwitchyOmega Conditions]"
  echo "@with result"
  echo ""

  awk -F '\t' '
    {
      print "@note " $1
      print $2 " " $3
    }
  ' "${output_file}.tmp"

  echo ""
  echo "* +direct"
} > "$output_file"

# 清理
rm -f "$temp_file" "${output_file}.tmp"
