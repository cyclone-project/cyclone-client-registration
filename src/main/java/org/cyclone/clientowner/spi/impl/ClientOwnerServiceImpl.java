package org.cyclone.clientowner.spi.impl;

import org.cyclone.clientowner.spi.ClientOwnerService;
import org.keycloak.connections.jpa.JpaConnectionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by sturgelose on 27/07/2016.
 */
public class ClientOwnerServiceImpl implements ClientOwnerService {

    private final KeycloakSession session;

    public ClientOwnerServiceImpl(KeycloakSession session) {
        this.session = session;
        if (getRealm() == null) {
            throw new IllegalStateException("The service cannot accept a session without a realm in it's context.");
        }
    }

    private EntityManager getEntityManager() {
        return session.getProvider(JpaConnectionProvider.class).getEntityManager();
    }

    protected RealmModel getRealm() {
        return session.getContext().getRealm();
    }

    @Override
    public UserRepresentation findOwner(String clientId) {
        return getEntityManager().find(UserRepresentation.class, clientId);
    }

    @Override
    public List<ClientRepresentation> listClients(String clientId) {
        return null;
    }


    @Override
    public void close() {

    }
}
