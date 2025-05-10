#!/bin/bash

log_dir="/Users/changjinwei/Library/Application Support/io.github.clash-verge-rev.clash-verge-rev/logs/service"
output_file="./domain_stats.txt"
temp_file="./domain_stats_new.txt"

# 自动创建空文件
[ -f "$output_file" ] || touch "$output_file"

# 支持的后缀列表
suffixes=(
  "com"
  "cn"
  "com.hk"
  "com.jp"
)

# Step 1: 提取域名并统计（带默认逻辑）
find "$log_dir" -name "*.log" -print0 | xargs -0 awk -v suffix_list="$(IFS=,; echo "${suffixes[*]}")" '
  BEGIN {
    n = split(suffix_list, raw_suffixes, ",")
    for (i = 1; i <= n; i++) {
      suffixes[i] = raw_suffixes[i]
    }
  }

  {
    for (i = 1; i <= NF; i++) {
      if ($i == "-->") {
        split($(i+1), hp, ":")
        host = hp[1]
        split(host, parts, ".")
        plen = length(parts)
        matched = 0

        # 尝试匹配最长后缀
        for (j = 1; j <= length(suffixes); j++) {
          split(suffixes[j], sfx_parts, ".")
          sfx_len = length(sfx_parts)

          if (plen >= sfx_len + 1) {
            found = 1
            for (k = 0; k < sfx_len; k++) {
              if (parts[plen - k] != sfx_parts[sfx_len - k]) {
                found = 0
                break
              }
            }

            if (found) {
              domain = parts[plen - sfx_len] "."
              for (k = 1; k <= sfx_len; k++) {
                domain = domain parts[plen - sfx_len + k]
                if (k < sfx_len) domain = domain "."
              }
              freq[domain]++
              matched = 1
              break
            }
          }
        }

        # 未匹配任何已知后缀 → 使用默认规则：最后两段
        if (!matched && plen >= 2) {
          domain = parts[plen - 1] "." parts[plen]
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

# Step 2: 合并旧数据（保留 chatgpt.com/openai.com 固定）
awk '
  BEGIN {
    fixed["chatgpt.com"] = 1
    fixed["openai.com"] = 1
  }

  # 旧统计
  FILENAME == "'"$output_file"'" {
    if ($1 == "@note") {
      count = $2
      getline
      domain_line = $0
      domain = substr($1, 3)
      comment = substr($0, index($0, $2))
      old_count[domain] = count
      old_note[domain] = comment
    }
    next
  }

  # 新统计
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

# Step 3: 格式化输出，加头尾
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
