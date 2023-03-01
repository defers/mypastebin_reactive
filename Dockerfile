FROM openjdk:11
MAINTAINER defers.com
COPY target/mypastebin-0.0.1-SNAPSHOT.jar mypastebin-0.0.1.jar
ENTRYPOINT ["java","-jar","/mypastebin-0.0.1.jar"]