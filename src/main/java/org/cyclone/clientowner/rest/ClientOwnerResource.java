package org.cyclone.clientowner.rest;

import org.jboss.resteasy.annotations.cache.NoCache;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmProvider;
import org.keycloak.models.UserProvider;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public class ClientOwnerResource {

    private final KeycloakSession session;

    public ClientOwnerResource(KeycloakSession session) {
        this.session = session;
    }

    /**
     *
     * @return
     */
    @GET
    @Path("")
    @NoCache
    @Produces(MediaType.APPLICATION_JSON)
    public String getClients() {
        Object userProvider = session.getProvider(UserProvider.class);
        Object realmProvider = session.getProvider(RealmProvider.class);

        String name = session.getContext().getRealm().getDisplayName();
        if (name == null) {
            name = session.getContext().getRealm().getName();
        }
        return "Hello " + name;
    }

    @GET
    @NoCache
    @Path("{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getClients(@PathParam("id") final String id) {
        return "This is the id " + id;
    }

}