FROM openjdk:17-jre-slim

WORKDIR /app

COPY target/vending-machine.jar /app/vending-machine.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
