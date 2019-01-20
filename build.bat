@echo off
if not exist bin mkdir bin
SET OUTPUT_DIR=bin
javac -g -Xlint:all -d %OUTPUT_DIR% src\com\proyecto\Main.java

pushd bin
jar -cvmf ..\MANIFEST.MF Oxxo.jar com\proyecto\*.class
popd
