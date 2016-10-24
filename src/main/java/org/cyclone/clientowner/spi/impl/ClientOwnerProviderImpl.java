package org.cyclone.clientowner.spi.impl;

import org.cyclone.clientowner.ClientOwner;
import org.cyclone.clientowner.jpa.ClientOwnerEntity;
import org.cyclone.clientowner.spi.ClientOwnerProvider;
import org.keycloak.connections.jpa.JpaConnectionProvider;
import org.keycloak.models.*;
import org.keycloak.models.jpa.entities.ClientEntity;
import org.keycloak.models.jpa.entities.UserEntity;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the ClientOwnerProvider interface
 */
public class ClientOwnerProviderImpl implements ClientOwnerProvider {

    private final KeycloakSession session;

    public ClientOwnerProviderImpl(KeycloakSession session) {
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


    private ClientEntity findClientEntitybyId(String clientId) {

        List<ClientEntity> clients = getEntityManager()
                .createNamedQuery("getClientById", ClientEntity.class)
                .setParameter("id", clientId)
                .setParameter("realmId", getRealm().getId())
                .getResultList();

        // We should find only one
        if (clients.size() > 1) {
            throw new ModelDuplicateException("There are too many Clients with ID [" + clientId + "].");
        } else if (clients.size() < 1) {
            throw new ModelException("Client model with id [" + clientId + "] not found.");
        }

        return clients.get(0);

    }

    private UserEntity findUserEntitybyId(String userId) {
        List<UserEntity> users = getEntityManager()
                .createNamedQuery("getRealmUserById", UserEntity.class)
                .setParameter("id", userId)
                .setParameter("realmId", getRealm().getId())
                .getResultList();

        // We should find only one
        if (users.size() > 1) {
            throw new ModelDuplicateException("There are too many Users with ID [" + userId + "].");
        } else if (users.size() < 1) {
            throw new ModelException("User model with id [" + userId + "] not found.");
        }

        return users.get(0);
    }

    private ClientOwnerEntity findClientOwnerEntitybyId(String clientOwnerId) {
        List<ClientOwnerEntity> clientOwners = getEntityManager()
                .createNamedQuery("getClientOwnerById", ClientOwnerEntity.class)
                .setParameter("id", clientOwnerId)
                .setParameter("realmId", getRealm().getId())
                .getResultList();

        // We should find only one
        if (clientOwners.size() > 1) {
            throw new ModelDuplicateException("There are too many ClientOwners with ID [" + clientOwnerId + "].");
        } else if (clientOwners.size() < 1) {
            throw new ModelException("ClientOwner model with id [" + clientOwnerId + "] not found.");
        }

        return clientOwners.get(0);
    }

    @Override
    public ClientOwner getClientOwner(String clientOwnerId) {
        return new ClientOwner(findClientOwnerEntitybyId(clientOwnerId));
    }

    @Override
    public ClientOwner addClientOwner(ClientOwner clientOwner) {
        //TODO To be implemented: we need to assign the actual realm. Also, what about the ID assignement?
        return null;
    }

    @Override
    public ClientOwner updateClientOwner(ClientOwner clientOwner) {
        //TODO To be implemented
        return null;
    }

    @Override
    public ClientOwner deleteClientOwner(ClientOwner clientOwner) {
        //TODO To be implemented
        return null;
    }

    @Override
    public ClientModel getClient(ClientOwner clientOwner) {
        return session.getContext().getRealm().getClientById(clientOwner.getOwnerId());
    }

    @Override
    public ClientOwner setClient(ClientModel client, ClientOwner clientOwner) {

        // Find the client
        ClientEntity clientEntity = findClientEntitybyId(client.getId());

        ClientOwnerEntity clientOwnerEntity = findClientOwnerEntitybyId(clientOwner.getId());

        // Update Entity
        clientOwnerEntity
                .setClient(clientEntity);
        getEntityManager().persist(clientOwnerEntity);
        getEntityManager().flush();

        return new ClientOwner(clientOwnerEntity);
    }

    @Override
    public UserModel getOwner(String clientOwnerId) {
        return session
                .getProvider(UserProvider.class)
                .getUserById(findClientOwnerEntitybyId(clientOwnerId).getOwnerId(), session.getContext().getRealm());
    }

    @Override
    public UserModel getOwner(ClientOwner clientOwner) {
        return session.getProvider(UserProvider.class)
                .getUserById(clientOwner.getOwnerId(), session.getContext().getRealm());
    }

    @Override
    public ClientOwner setOwner(UserModel owner, ClientOwner clientOwner) {

        // Find the owner
        UserEntity userEntity = findUserEntitybyId(clientOwner.getId());

        ClientOwnerEntity clientOwnerEntity = findClientOwnerEntitybyId(clientOwner.getId());

        // Update Entity
        clientOwnerEntity
                .setOwner(userEntity);
        getEntityManager().persist(clientOwnerEntity);
        getEntityManager().flush();

        return new ClientOwner(clientOwnerEntity);
    }

    @Override
    public List<ClientOwner> getClientOwnerListbyClient(ClientModel client) {

        List<ClientOwnerEntity> clientOwnerEntities = getEntityManager()
                .createNamedQuery("getClientOwnerByClient", ClientOwnerEntity.class)
                .setParameter("client", findClientEntitybyId(client.getId()))
                .setParameter("realmId", getRealm().getId())
                .getResultList();

        List <ClientOwner> clientOwners = new ArrayList<>();
        for (ClientOwnerEntity clientOwnerEntity: clientOwnerEntities) {
            clientOwners.add(new ClientOwner(clientOwnerEntity));
        }

        return clientOwners;
    }

    @Override
    public List<ClientOwner> getClientOwnerListbyOwner(UserModel owner) {

        List<ClientOwnerEntity> clientOwnerEntities = getEntityManager()
                .createNamedQuery("getClientOwnerByOwner", ClientOwnerEntity.class)
                .setParameter("owner", findUserEntitybyId(owner.getId()))
                .setParameter("realmId", getRealm().getId())
                .getResultList();

        List <ClientOwner> clientOwners = new ArrayList<>();
        for (ClientOwnerEntity clientOwnerEntity: clientOwnerEntities) {
            clientOwners.add(new ClientOwner(clientOwnerEntity));
        }

        return clientOwners;
    }

    @Override
    public ClientOwner getClientOwnerbyClientandOwner(ClientModel client, UserModel owner) {

        List<ClientOwnerEntity> clientOwnerEntities = getEntityManager()
                .createNamedQuery("getClientOwnerByOwnerAndClient", ClientOwnerEntity.class)
                .setParameter("owner", findClientOwnerEntitybyId(owner.getId()))
                .setParameter("client", findClientEntitybyId(client.getId()))
                .setParameter("realmId", getRealm().getId())
                .getResultList();

        // We should find only one
        if (clientOwnerEntities.size() > 1) {
            throw new ModelDuplicateException("There are too many matches with Client ID [" + client.getId() + "] and Owner with ID[" + owner.getId() + "].");
        } else if (clientOwnerEntities.size() < 1) {
            throw new ModelException("Match model with Client ID [" + client.getId() + "] and User ID [" + owner.getId() + "] not found.");
        }

        return new ClientOwner(clientOwnerEntities.get(0));
    }

    @Override
    public void close() {

    }
}
