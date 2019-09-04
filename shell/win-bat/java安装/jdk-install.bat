@echo off
set path_=%~dp0
set jdk=jdk-8u191-windows-x64.exe
set version=jdk1.8.0_191
set installPath="D:\Program Files\Java\%version%"

%1 mshta vbscript:CreateObject("Shell.Application").ShellExecute("cmd.exe","/c %~s0 ::","","runas",1)(window.close)&&exit

%path_%%jdk% INSTALL_SILENT=Enable INSTALLDIR=%installPath% AUTO_UPDATE=Disable

setx /m JAVA_HOME %installPath%
setx /m CLASSPATH ".;%%JAVA_HOME%%\lib\dt.jar;%%JAVA_HOME%%\lib\tools.jar;"
setx /m Path "%Path%;%%JAVA_HOME%%\bin;%%JAVA_HOME%%\jre\bin;"
pause

