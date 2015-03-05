@echo off

call app-env.cmd

java -jar "%JAR%" -appHome %CELL_PATH%/%APP_NAME% -appMain %APP_MAIN%  -stop
