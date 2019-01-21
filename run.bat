@echo off
SET USER=misael
SET DATABASE=oxxo_db
SET PASSWORD=

java -jar -Duser=%USER% -Ddatabase=%DATABASE% -Dpassword=%PASSWORD%  bin\Oxxo.jar %*
