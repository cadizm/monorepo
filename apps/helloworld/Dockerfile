FROM gcr.io/bazel-public/bazel:7.0.2 as BUILD

WORKDIR /src

COPY . .
RUN bazel build //src/java/com/cadizm/springboot:helloworld

FROM amazoncorretto:21-alpine
COPY --from=BUILD /src/bazel-bin/src/java/com/cadizm/springboot/helloworld.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]
