------------------------------------------------------------
-- Hammerspoon init.lua
-- Ctrl+Cmd+Shift+5：弹出 chooser
-- 1) 直接点击预置尺寸
-- 2) 或者直接键盘输入 1280x720 / 1280 720 / 1280,720 回车应用
-- 不使用 hs.dialog.textPrompt（避免必须点到 Hammerspoon 才能输入）
------------------------------------------------------------

local MODS = { "ctrl", "cmd", "shift" }
local KEY  = "5"

-- px -> points（Retina 下按屏幕 scale 自动换算）
local function pxToPoints(win, wPx, hPx)
  local screen = win:screen()
  local mode = screen and screen:currentMode()
  local scale = (mode and mode.scale) or 1
  return wPx / scale, hPx / scale
end

-- 解析输入：支持 1280x720 / 1280×720 / 1280 720 / 1280,720
local function parseSize(input)
  if not input then return nil end
  input = input:lower()

  local w, h = input:match("(%d+)%s*[x×,%s]%s*(%d+)")
  if not w or not h then return nil end

  w, h = tonumber(w), tonumber(h)
  if not w or not h or w <= 0 or h <= 0 then return nil end
  return w, h
end

-- 封装：按 px 尺寸调整指定窗口并居中
local function resizeWindowToPxAndCenter(win, wPx, hPx)
  if not win then return end
  if win:isFullScreen() then return end

  local w, h = pxToPoints(win, wPx, hPx)

  local screen = win:screen()
  if not screen then return end

  local sf = screen:frame() -- 可用区域（不含菜单栏/Dock）
  local x = sf.x + (sf.w - w) / 2
  local y = sf.y + (sf.h - h) / 2

  win:setFrame(hs.geometry.rect(x, y, w, h))
end

------------------------------------------------------------
-- 预置规则
------------------------------------------------------------
local PRESET_RULES = {
  { title = "JetBrains 1200 × 760", w = 1200, h = 760 },
  { title = "Chrome 1280x800",      w = 1280, h = 800 },
  { title = "Chrome 640x400",       w = 640,  h = 400 },
}

------------------------------------------------------------
-- Chooser（支持直接输入尺寸并回车应用）
------------------------------------------------------------

-- 记录热键触发时的目标窗口，避免 chooser 出现时焦点变化导致拿错窗口
local targetWinId = nil

local chooser = hs.chooser.new(function(choice)
  if not choice then return end

  local win = targetWinId and hs.window.get(targetWinId) or nil
  if not win then return end

  if choice.custom then
    resizeWindowToPxAndCenter(win, choice.w, choice.h)
    win:focus()
    return
  end

  if choice.w and choice.h then
    resizeWindowToPxAndCenter(win, choice.w, choice.h)
    win:focus()
  end
end)

chooser:rows(10)
chooser:searchSubText(true)
chooser:placeholderText("1280x720 / 1280 720 / 1280,720，回车应用")

-- 根据当前输入动态生成 choices
local function buildChoices(query)
  local choices = {}

  local wPx, hPx = parseSize(query)
  if wPx then
    table.insert(choices, {
      text = string.format("自定义：%d × %d（回车应用）", wPx, hPx),
      subText = "应用到当前聚焦窗口（热键触发时的窗口）",
      custom = true,
      w = wPx,
      h = hPx,
    })
  elseif query and query ~= "" then
    table.insert(choices, {
      text = "格式不对：请输入 1280x720 / 1280 720 / 1280,720",
      subText = "继续输入即可",
      valid = false,
    })
  end

  for _, r in ipairs(PRESET_RULES) do
    table.insert(choices, {
      text = r.title,
      subText = string.format("%d x %d px", r.w, r.h),
      w = r.w,
      h = r.h,
    })
  end

  return choices, (wPx ~= nil)
end

chooser:invalidCallback(function(_)
  -- 可选：想提示的话可以用 hs.alert.show("输入格式不正确")
end)

chooser:queryChangedCallback(function(q)
  local list, hasCustom = buildChoices(q)
  chooser:choices(list)

  -- 如果输入能解析出尺寸，把第一行选中，直接回车就应用
  if hasCustom then
    chooser:selectedRow(1)
  end
end)

function ShowResizeChooser()
  local win = hs.window.focusedWindow()
  if not win then return end
  if win:isFullScreen() then return end

  targetWinId = win:id()

  chooser:query("") -- 清空输入
  chooser:choices((buildChoices(""))) -- ✅ 修复：只取第一个返回值
  chooser:show()
end

hs.hotkey.bind(MODS, KEY, ShowResizeChooser)
