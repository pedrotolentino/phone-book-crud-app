FROM eclipse-temurin:17-jdk-focal

WORKDIR /phone-book-app

COPY .mvn .mvn
COPY mvnw mvnw
COPY pom.xml .

RUN ./mvnw dependency:go-offline

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]