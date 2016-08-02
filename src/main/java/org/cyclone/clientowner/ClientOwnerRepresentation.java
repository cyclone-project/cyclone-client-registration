package org.cyclone.clientowner;


import org.cyclone.clientowner.jpa.ClientOwnerEntity;
import org.keycloak.models.jpa.entities.ClientEntity;
import org.keycloak.models.jpa.entities.UserEntity;

/**
 * Created by sturgelose on 27/07/2016.
 */
public class ClientOwnerRepresentation {

    private String id;
    private UserEntity owner;
    private ClientEntity client;

    public ClientOwnerRepresentation() {

    }

    public ClientOwnerRepresentation(ClientOwnerEntity clientOwnerEntity) {
        id = clientOwnerEntity.getId();
        owner = clientOwnerEntity.getOwner();
        client = clientOwnerEntity.getClient();
    }

    // Get
    public String getId() {
        return id;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public ClientEntity getClient() { return client; }

    // Set

    public void setId(String id) {
        this.id = id;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public void setClient(ClientEntity client) { this.client = client; }

}
