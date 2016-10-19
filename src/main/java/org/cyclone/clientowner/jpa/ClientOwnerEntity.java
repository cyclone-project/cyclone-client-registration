package org.cyclone.clientowner.jpa;

import org.keycloak.models.ClientModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.jpa.entities.ClientEntity;
import org.keycloak.models.jpa.entities.UserEntity;

import javax.persistence.*;

@Entity
@Table(name = "CLIENT_OWNER",
        uniqueConstraints = {@UniqueConstraint(
        columnNames = {"ID"}
)})

@NamedQueries({
        @NamedQuery(
                name = "findByRealm",
                query = "select co from ClientOwnerModel co where co.realmId = :realmId"
        ),
        @NamedQuery(
                name = "getClientOwnerById",
                query = "select co from ClientOwnerModel co where co.id = :id and co.realmId = :realmId"
        ),
        @NamedQuery(
                name = "getClientOwnerByOwnerAndClient",
                query = "select co from ClientOwnerModel co where co.owner = :owner and co.client = :client and co.realmId = :realmId"
        ),
        @NamedQuery(
                name = "getClientOwnerByOwner",
                query = "select co from ClientOwnerModel co where co.owner = :owner  and co.realmId = :realmId"
        ),
        @NamedQuery(
                name = "getClientOwnerByClient",
                query = "select co from ClientOwnerModel co where  co.client = :client and co.realmId = :realmId"
        )
})
public class ClientOwnerEntity {

    // Column definitions

    @Id
    @Column(
            name = "ID",
            length = 36
    )
    @Access(AccessType.PROPERTY)
    protected String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OWNER")
    private UserEntity owner;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENT")
    private ClientEntity client;

    @Column(name = "REALM_ID", nullable = false)
    private String realmId;

    // Get

    public String getId() {
        return id;
    }

    public String getOwnerId() {
        return owner.getId();
    }

    public String getClientId() {
        return client.getId();
    }

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

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    public void setRealmId(String realmId) {
        this.realmId = realmId;
    }
}
