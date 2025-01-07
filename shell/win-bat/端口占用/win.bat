@echo off
mode con cols=80 lines=25
set /p port=输入查询端口: 
netstat -ano|findstr %port%

set /p pid=输入终止PID:
taskkill /pid %pid% /f
pause
