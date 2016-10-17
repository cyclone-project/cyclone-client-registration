#!/usr/bin/env bash

docker-compose up -d keycloak
docker-compose exec keycloak /opt/jboss/keycloak/bin/jboss-cli.sh \
        --command="module add --name=org.cyclone.clientowner \
        --resources=/opt/jboss/target/cyclone-clientowner.jar \
        --dependencies=org.keycloak.keycloak-core,org.keycloak.keycloak-services,org.keycloak.keycloak-model-jpa,org.keycloak.keycloak-server-spi,javax.ws.rs.api,javax.persistence.api,org.hibernate,org.javassist"