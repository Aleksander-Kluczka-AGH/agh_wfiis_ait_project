# build stage
FROM maven:3.9.2-eclipse-temurin-17 AS build-stage
WORKDIR /app
COPY . /app
RUN mvn package

# production stage
FROM tomcat:jdk17-temurin AS production-stage
ARG WAR_FILE=lichess_stats-0.0.1-SNAPSHOT.war
WORKDIR /opt/app
COPY --from=build-stage /app/target/${WAR_FILE} /usr/local/tomcat/webapps/ROOT.war
