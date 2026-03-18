FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# ---------- common-events ----------
COPY common-events/pom.xml /app/common-events/pom.xml
RUN mvn -f /app/common-events/pom.xml -B -DskipTests dependency:go-offline

COPY common-events/src /app/common-events/src
RUN mvn -f /app/common-events/pom.xml -B -DskipTests clean install

# ---------- auth ----------
COPY auth_server/pom.xml /app/auth_server/pom.xml
RUN mvn -f /app/auth_server/pom.xml -B -DskipTests dependency:go-offline

COPY auth_server/src /app/auth_server/src
RUN mvn -f /app/auth_server/pom.xml -B -DskipTests clean package

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/auth_server/target/*.jar app.jar

EXPOSE 9000
ENTRYPOINT ["java","-jar","/app/app.jar"]