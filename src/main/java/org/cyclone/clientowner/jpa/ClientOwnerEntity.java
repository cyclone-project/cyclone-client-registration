package org.cyclone.clientowner.jpa;

import org.keycloak.models.jpa.entities.ClientEntity;
import org.keycloak.models.jpa.entities.UserEntity;

import javax.persistence.*;

@Entity
@Table(name = "CLIENT_OWNER")
@NamedQueries({
    @NamedQuery(
            name = "findByRealm",
            query = "select co from ClientOwnerEntity co where co.realmId = :realmId"
    )
})
public class ClientOwnerEntity {

    // Column definitions

    @Id
    @Column(name = "ID")
    private String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="OWNER")
    private UserEntity owner;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CLIENT")
    private ClientEntity client;

    @Column(name = "REALM_ID", nullable = false)
    private String realmId;

    // Get

    public String getId() {
        return id;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public ClientEntity getClient() { return client; }

    public String getRealmId() {
        return realmId;
    }

    // Set

    public void setId(String id) {
        this.id = id;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public void setClient(ClientEntity client) { this.client = client; }

    public void setRealmId(String realmId) {
        this.realmId = realmId;
    }

}
