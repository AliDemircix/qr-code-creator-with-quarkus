package com.qrcodeapp.resource;

import com.qrcodeapp.dto.AuthRequest;
import com.qrcodeapp.dto.AuthResponse;
import com.qrcodeapp.model.User;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.mindrot.jbcrypt.BCrypt;
import org.jboss.logging.Logger;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    private static final Logger LOGGER = Logger.getLogger(AuthResource.class);

    @POST
    @Path("/register")
    @Transactional
    public Response register(AuthRequest request) {
        if (request.email == null || request.password == null || request.fullName == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new AuthResponse("Missing required fields"))
                    .build();
        }

        if (User.find("email", request.email).firstResult() != null) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(new AuthResponse("Email already registered"))
                    .build();
        }

        User user = new User();
        user.email = request.email;
        user.fullName = request.fullName;
        user.password = BCrypt.hashpw(request.password, BCrypt.gensalt()); // hash password
        user.persist();

        return Response.ok(new AuthResponse("User registered successfully")).build();
    }
}
