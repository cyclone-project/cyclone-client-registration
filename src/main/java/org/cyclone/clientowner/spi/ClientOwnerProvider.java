package org.cyclone.clientowner.spi;


import org.cyclone.clientowner.ClientOwner;
import org.keycloak.models.ClientModel;
import org.keycloak.models.UserModel;
import org.keycloak.provider.Provider;

import java.util.List;


/**
 * Interface defining the ClientOwner Provider
 */
public interface ClientOwnerProvider extends Provider {

    // CRUD

    ClientOwner getClientOwner(String clientOwnerId);

    ClientOwner addClientOwner(ClientOwner clientOwner);

    ClientOwner updateClientOwner(ClientOwner clientOwner);

    ClientOwner deleteClientOwner(ClientOwner clientOwner);


    // Get client of a clientOwner

    ClientModel getClient(ClientOwner clientOwner);


    // Set client of a clientOwner

    ClientOwner setClient(ClientModel client, ClientOwner clientOwner);


    // Get owner (users) of a clientOwner

    UserModel getOwner(String clientOwnerId);

    UserModel getOwner(ClientOwner clientOwner);


    // Set owner of a clientOwner

    ClientOwner setOwner(UserModel owner, ClientOwner clientOwner);


    // Get clientOwners list given a Client

    List<ClientOwner> getClientOwnerListbyClient (ClientModel client);

    // Get clientOwners list given an Owner (user)

    List<ClientOwner> getClientOwnerListbyOwner (UserModel owner);

    // Get clientOwner given a Client and an Owner

    ClientOwner getClientOwnerbyClientandOwner(ClientModel client, UserModel user);

}
