@echo off
set path_=%~dp0
REM %1 mshta vbscript:CreateObject("Shell.Application").ShellExecute("cmd.exe","/c %~s0 ::","","runas",1)(window.close)&&exit

set pull="%path_%pull.bat"
set push="%path_%push.bat"
set errorInfo="错误: 系统找不到指定的注册表项或值。"
set one="HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows\CurrentVersion\Group Policy\Scripts"
set two="HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows\CurrentVersion\Group Policy\State\Machine\Scripts"
REM set three="HKEY_LOCAL_MACHINE\SOFTWARE\WOW6432Node\Microsoft\Windows\CurrentVersion\Group Policy\Scripts"
REM set four="HKEY_LOCAL_MACHINE\SOFTWARE\WOW6432Node\Microsoft\Windows\CurrentVersion\Group Policy\State\Machine\Scripts"

REG QUERY %one%\Startup
if errorlevel==1 (
    goto createFile
)
goto addScipts
exit

:createFile
echo --
echo *********添加文件夹*********
echo --
    REG ADD %one% /f
    REG ADD %one%\Startup
    REG ADD %one%\Startup\0
    REG ADD %one%\Startup\0 /v DisplayName /d 本地组策略
    REG ADD %one%\Startup\0 /v FileSysPath /d C:\Windows\System32\GroupPolicy\Machine
    REG ADD %one%\Startup\0 /v GPO-ID /d LocalGPO
    REG ADD %one%\Startup\0 /v GPOName /d 本地组策略
    REG ADD %one%\Startup\0 /v SOM-ID /d Local
    REG ADD %one%\Startup\0 /v PSScriptOrder /t REG_DWORD /d 0x1

    REG COPY %one%\Startup %one%\Shutdown /s

:addScipts
echo --
echo *********添加脚本*********
echo --
for /l %%i in (0,1,1000) do (
    REG QUERY %one%\Startup\0\%%i
    if errorlevel==1 (
        REG ADD %one%\Startup\0\%%i
        REG ADD %one%\Startup\0\%%i /v Parameters 
        REG ADD %one%\Startup\0\%%i /v Script /d %pull%
        REG ADD %one%\Startup\0\%%i /v ExecTime /t REG_QWORD /d 0x0
        REG ADD %one%\Startup\0\%%i /v IsPowershell /t REG_DWORD /d 0x0

        REG COPY %one%\Startup\0\%%i %one%\Shutdown\0\%%i /s
        REG ADD %one%\Shutdown\0\%%i /v Script /d %push% /f

        REG COPY %one% %two% /s /f
        goto END
    )
)

:END
echo --
echo *********操作完成*********
echo --
pause
exit

