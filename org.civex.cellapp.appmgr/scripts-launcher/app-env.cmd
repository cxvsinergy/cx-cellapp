@echo off
set CELL_PATH=c:/local/1/cells/0/code
set JAVA_HOME=%JAVA_HOME%
set APP_MAVEN_REPO=C:\local\1\devrepo\mavenrepo
set APP_LAUNCHER=%APP_MAVEN_REPO%\org\civex\cellapp\appmgr\0.0.1\appmgr-0.0.1.jar 
set SCRIPT_HOME=%~dp0
set APP_HOME=%SCRIPT_HOME%..
set APP_JMX_PORT=


rem #commands : start,stop,configure,sleep,restart,check