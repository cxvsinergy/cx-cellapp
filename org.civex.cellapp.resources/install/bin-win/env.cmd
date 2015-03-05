@echo off

set ENV_HOME=Z:
set CELLID=0
set CELL_PATH=%ENV_HOME%/cells/%CELLID%/code
set JAVA_HOME=%JAVA_HOME%
set M2_REPO=%ENV_HOME%/devrepo/mavenrepo
set APP_LAUNCHER=%M2_REPO%\org\civex\cellapp\appmgr\0.0.1-SNAPSHOT\appmgr-0.0.1-SNAPSHOT.jar 
set JAVA_LAUNCHER=start java
