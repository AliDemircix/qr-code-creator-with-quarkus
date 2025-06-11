package com.qrcodeapp.resource;

import com.qrcodeapp.dto.AuthRequest;
import com.qrcodeapp.dto.LoginRequest;
import com.qrcodeapp.dto.LoginResponse;
import com.qrcodeapp.dto.UserDto;
import com.qrcodeapp.model.User;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import jakarta.transaction.Transactional;
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

    @POST
    @Path("/register")
    @Transactional
    public Response register(AuthRequest req) {
        if (User.find("email", req.email).firstResult() != null) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Email already registered").build();
        }
        User.add(req.email, req.password, "user", req.fullName);
        return Response.ok("User registered successfully").build();
    }

    @POST
    @Path("/login")
    public Response login(LoginRequest req) {
        User user = User.find("email", req.email).firstResult();
        if (user == null || !BcryptUtil.matches(req.password, user.password)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Invalid credentials").build();
        }
        String token = Jwt.issuer("qrcodeapp")
                .upn(user.email)
                .groups(user.role) // or a list of roles if you have more than one
                .claim("role", user.role)
                .sign();
        System.out.println("Generated JWT Token: " + token);
        LoginResponse loginResponse = new LoginResponse(token, UserDto.fromEntity(user));
        return Response.ok(loginResponse).build();
    }
}
