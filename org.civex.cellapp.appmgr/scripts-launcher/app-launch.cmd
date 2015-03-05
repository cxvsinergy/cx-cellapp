@echo off

call app-env.cmd

set APP_MAIN=org.civex.hostservice.WebServerProxyAppMain
pushd
cd %APP_HOME%
java  -jar "%APP_LAUNCHER%" -appHome %APP_HOME% -appMain %APP_MAIN% 
popd
