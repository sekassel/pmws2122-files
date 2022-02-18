FROM gradle:jdk17 as builder
COPY --chown=gradle:gradle . /app
WORKDIR /app
RUN gradle shadowJar

FROM openjdk:17-alpine
WORKDIR /app
COPY --from=builder /app/build/libs .
EXPOSE 3000
CMD ["java", "-jar", "server-1.0.0-all.jar"]