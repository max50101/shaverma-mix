FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# ---------- common-events ----------
COPY common-events/pom.xml /app/common-events/pom.xml

RUN mvn -f /app/common-events/pom.xml -B -DskipTests dependency:go-offline

COPY common-events/src /app/common-events/src

RUN mvn -f /app/common-events/pom.xml -B -DskipTests clean install

# ---------- api ----------
COPY shaverma-cloud/pom.xml /app/shaverma-cloud/pom.xml

RUN mvn -f /app/shaverma-cloud/pom.xml -B -DskipTests dependency:go-offline

COPY shaverma-cloud/src /app/shaverma-cloud/src

RUN mvn -f /app/shaverma-cloud/pom.xml -B -DskipTests clean package

FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/shaverma-cloud/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]