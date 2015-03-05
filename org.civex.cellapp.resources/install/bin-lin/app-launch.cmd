@echo off

call app-env.cmd
set JAVA_PARAMS=-Dlog4j.configuration=%LOG_CONFIG%
set APP_MAIN=org.civex.hostservice.WebServerProxyAppMain

pushd
cd %APP_HOME%
java %JAVA_PARAMS% -jar "%APP_LAUNCHER%" -appHome %APP_HOME% -appMain %APP_MAIN% 
popd
