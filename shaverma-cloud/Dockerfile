FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# ---------- common-events ----------
COPY common-events/pom.xml /app/common-events/pom.xml

# ---------- api ----------
COPY shaverma-cloud/pom.xml /app/shaverma-cloud/pom.xml

# ---------- preload dependencies ----------
RUN mvn -f /app/common-events/pom.xml -B -q -DskipTests dependency:go-offline
RUN mvn -f /app/shaverma-cloud/pom.xml -B -q -DskipTests dependency:go-offline

# ---------- sources ----------
COPY common-events/src /app/common-events/src
COPY shaverma-cloud/src /app/shaverma-cloud/src

# ---------- build ----------
RUN mvn -f /app/common-events/pom.xml -DskipTests clean install
RUN mvn -f /app/shaverma-cloud/pom.xml -DskipTests clean package

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/shaverma-cloud/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]