@echo off
if not exist bin mkdir bin
if not exist bin\classes mkdir bin\classes
SET OUTPUT_DIR=bin\classes
SET FLAGS=-d -Xlint:all -deprecation -d %OUTPUT_DIR% -encoding UTF-8 -g
dir /s /B *.java > sourcefiles.txt
javac %FLAGS% @sourcefiles.txt

pushd bin
jar -cvmf ..\MANIFEST.MF Oxxo.jar -C classes .
popd
