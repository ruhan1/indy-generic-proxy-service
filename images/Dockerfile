FROM registry.access.redhat.com/ubi9/openjdk-11:1.21-1 AS builder

USER root

WORKDIR /workspace

COPY ./ .

# The buildah --secret flag requires a corresponding --mount=type=secret instruction to work, and the id in both must match exactly.
RUN --mount=type=secret,id=pulp-secret/dkliban.jks \
  --mount=type=secret,id=pulp-secret/settings-pulp.xml \
  mvn -B package -Dquarkus.package.type=uber-jar \
  -s /run/secrets/pulp-secret/settings-pulp.xml \
  -Djavax.net.ssl.keyStore=/run/secrets/pulp-secret/dkliban.jks \
  -Djavax.net.ssl.keyStorePassword=changeit

FROM registry.access.redhat.com/ubi9/openjdk-11-runtime:1.21-1

USER root

RUN mkdir -p /deployment/log /deployment/config && \
  chmod -R 777 /deployment/log /deployment/config

COPY --from=builder /workspace/target/*-runner.jar /deployment/indy-generic-proxy-service-runner.jar
RUN chmod +r /deployment/indy-generic-proxy-service-runner.jar

COPY images/start-service.sh /deployment/start-service.sh
RUN chmod +x /deployment/*

WORKDIR /

ENTRYPOINT ["bash", "-c"]
CMD ["/deployment/start-service.sh"]