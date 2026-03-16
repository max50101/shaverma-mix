FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# ---------- common-events ----------
COPY common-events/pom.xml /app/common-events/pom.xml

# ---------- auth ----------
COPY auth_server/pom.xml /app/auth_server/pom.xml

# Скачать зависимости заранее (кешируется)
RUN mvn -f /app/common-events/pom.xml -B -q -DskipTests dependency:go-offline
RUN mvn -f /app/auth_server/pom.xml -B -q -DskipTests dependency:go-offline

# ---------- теперь копируем src ----------
COPY common-events/src /app/common-events/src
COPY auth_server/src /app/auth_server/src

# ---------- билд ----------
RUN mvn -f /app/common-events/pom.xml -DskipTests install
RUN mvn -f /app/auth_server/pom.xml -DskipTests package

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/auth_server/target/*.jar app.jar

EXPOSE 9000
ENTRYPOINT ["java","-jar","/app/app.jar"]