#!/usr/bin/env bash

docker-compose exec keycloak /opt/jboss/keycloak/bin/jboss-cli.sh \
        --command="module remove --name=org.cyclone.clientowner"