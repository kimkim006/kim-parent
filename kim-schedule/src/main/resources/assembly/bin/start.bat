echo off

set APP_NAME=${project.build.finalName}.jar

set SPRING_CONFIG_LOCATION=../config/application.yml,../config/banner.txt
cd ..
cd bin
set CONFIG= -Dlogging.path=../logs -Dlogging.config=../config/logback-spring.xml -Dspring.config.location=%SPRING_CONFIG_LOCATION%

set DEBUG_OPTS=
if ""%1"" == ""debug"" (
   set DEBUG_OPTS= -Xloggc:../logs/gc.log -verbose:gc -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=../logs 
   goto debug
)

set JMX_OPTS=
if ""%1"" == ""jmx"" (
   set JMX_OPTS= -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9888 -Dcom.sun.management.jmxremote.ssl=FALSE -Dcom.sun.management.jmxremote.authenticate=FALSE 
   goto jmx
)

echo "Starting the %APP_NAME%"
echo "loading config: %SPRING_CONFIG_LOCATION%"
java -Xms512m -Xmx512m -server %DEBUG_OPTS% %JMX_OPTS% %CONFIG% -jar ../lib/%APP_NAME%
goto end

:debug
echo "debug"
java -Xms512m -Xmx512m -server %DEBUG_OPTS% %CONFIG% -jar ../lib/%APP_NAME%
goto end

:jmx
java -Xms512m -Xmx512m -server %JMX_OPTS% %CONFIG% -jar ../lib/%APP_NAME%
goto end

:end
pause