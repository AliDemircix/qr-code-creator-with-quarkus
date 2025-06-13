package com.qrcodeapp.resource;

import com.qrcodeapp.dto.AuthResponse;
import com.qrcodeapp.dto.AuthService;
import com.qrcodeapp.dto.LoginRequest;
import com.qrcodeapp.dto.RegisterRequest;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    AuthService authService;

    @POST
    @Path("/register")
    public Response register(RegisterRequest request) {
        try {
            AuthResponse response = authService.register(request);

            if (response.token != null) {
                return Response.ok(response).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new AuthResponse("Registration failed: " + e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/login")
    public Response login(LoginRequest request) {
        try {
            AuthResponse response = authService.login(request);

            if (response.token != null) {
                return Response.ok(response).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity(response).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new AuthResponse("Login failed: " + e.getMessage()))
                    .build();
        }
    }
}
