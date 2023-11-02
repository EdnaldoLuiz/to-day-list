FROM maven:3.8.5-openjdk-17

COPY . .

RUN mvn clean install
