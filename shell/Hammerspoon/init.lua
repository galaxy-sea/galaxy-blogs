------------------------------------------------------------
-- Hammerspoon init.lua
-- Ctrl+Cmd+Shift+5：弹出输入框输入尺寸(px)，并把当前聚焦窗口调整到该尺寸并居中
------------------------------------------------------------

-- 固定快捷键：Ctrl + Command + Shift + 5
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

function PromptAndResizeFocusedWindow()
  local win = hs.window.focusedWindow()
  if not win then return end
  if win:isFullScreen() then return end

  local button, text = hs.dialog.textPrompt(
    "设置窗口尺寸（px）",
    "请输入宽x高，例如：1280 720",
    "",
    "确定",
    "取消"
  )
  if button ~= "确定" then return end

  local wPx, hPx = parseSize(text)
  if not wPx then return end

  local w, h = pxToPoints(win, wPx, hPx)

  local sf = win:screen():frame() -- 可用区域（不含菜单栏/Dock）
  local x = sf.x + (sf.w - w) / 2
  local y = sf.y + (sf.h - h) / 2

  win:setFrame(hs.geometry.rect(x, y, w, h))
end

-- 快捷键绑定
hs.hotkey.bind(MODS, KEY, PromptAndResizeFocusedWindow)
