Client Owner Manager
====================

This module provides an extension to Keycloak so clients can be linked and managed by specific users.

An example of all the possible requests that can be done to the API can be found in https://www.getpostman.com/collections/d5601317d3dbcf9c6421. 
This queries can be tested using Postman.



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

Another deployment option is to use IntelliJ Idea and load the project data, which includes a basic build system that 
deploys the package automatically to Keycloak and enables debug settings.
