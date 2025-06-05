package com.qrcodeapp.resource;

import java.time.LocalDateTime;
import java.util.Optional;

import org.jboss.logging.Logger;

import com.qrcodeapp.dto.UserSubscriptionUpdateRequest;
import com.qrcodeapp.model.User;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    SecurityIdentity identity;

    private static final Logger LOGGER = Logger.getLogger(AuthResource.class);

    @POST
@Path("/sync")
@RolesAllowed("user")
@Transactional
public Response syncUser() {
    String email = identity.getPrincipal().getName();

    // ✅ Log the sync attempt
    LOGGER.infof("Syncing user: %s", email);

    User user = User.find("email", email).firstResult();

    if (user == null) {
        user = new User();
        user.email = email;

        // ✅ Get fullName from Keycloak or fallback to "User"
        String fullName = Optional.ofNullable((String) identity.getAttribute("name")).orElse("User");
        user.fullName = fullName;

        user.registerTime = LocalDateTime.now();
        user.subscriptionType = "free"; // or "trial", your choice
        user.qrCodeLimit = 3;

        user.persist();
    }

    return Response.ok(user).build();
}

    @GET
    @Path("/me")
    @RolesAllowed("user")
    public Response getMe() {
        String email = identity.getPrincipal().getName();
        User user = User.find("email", email).firstResult();
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(user).build(); // includes limits, subscription, etc.
    }

    @PUT
    @Path("/subscription")
    @RolesAllowed("user")
    @Transactional
    public Response updateSubscription(UserSubscriptionUpdateRequest req) {
        String email = identity.getPrincipal().getName();
        User user = User.find("email", email).firstResult();
        if (user == null) {
            return Response.status(404).build();
        }

        user.subscriptionType = req.subscriptionType;
        user.qrCodeLimit = req.qrCodeLimit;
        return Response.ok().build();
    }

}
