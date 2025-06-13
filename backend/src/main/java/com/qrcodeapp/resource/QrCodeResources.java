package com.qrcodeapp.resource;

import java.util.List;
import java.util.stream.Collectors;

import com.qrcodeapp.dto.QrCodeDto;
import com.qrcodeapp.model.QrCode;
import com.qrcodeapp.model.User;

import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("/qrcodes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({"user", "admin"})
public class QrCodeResources {

    @Context
    SecurityContext securityContext;

    @GET
    @Path("/my")

    public List<QrCodeDto> getMyQRCodes() {
        String email = securityContext.getUserPrincipal().getName();
        User user = User.find("email", email).firstResult();
        if (user == null) {
            throw new NotFoundException();
        }
        List<QrCode> qrCodes = QrCode.find("user", user).list();
        return qrCodes.stream().map(QrCodeDto::fromEntity).collect(Collectors.toList());
    }

    @POST
    @Transactional
    public Response createQRCode(QrCodeDto dto) {
        String email = securityContext.getUserPrincipal().getName();
        User user = User.find("email", email).firstResult();
        if (user == null) {
            throw new NotFoundException();
        }
        if (user.qrCodes.size() >= user.qrCodeLimit) {
            return Response.status(Response.Status.FORBIDDEN).entity("QR code limit reached").build();
        }
        QrCode qr = new QrCode();
        qr.content = dto.content;
        qr.title = dto.title;
        qr.type = dto.type;
        qr.createdAt = java.time.LocalDateTime.now();
        qr.user = user;
        qr.persist();
        return Response.ok(QrCodeDto.fromEntity(qr)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateQRCode(@PathParam("id") Long id, QrCodeDto dto) {
        String email = securityContext.getUserPrincipal().getName();
        User user = User.find("email", email).firstResult();
        QrCode qr = QrCode.findById(id);
        if (qr == null || !qr.user.id.equals(user.id)) {
            throw new NotFoundException();
        }
        qr.content = dto.content;
        qr.title = dto.title;
        qr.type = dto.type;
        return Response.ok(QrCodeDto.fromEntity(qr)).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteQRCode(@PathParam("id") Long id) {
        String email = securityContext.getUserPrincipal().getName();
        User user = User.find("email", email).firstResult();
        QrCode qr = QrCode.findById(id);
        if (qr == null || !qr.user.id.equals(user.id)) {
            throw new NotFoundException();
        }
        qr.delete();
        return Response.noContent().build();
    }
}
