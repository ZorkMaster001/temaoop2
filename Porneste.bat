@echo off
setlocal
cd /d "%~dp0"
set "JAVA_RUN=%USERPROFILE%\.jdks\temurin-17.0.20.1\bin\java.exe"
if exist "%JAVA_RUN%" goto run
if defined JAVA_HOME set "JAVA_RUN=%JAVA_HOME%\bin\java.exe"
if exist "%JAVA_RUN%" goto run
set "JAVA_RUN=java"
:run
"%JAVA_RUN%" -jar Biblioteca.jar
if errorlevel 1 pause