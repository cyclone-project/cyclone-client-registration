package org.cyclone.clientowner.rest;

import org.cyclone.clientowner.jpa.ClientOwnerEntity;
import org.cyclone.clientowner.spi.ClientOwnerService;
import org.jboss.resteasy.annotations.cache.NoCache;
import org.keycloak.models.*;
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
    private final UserModel user;

    public ClientOwnerRestResource(KeycloakSession session) {
        this.session = session;

        // Generate authentication
        this.auth = new AppAuthManager().authenticateBearerToken(session, session.getContext().getRealm());

        // Save the user variable if we got proper authentication
        this.user = this.auth == null ? null : this.auth.getUser();
    }

    /**
     * Endpoint to list all clients of a user
     * auth/realm/{realm}/client-registration/
     *
     * @return ClientRepresentation of the new client
     */
    @GET
    @Path("")
    @NoCache
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientOwnerResource() {

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

    /**
     * Endpoint to create clients
     * auth/realm/{realm}/client-registration/
     *
     * @return ClientRepresentation of the new client
     */
    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @NoCache
    @Produces(MediaType.APPLICATION_JSON)
    public Response setClientOwnerResource(ClientRepresentation clientRepresentation) {
        ClientModel client = RepresentationToModel.createClient(session, session.getContext().getRealm(), clientRepresentation, true);
        return null;
    }

    /**
     * Endpoint to update clients
     * auth/realm/{realm}/client-registration/
     *
     * @return ClientRepresentation of the new client
     */
    @PUT
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @NoCache
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateClientOwnerResource(ClientRepresentation clientRepresentation) {
        return null;
    }

    /**
     * Endpoint to delete clients
     * auth/realm/{realm}/client-registration/
     *
     * @return ClientRepresentation of the new client
     */
    @DELETE
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @NoCache
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteClientOwnerResource(ClientRepresentation clientRepresentation) {
        return null;
    }

    /**
     * Endpoint to get a specific client of a user
     * auth/realm/{realm}/client-registration/{clientId}
     *
     * @return ClientRepresentation of the new client
     */
    @GET
    @Path("{id}")
    @NoCache
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientOwnerResourceList(String clientId) {
        checkRealmAdmin();
        ClientOwnerEntity match = getClientOwnership(clientId);
        return null;
    }









    /**
     * Example on how to use authentication
     */
    @Path("registerclient-auth")
    public Response getClientResourceAuthenticated() {
        checkRealmAdmin();
        return null;
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

    /**
     * Checks that a client is under the ownership of the user executing the request
     * @param clientId Id of the client to matched against the user
     * @return Entity representing the match
     */
    private ClientOwnerEntity getClientOwnership(String clientId) {
        ClientOwnerEntity match = session.getProvider(ClientOwnerService.class).getClientOwnerMatch(clientId, user.getId());

        if (match == null){
            throw new NotFoundException("Couldn't find the specified client for this user owner");
        }

        return match;
    }

}