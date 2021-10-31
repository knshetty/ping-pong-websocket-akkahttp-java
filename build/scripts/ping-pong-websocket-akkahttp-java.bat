@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  ping-pong-websocket-akkahttp-java startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and PING_PONG_WEBSOCKET_AKKAHTTP_JAVA_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto execute

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\ping-pong-websocket-akkahttp-java.jar;%APP_HOME%\lib\akka-http-jackson_2.13-10.1.13.jar;%APP_HOME%\lib\akka-http_2.13-10.1.13.jar;%APP_HOME%\lib\akka-stream_2.13-2.5.32.jar;%APP_HOME%\lib\akka-http-core_2.13-10.1.13.jar;%APP_HOME%\lib\akka-actor_2.13-2.5.32.jar;%APP_HOME%\lib\akka-protobuf_2.13-2.5.32.jar;%APP_HOME%\lib\ssl-config-core_2.13-0.3.8.jar;%APP_HOME%\lib\akka-parsing_2.13-10.1.13.jar;%APP_HOME%\lib\scala-java8-compat_2.13-0.9.0.jar;%APP_HOME%\lib\scala-parser-combinators_2.13-1.1.2.jar;%APP_HOME%\lib\scala-library-2.13.1.jar;%APP_HOME%\lib\reactive-streams-1.0.2.jar;%APP_HOME%\lib\jackson-databind-2.10.2.jar;%APP_HOME%\lib\config-1.3.3.jar;%APP_HOME%\lib\jackson-annotations-2.10.2.jar;%APP_HOME%\lib\jackson-core-2.10.2.jar


@rem Execute ping-pong-websocket-akkahttp-java
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %PING_PONG_WEBSOCKET_AKKAHTTP_JAVA_OPTS%  -classpath "%CLASSPATH%" services.websocket.WebsocketServerForEcho %*

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable PING_PONG_WEBSOCKET_AKKAHTTP_JAVA_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%PING_PONG_WEBSOCKET_AKKAHTTP_JAVA_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
