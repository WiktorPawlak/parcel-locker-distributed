package pl.pas.as.rest.controllers;


import java.util.List;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.annotation.RequestScope;

import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import pl.pas.as.core.model.exceptions.ClientManagerException;
import pl.pas.as.core.model.user.Client;
import pl.pas.as.ports.incoming.UserService;
import pl.pas.as.rest.controllers.dto.ClientDto;
import pl.pas.as.rest.security.JwtProvider;

@Log
@Path(value = "/clients")
@RequestScope
@NoArgsConstructor
public class ClientController {

    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private JwtProvider jwtProvider;

    @GET
    @Path("/{telNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClient(@PathParam("telNumber") String telNumber) {
        try {
            return Response.ok().entity(userService.getUser(telNumber)).build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientsByPhoneNumberPattern(@QueryParam("telNumber") String telNumber) {
        try {
            return Response.ok().entity(userService.getUsersByPartialTelNumber(telNumber)).build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response registerClient(@Valid ClientDto clientDTO) {
        try {
            log.log(Level.INFO, "Registering client...");
            String token = jwtProvider.encode("INTERNAL", List.of());
            Client newUser = userService.registerClient(
                clientDTO.firstName,
                clientDTO.lastName,
                clientDTO.telNumber,
                bCryptPasswordEncoder.encode(clientDTO.password),
                token
            );
            log.log(Level.INFO, "Registering client... Done");
            return Response.status(Response.Status.CREATED).entity(newUser).build();
        } catch (ValidationException | NullPointerException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } catch (ClientManagerException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.TEXT_PLAIN})
    public Response unregisterClient(String telNumber) {
        try {
            Client user = userService.getUser(telNumber);
            String token = jwtProvider.encode("INTERNAL", List.of());
            Client unregisteredUser = userService.unregisterClient(user, token);
            return Response.ok().entity(unregisteredUser).build();
        } catch (ValidationException | NullPointerException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } catch (ClientManagerException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
