FROM jboss/keycloak-mysql

EXPOSE 8080

USER root

ADD ./configuration /opt/jboss/keycloak/standalone/configuration
RUN chown -R jboss:jboss /opt/jboss/keycloak/standalone/configuration
ADD ./target /opt/jboss/keycloak/cyclone
RUN /opt/jboss/keycloak/bin/jboss-cli.sh \
            --command="module add --name=org.cyclone.clientowner \
            --resources=/opt/jboss/keycloak/cyclone/cyclone-clientowner.jar \
            --dependencies=org.keycloak.keycloak-core,org.keycloak.keycloak-services,org.keycloak.keycloak-model-jpa,org.keycloak.keycloak-server-spi,javax.ws.rs.api,javax.persistence.api,org.hibernate,org.javassist"

RUN rm -rf /opt/jboss/keycloak/cyclone

USER jboss