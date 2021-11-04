# ping-pong-websocket-akkahttp-java
A basic websocket-server implemented using Akka HTTP stream-based websocket

This is an **echo** websocket service. Following technologies were used to bring it to life:
- 	**[Java](https://oracle.com/java/ "Java")** programming language
- 	**[Akka HTTP](https://akka.io "Akka HTTP")** stream-based websocket
- 	**[Gradle](https://gradle.org "Gradle")** for build automation including [IntelliJ](https://www.jetbrains.com/idea/ "IntelliJ") IDE

### URI of this websocket service:
`ws://localhost:8080/echo`

### Future (ToDo):
- Keep-alive support

## Useful references:
Akka Documentation
- https://developer.lightbend.com/guides/akka-http-quickstart-java/
- https://doc.akka.io/docs/akka-http/10.1.13/server-side/websocket-support.html

Examples
- https://github.com/akka/akka-http/blob/v10.1.13/docs/src/test/java/docs/http/javadsl/server/WebSocketCoreExample.java
- https://github.com/akka/akka-http/blob/v10.1.13/docs/src/test/java/docs/http/javadsl/server/WebSocketRoutingExample.java
- https://github.com/JakubDziworski/Akka-Streams-Websocket-Game-Server

Heroku Deployment
- https://blog.rockthejvm.com/akka-http-to-heroku-in-x-minutes/
- https://devcenter.heroku.com/articles/deploying-gradle-apps-on-heroku#the-procfile
- https://github.com/heroku/gradle-getting-started
- https://devcenter.heroku.com/articles/getting-started-with-gradle-on-heroku
- https://devcenter.heroku.com/articles/setting-the-http-port-for-java-applications
- https://devcenter.heroku.com/articles/websockets
- https://devcenter.heroku.com/articles/play-java-websockets-advanced
- https://devcenter.heroku.com/articles/websocket-security

------------

# Guide: How this Akka-HTTP server project was bootstrapped

## Aim: Bootstrap a minimal akka-http project with below aspects in-mind

- Java programming language
- Gradle as a primary build-tool
- Cloud deployment ready (i.e. a Heroku-App)

## STEP 1. How to seed a starter project

### Aim: (a) Seeding a minimal starter project from official quick-start template using SBT(Simple Build Tool) repl and then (b) migrate to Intellij IDEA as the IDE

> **Note!** Here's the instructions on how to seed a basic starter project:
> https://github.com/akka/akka-http-quickstart-java.g8

Here's my journey to seed a minimal akka-http project:

**i. In the terminal run the below SBT command:**

`$ sbt new akka/akka-http-java-seed.g8`

**ii. Now, this sbt project template will prompt for following parameters:**

> **Note!** Due to issues of mix java/scala fat-jar compilation with in the latest release of 10.2.x. Our preferred Akka-HTTP version is 10.1.x

> **Note!** Akka HTTP 10.1.x is compatible with Akka 2.5 and any later 2.x versions released

- name:  “Your Preferred Poject Name ”
- akka-http-version:  10.1.13 (last release among 10.1.x)
- akka-version:  2.5.32 (last release among 2.5.x)
- organisation: org.your-company-name
- package: services

**iii. Now, with the help of  Intellij IDEA, open the above created starter project as instructed below:**

1. File > New > Project from Existing Sources… (And choose the project parent directory)
2. Select >> Import project from external model
3. Select >> Gradle

**iv. Now, purge the below unwanted artifacts that were carried forward from the seed:**

1. src/main/java/services/QuickstartApp.java
2. src/main/java/services/UserRegistry.java
3. src/main/java/services/UserRoutes.java
4. src/test/java/services/UserRoutesTest.java

## STEP 2. Setup GIT version control for this project

**i. In the terminal run the below GIT command:**

`$ git init`

**ii. Now, create '.gitignore' file with the content as seen in this project**

> **Note!** this file is located in project's root directory

**iii. Followed by these GIT commands:**

`$ git add .`

`$ git commit -m “Initial commit”`

## STEP 3. Make Heroku Ready

Prepare this project for cloud deployment as a Heroku App

**i. Create below three files in the project’s root directory, with respective contents:**

1. system.properties
```
java.runtime.version=11
```
2. settings.gradle
```
rootProject.name = 'your-project-name'
```
3. Procfile
```
web: java -jar build/libs/your-project-name-all.jar -Dhttp.port=$PORT
```

**ii. Now, amend the 'build.gradle' file with the content as seen in this project**

> **Note!** this file is located in project's root directory
