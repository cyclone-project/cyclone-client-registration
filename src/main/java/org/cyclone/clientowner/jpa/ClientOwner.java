package org.cyclone.clientowner.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "CLIENT_OWNER")
@NamedQueries({ @NamedQuery(name = "findByRealm", query = "from ClientOwner where realmId = :realmId") })
public class ClientOwner {

    // Column definitions

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "OWNER", nullable = false)
    private String owner;

    @Column(name = "CLIENT", nullable = false)
    private String client;

    @Column(name = "REALM_ID", nullable = false)
    private String realmId;

    // Get

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getClient() { return client; }

    public String getRealmId() {
        return realmId;
    }

    // Set

    public void setId(String id) {
        this.id = id;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setClient(String client) { this.client = client; }

    public void setRealmId(String realmId) {
        this.realmId = realmId;
    }

}
