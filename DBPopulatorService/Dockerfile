FROM openjdk:8-jdk-alpine

RUN apk --no-cache add curl
RUN apk --no-cache add mongodb-tools

MAINTAINER nizamiislamovs@gmail.com

#Debugging
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=n
EXPOSE 8000

#ENV SLACK_URL=${SLACK_URL}
#ENV MAILTRAP_USERNAME=${MAILTRAP_USERNAME}
#ENV MAILTRAP_PASSWORD=${MAILTRAP_PASSWORD}
VOLUME /tmp
EXPOSE 8585
ARG DEPENDENCY=build/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.dbpopulator.app.AppApplication"]
