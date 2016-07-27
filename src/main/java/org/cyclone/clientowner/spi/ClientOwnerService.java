package org.cyclone.clientowner.spi;


import org.cyclone.clientowner.ClientOwnerRepresentation;
import org.cyclone.clientowner.jpa.ClientOwner;
import org.keycloak.provider.Provider;
import org.keycloak.representations.idm.ClientRepresentation;

/**
 * Created by sturgelose on 27/07/2016.
 */
public interface ClientOwnerService extends Provider {

    ClientOwnerRepresentation findOwner(String clientId);

    ClientOwner addClient(ClientRepresentation client);

}
