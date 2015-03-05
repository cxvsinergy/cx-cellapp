@echo off
call ..\..\cx-resources\bin\env.cmd
set SCRIPT_HOME=%~dp0
set LOG_CONFIG=file:/%APP_HOME%/config/log4j.properties
set APP_HOME=%SCRIPT_HOME%..
set APP_JMX_PORT=


rem #commands : start,stop,configure,sleep,restart,check