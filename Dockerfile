# ===== BUILD STAGE =====
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copia solo pom.xml per caching delle dipendenze
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia il codice sorgente e compila
COPY src ./src
RUN mvn clean package -DskipTests

# ===== RUNTIME STAGE =====
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copia solo il jar costruito dal builder
COPY --from=builder /app/target/*.jar app.jar

    # Espone la porta dell'app Spring Boot
EXPOSE 8080

# Variabile d'ambiente per Java
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Comando di avvio
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
