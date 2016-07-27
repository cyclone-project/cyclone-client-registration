package org.cyclone.clientowner;


import org.cyclone.clientowner.jpa.ClientOwner;

/**
 * Created by sturgelose on 27/07/2016.
 */
public class ClientOwnerRepresentation {

    private String id;
    private String owner;
    private String client;

    public ClientOwnerRepresentation() {

    }

    public ClientOwnerRepresentation(ClientOwner clientOwner) {
        id = clientOwner.getId();
        owner = clientOwner.getOwner();
        client = clientOwner.getClient();
    }

    // Get
    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getClient() { return client; }

    // Set

    public void setId(String id) {
        this.id = id;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setClient(String client) { this.client = client; }

}
