package org.cyclone.clientowner;


import org.cyclone.clientowner.jpa.ClientOwnerEntity;

/**
 * Created by sturgelose on 27/07/2016.
 */
public class ClientOwner {

    private String id;
    private String ownerId;
    private String clientId;

    public ClientOwner() {

    }

    public ClientOwner(ClientOwnerEntity clientOwnerEntity) {
        id = clientOwnerEntity.getId();
        ownerId = clientOwnerEntity.getOwnerId();
        clientId = clientOwnerEntity.getClientId();
    }

    // Get
    public String getId() {
        return id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getClientid() { return clientId; }

    // Set

    public void setId(String id) {
        this.id = id;
    }

    public void setOwner(String ownerId) {
        this.ownerId = ownerId;
    }

    public void setClient(String clientId) { this.clientId = this.clientId; }

}
