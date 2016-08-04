package org.cyclone.clientowner.spi.impl;

import org.cyclone.clientowner.jpa.ClientOwnerEntity;
import org.cyclone.clientowner.spi.ClientOwnerService;
import org.keycloak.connections.jpa.JpaConnectionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;

import org.keycloak.models.jpa.entities.ClientEntity;
import org.keycloak.models.jpa.entities.RealmEntity;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.services.resources.admin.ClientsResource;

import javax.persistence.EntityManager;
import java.util.LinkedList;
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
    public ClientsResource listClients() {

        RealmEntity realm = getEntityManager().find(RealmEntity.class, getRealm().getId());

        List<ClientEntity> companyEntities = getEntityManager()
                .createNamedQuery("getClientsByRealm", ClientEntity.class)
                .setParameter("realm", realm)
                .getResultList();

        List<ClientRepresentation> respresentations = new LinkedList<>();
        for (ClientEntity entity : companyEntities) {
            ClientEntity test = entity;
        }
        ClientsResource result = null;

        return result;
    }


    @Override
    public void close() {

    }
}
