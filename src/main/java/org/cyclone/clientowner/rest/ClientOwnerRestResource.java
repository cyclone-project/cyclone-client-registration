package org.cyclone.clientowner.rest;

import org.cyclone.clientowner.ClientOwner;
import org.cyclone.clientowner.spi.ClientOwnerProvider;
import org.jboss.resteasy.annotations.cache.NoCache;
import org.keycloak.models.ClientModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.UserModel;
import org.keycloak.models.utils.ModelToRepresentation;
import org.keycloak.models.utils.RepresentationToModel;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.services.managers.AppAuthManager;
import org.keycloak.services.managers.AuthenticationManager;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

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
     * @return ClientRepresentation of all the clients underneath this user
     */
    @GET
    @Path("")
    @NoCache
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientOwnerResourceList() {
        checkRealmAdmin();
        // Get all the clientOwner entities for this user
        List<ClientOwner> clientOwners = cop().getClientOwnerListbyOwner(this.user);

        // Generate all the representations for each of the clients
        List<ClientRepresentation> clientRepresentations = new ArrayList<>();
        for (ClientOwner clientOwner: clientOwners) {
            clientRepresentations.add(ModelToRepresentation.toRepresentation(cop().getClient(clientOwner)));
        }

        return Response.ok(clientRepresentations).build();
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
    public Response setClientOwnerResource(final @Context UriInfo uriInfo, ClientRepresentation clientRepresentation) {

        checkRealmAdmin();

        // Create client from the representation
        ClientModel client = RepresentationToModel.createClient(session, session.getContext().getRealm(), clientRepresentation, true);

        // Create the model and assign parameters
        ClientOwner newClientOwner = new ClientOwner();
        newClientOwner.setClient(clientRepresentation.getId());
        newClientOwner.setOwner(this.user.getId()); //Assign it to the actual user

        cop().addClientOwner(newClientOwner);

        // Redirect the user to the new created content
        return Response.created(uriInfo.getAbsolutePathBuilder().path(client.getId()).build()).build();
    }

    /**
     * Endpoint to update clients
     * auth/realm/{realm}/client-registration/{clientId}
     *
     * @return ClientRepresentation of the new client
     */
    @PUT
    @Path("{clientId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @NoCache
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateClientOwnerResource(ClientRepresentation clientRepresentation) {
        checkRealmAdmin();
        checkClientOwnership(clientRepresentation.getClientId());
        //TODO To be implemented
        return null;
    }

    /**
     * Endpoint to delete clients
     * auth/realm/{realm}/client-registration/
     *
     * @return ClientRepresentation of the new client
     */
    @DELETE
    @Path("{clientId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @NoCache
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteClientOwnerResource(ClientRepresentation clientRepresentation) {
        checkRealmAdmin();
        checkClientOwnership(clientRepresentation.getClientId());
        //TODO To be implemented
        return null;
    }

    /**
     * Endpoint to get a specific client of a user
     * auth/realm/{realm}/client-registration/{clientId}
     *
     * @return ClientRepresentation of the new client
     */
    @GET
    @Path("{clientId}")
    @NoCache
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientOwnerResource(String clientId) {
        checkRealmAdmin();
        checkClientOwnership(clientId);

        // Get the client
        ClientModel client = session.getContext().getRealm().getClientById(clientId);

        // Generate the representation from the client
        ClientRepresentation clientRepresentation = ModelToRepresentation.toRepresentation(client);
        return Response.ok(clientRepresentation).build();
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
     * @param clientId ID of the client to check the ownership of
     */
    private void checkClientOwnership(String clientId) {

        ClientModel client = session.getContext().getRealm().getClientById(clientId);

        if (client == null) {
            throw new NotFoundException("Couldn't find the specified client.");
        }


        if (cop().getClientOwnerbyClientandOwner(client, this.user) == null) {
            throw new NotFoundException("Couldn't find an ownership relationship over the selected client.");
        }
    }

    private ClientOwnerProvider cop () {
        return session.getProvider(ClientOwnerProvider.class);
    }

}