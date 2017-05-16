FROM jboss/keycloak-mysql:3.0.0.Final

EXPOSE 8080

USER root

ADD ./configuration /opt/jboss/keycloak/standalone/configuration
RUN chown -R jboss:jboss /opt/jboss/keycloak/standalone/configuration
ADD ./target /opt/jboss/keycloak/cyclone
RUN /opt/jboss/keycloak/bin/jboss-cli.sh \
            --command="module add --name=org.cyclone.clientowner \
            --resources=/opt/jboss/keycloak/cyclone/cyclone-clientowner.jar \
            --dependencies=org.keycloak.keycloak-core,org.keycloak.keycloak-server-spi,org.keycloak.keycloak-server-spi-private,javax.ws.rs.api"

USER jboss