#!/usr/bin/env bash

rm -rf target/*

mvn package

docker-compose up -d

docker-compose exec keycloak /opt/jboss/keycloak/bin/jboss-cli.sh \
        --command="module remove --name=org.cyclone.clientowner"

docker-compose exec keycloak /opt/jboss/keycloak/bin/jboss-cli.sh \
        --command="module add --name=org.cyclone.clientowner \
        --resources=/opt/jboss/keycloak/target/cyclone-clientowner.jar \
        --dependencies=org.keycloak.keycloak-core,org.keycloak.keycloak-services,org.keycloak.keycloak-model-jpa,org.keycloak.keycloak-server-spi,javax.ws.rs.api,javax.persistence.api,org.hibernate,org.javassist"

docker-compose restart keycloak