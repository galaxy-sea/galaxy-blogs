@echo off
REM TIMEOUT /T 60
REM set path_=%~dp0
REM %1 mshta vbscript:CreateObject("Shell.Application").ShellExecute("cmd.exe","/c %~s0 ::","","runas",1)(window.close)&&exit
cd %~d0
cd %~dp0
cd ../
git pull