Client Owner Manager
====================

This module provides an extension to Keycloak so clients can be linked and managed by specific users.

To deploy, build this directory then take the jar and copy it to providers directory. Alternatively you can deploy as a module by running:


```
KEYCLOAK_HOME/bin/jboss-cli.sh 
    --command="module add 
    --name=org.cyclone.clientowner 
    --resources=target/cyclone-clientowner.jar 
    --dependencies=org.keycloak.keycloak-core,
        org.keycloak.keycloak-services,
        org.keycloak.keycloak-model-jpa,
        org.keycloak.keycloak-server-spi,
        javax.ws.rs.api,
        javax.persistence.api,
        org.hibernate,
        org.javassist"

```

Then registering the provider by editing keycloak-server.json and adding the module to the providers field:

    "providers": [
        ....
        "module:org.cyclone.clientowner"
    ],


