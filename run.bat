@echo off
SET USER=misael
SET DATABASE=oxxo_db
SET PASSWORD=%1

java -jar -Duser=%USER% -Ddatabase=%DATABASE% -Dpassword=%PASSWORD%  bin\Oxxo.jar
