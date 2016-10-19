package org.cyclone.clientowner.rest;

import org.jboss.resteasy.annotations.cache.NoCache;
import org.keycloak.models.ClientModel;
import org.keycloak.models.ClientTemplateModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.UserProvider;
import org.keycloak.models.utils.ModelToRepresentation;
import org.keycloak.models.utils.RepresentationToModel;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.services.managers.AppAuthManager;
import org.keycloak.services.managers.AuthenticationManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Objects;

public class ClientOwnerRestResource {

    private final KeycloakSession session;
    private final AuthenticationManager.AuthResult auth;

    public ClientOwnerRestResource(KeycloakSession session) {
        this.session = session;

        // Generate authentication
        this.auth = new AppAuthManager().authenticateBearerToken(session, session.getContext().getRealm());
    }

    /**
     * Endpoint to create clients
     * auth/realm/{realm}/client-registration/registerclient/
     *
     * @return ClientRepresentation of the new client
     */
    @GET
    @Path("")
    @NoCache
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientResource() {

        ClientTemplateModel clientTemplate = null;
        for (ClientTemplateModel template: session.getContext().getRealm().getClientTemplates()) {
            if (Objects.equals(template.getName(), "Test")){
                clientTemplate = template;
            }
        }
        UserProvider userProvider = session.getProvider(UserProvider.class);

        // This gets the user
        this.auth.getUser();

        // Add a new client and set the template to it
        ClientModel client = session.getContext().getRealm().addClient("hello");
        client.setClientTemplate(clientTemplate);
        ClientRepresentation representation = ModelToRepresentation.toRepresentation(client);
        return Response.ok(representation).build();
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @NoCache
    @Produces(MediaType.APPLICATION_JSON)
    public Response setClientResource(ClientRepresentation clientRepresentation) {
        ClientModel client = RepresentationToModel.createClient(session, session.getContext().getRealm(), clientRepresentation, true);
        return null;
    }

    /**
     * Auth endpoint to create clients
     * auth/realm/{realm}/client-registration/registerclient-auth/
     *
     * @return ClientRepresentation of the new client
     */
    @Path("registerclient-auth")
    public ClientOwnerResource getClientResourceAuthenticated() {
        checkRealmAdmin();
        return new ClientOwnerResource(session);
    }

    /**
     * Checks that the user has 'admin' role
     */
    private void checkRealmAdmin() {
        if (auth == null) {
            throw new NotAuthorizedException("Bearer");
        } else if (auth.getToken().getRealmAccess() == null || !auth.getToken().getRealmAccess().isUserInRole("admin")) {
            throw new ForbiddenException("Does not have realm admin role");
        }
    }

}