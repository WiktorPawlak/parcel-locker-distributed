package pl.pas.domain.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import pl.pas.domain.core.applicationmodel.exceptions.LockerManagerException;
import pl.pas.domain.core.applicationmodel.model.locker.Locker;
import pl.pas.domain.ports.incoming.LockerService;
import pl.pas.domain.rest.controllers.dto.LockerDto;

@Log
@Path(value = "/lockers")
@RequestScope
@NoArgsConstructor
public class LockerController {

    @Autowired
    private LockerService lockerService;

    @GET
    @Path("/{identityNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLocker(@PathParam("identityNumber") String identityNumber) {
        try {
            return Response.ok().entity(lockerService.getLocker(identityNumber)).build();
        } catch (LockerManagerException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addLocker(@Valid LockerDto lockerDto) {
        try {
            Locker newLocker = lockerService.createLocker(
                lockerDto.identityNumber,
                lockerDto.address,
                lockerDto.numberOfBoxes
            );
            return Response.status(Response.Status.CREATED).entity(newLocker).build();
        } catch (ValidationException | NullPointerException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } catch (LockerManagerException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{identityNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeLocker(@PathParam("identityNumber") String identityNumber) {
        try {
            lockerService.removeLocker(identityNumber);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (ValidationException | NullPointerException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } catch (LockerManagerException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
