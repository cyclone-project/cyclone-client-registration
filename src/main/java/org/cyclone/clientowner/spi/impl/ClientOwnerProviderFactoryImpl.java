package org.cyclone.clientowner.spi.impl;

import org.cyclone.clientowner.spi.ClientOwnerProvider;
import org.cyclone.clientowner.spi.ClientOwnerProviderFactory;
import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

/**
 * Created by sturgelose on 27/07/2016.
 */
public class ClientOwnerProviderFactoryImpl implements ClientOwnerProviderFactory {
    @Override
    public ClientOwnerProvider create(KeycloakSession session) {
        return new ClientOwnerProviderImpl(session);
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
