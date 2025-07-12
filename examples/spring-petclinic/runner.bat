@echo off
setlocal

IF "%~1" == "" (
    ECHO ERROR: No argument provided.
    ECHO Usage: %~nx0 "my_pg_file.pg"
    pause
    EXIT /B 1
)

REM Define the relative path to the folder containing the JAR
set "jarFolder=..\..\target"

REM Define the base name of your JAR file (without version or extension)
set "jarBaseName=packagraph"

REM Initialize a variable for the found JAR file
set "foundJar="

REM Search for the JAR file using wildcards in the specified folder
REM /B means bare format (no heading, file sizes, or summary)
REM This will find files like "myjar-0.0.1-SNAPSHOT.jar", "myjar-1.2.3.jar", etc.
for /f "delims=" %%f in ('dir /b "%jarFolder%\%jarBaseName%*SNAPSHOT.jar"') do (
    set "foundJar=%%f"
    goto :jarFound
)

:jarFound
if not defined foundJar (
    echo Error: No JAR file found matching "%jarBaseName%*.jar" in "%jarFolder%".
    goto :eof
)

echo Found JAR: %foundJar%
echo.

REM Construct the full path to the JAR file
set "fullJarPath=%jarFolder%\%foundJar%"

REM Run the Java application
echo Running java -jar "%fullJarPath%"
java -jar "%fullJarPath%" %1

endlocal
pause