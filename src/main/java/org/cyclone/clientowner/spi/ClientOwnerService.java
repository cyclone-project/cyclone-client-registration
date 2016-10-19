package org.cyclone.clientowner.spi;


import org.cyclone.clientowner.jpa.ClientOwnerEntity;
import org.keycloak.provider.Provider;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.services.resources.admin.ClientsResource;

/**
 * Created by sturgelose on 27/07/2016.
 */
public interface ClientOwnerService extends Provider {

    UserRepresentation findOwner(String clientId);

    ClientsResource listClients();

    ClientOwnerEntity getClientOwnerMatch(String clientId, String userId);

}
