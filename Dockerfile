FROM maven:3.9.16-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests


FROM tomcat:10.1-jdk21-temurin

ENV CATALINA_HOME=/usr/local/tomcat

WORKDIR $CATALINA_HOME

RUN rm -rf webapps/*

COPY --from=builder /app/target/campusconnect-1.0.war webapps/ROOT.war

EXPOSE 8080

CMD ["sh", "-c", "sed -i \"s/port=\\\"8080\\\"/port=\\\"${PORT:-8080}\\\"/\" conf/server.xml && catalina.sh run"]