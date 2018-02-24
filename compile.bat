@echo off
set ROOT_PATH=%~dp0

set LIB_PATH=%ROOT_PATH%lib


echo Working dir; %ROOT_PATH%

cd %ROOT_PATH%

del /f /Q server.jar
del /f /Q client.jar

echo Compiling Server


javac -d . -cp "%ROOT_PATH%src;%LIB_PATH%\kryo-4.0.1.jar;%LIB_PATH%\kryonet-2.22.0-RC1.jar;%LIB_PATH%\jfreechart-1.0.13.jar;%LIB_PATH%\jcommon-1.0.23.jar;%LIB_PATH%\minlog-1.3.0.jar;%LIB_PATH%\objenesis-2.1.jar;%LIB_PATH%\reflectasm-1.10.1-shaded.jar" "%ROOT_PATH%src\server\ServerApp.java"

jar cfm server.jar manifest_server.txt lib src server network
del /f /Q server 

echo Compiling Client


javac -d . -cp "%ROOT_PATH%src;%LIB_PATH%\kryo-4.0.1.jar;%LIB_PATH%\kryonet-2.22.0-RC1.jar;%LIB_PATH%\jfreechart-1.0.13.jar;%LIB_PATH%\jcommon-1.0.23.jar;%LIB_PATH%\minlog-1.3.0.jar;%LIB_PATH%\objenesis-2.1.jar;%LIB_PATH%\reflectasm-1.10.1-shaded.jar" "%ROOT_PATH%src\client\ClientApp.java"

jar cfm client.jar manifest_client.txt lib src client network
del /f /Q client
del /f /Q network


echo Compile SUCCESS!
echo Next;
echo Use 'java -jar server.jar' to start server
echo Use 'java -jar client.jar' to start client