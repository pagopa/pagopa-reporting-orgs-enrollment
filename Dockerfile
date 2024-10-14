FROM maven:3.8.4-jdk-11-slim@sha256:04f8e5ba4a6a74fb7f97940bc75ac7340520728d2fb051ecc5c9ecbb9ba28b48 as buildtime
WORKDIR /build
COPY . .
RUN mvn clean package -Dmaven.test.skip=true


FROM adoptopenjdk/openjdk11:alpine-jre@sha256:14c221828cb2fe042de52ccf46d3a8e77f6c8d9cae75d22c8d84e768409e9faf as builder
COPY --from=buildtime /build/target/*.jar application.jar
RUN java -Djarmode=layertools -jar application.jar extract


FROM ghcr.io/pagopa/docker-base-springboot-openjdk17:v2.2.0@sha256:b866656c31f2c6ebe6e78b9437ce930d6c94c0b4bfc8e9ecc1076a780b9dfb18

COPY --chown=spring:spring  --from=builder dependencies/ ./
COPY --chown=spring:spring  --from=builder snapshot-dependencies/ ./
# https://github.com/moby/moby/issues/37965#issuecomment-426853382
RUN true
COPY --chown=spring:spring  --from=builder spring-boot-loader/ ./
COPY --chown=spring:spring  --from=builder application/ ./
