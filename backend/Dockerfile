FROM openjdk:17-jdk-slim

WORKDIR /backend

COPY ./ .

RUN chmod +x mvnw

EXPOSE 8080

CMD ["./mvnw", "spring-boot:run"]