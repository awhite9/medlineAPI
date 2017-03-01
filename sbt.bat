@REM SBT launcher script
SET SBT_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=9999
.\sbt-dist\bin\sbt.bat %*
