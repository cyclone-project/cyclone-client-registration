package org.cyclone.clientowner.spi.impl;

import org.cyclone.clientowner.spi.ClientOwnerService;
import org.cyclone.clientowner.spi.ClientOwnerServiceProviderFactory;
import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

/**
 * Created by sturgelose on 27/07/2016.
 */
public class ClientOwnerServiceProviderFactoryImpl implements ClientOwnerServiceProviderFactory {
    @Override
    public ClientOwnerService create(KeycloakSession session) {
        return new ClientOwnerServiceImpl(session);
    }

    @Override
    public void init(Config.Scope config) {

    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return "clientOwnerImpl";
    }
}
