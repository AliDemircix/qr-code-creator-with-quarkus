package com.qrcodeapp.resource;

import java.util.List;
import java.util.stream.Collectors;

import com.qrcodeapp.dto.AdminUsersDto;
import com.qrcodeapp.model.User;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;

@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({"admin"})
public class AdminResources {

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/users")
    public List<AdminUsersDto> getAdminUsers() {
        String email = securityContext.getUserPrincipal().getName();
        User user = User.find("email", email).firstResult();
        if (user == null) {
            throw new NotFoundException();
        }
        List<User> users = User.listAll();
        return users.stream()
                .map(AdminUsersDto::fromUser)
                .collect(Collectors.toList());
    }

}
