package org.cyclone.clientowner.spi;


import org.keycloak.provider.Provider;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

/**
 * Created by sturgelose on 27/07/2016.
 */
public interface ClientOwnerService extends Provider {

    UserRepresentation findOwner(String clientId);

    List<ClientRepresentation> listClients(String clientId);

}
