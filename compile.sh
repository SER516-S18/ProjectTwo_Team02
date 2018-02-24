#!/bin/sh

SCRIPT_PATH=$(readlink -f "$0")
ROOT_PATH=$(dirname "$SCRIPT_PATH")

LIB_PATH="$ROOT_PATH/lib"

echo 
echo "\033[0;93mWorking dir: $ROOT_PATH"
echo 

cd "$ROOT_PATH"

rm server.jar
rm client.jar

echo 
echo "\033[0;32mCompiling Server"
echo "\033[0;31m"


javac -d . -cp ".:$ROOT_PATH/src:$LIB_PATH/kryo-4.0.1.jar:$LIB_PATH/kryonet-2.22.0-RC1.jar:$LIB_PATH/jfreechart-1.0.13.jar:$LIB_PATH/jcommon-1.0.23.jar:$LIB_PATH/minlog-1.3.0.jar:$LIB_PATH/objenesis-2.1.jar:$LIB_PATH/reflectasm-1.10.1-shaded.jar" $ROOT_PATH/src/server/ServerApp.java

jar cfm server.jar manifest_server.txt lib src server network
rm -rf server 

echo
echo "\033[0;32mCompiling Client"
echo "\033[0;31m"


javac -d . -cp ".:$ROOT_PATH/src:$LIB_PATH/kryo-4.0.1.jar:$LIB_PATH/kryonet-2.22.0-RC1.jar:$LIB_PATH/jfreechart-1.0.13.jar:$LIB_PATH/jcommon-1.0.23.jar:$LIB_PATH/minlog-1.3.0.jar:$LIB_PATH/objenesis-2.1.jar:$LIB_PATH/reflectasm-1.10.1-shaded.jar" $ROOT_PATH/src/client/ClientApp.java

jar cfm client.jar manifest_client.txt lib src client network
rm -rf client
rm -rf network

echo
echo "\033[0;32mCompile SUCCESS!"
echo
echo "\033[0;93mNext:"
echo "\033[0;93mUse 'java -jar server.jar' to start server"
echo "\033[0;93mUse 'java -jar client.jar' to start client"


