package org.cyclone.clientowner.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.cyclone.clientowner.spi.ClientOwnerService;
import org.jboss.resteasy.annotations.cache.NoCache;
import org.keycloak.models.ClientModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.services.resources.admin.ClientsResource;

public class ClientOwnerResource {

    private final KeycloakSession session;

    public ClientOwnerResource(KeycloakSession session) {
        this.session = session;
    }

    @GET
    @Path("")
    @NoCache
    @Produces(MediaType.APPLICATION_JSON)
    public ClientsResource getCompanies() {
        return session.getProvider(ClientOwnerService.class).listClients();
    }

    @GET
    @NoCache
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserRepresentation getCompany(@PathParam("id") final String id) {
        return session.getProvider(ClientOwnerService.class).findOwner(id);
    }

}