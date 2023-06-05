package pl.pas.domain.rest.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.context.annotation.RequestScope;

import jakarta.persistence.NoResultException;
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
import pl.pas.domain.core.applicationmodel.exceptions.DeliveryManagerException;
import pl.pas.domain.core.applicationmodel.exceptions.LockerException;
import pl.pas.domain.core.applicationmodel.model.delivery.Delivery;
import pl.pas.domain.ports.incoming.DeliveryService;
import pl.pas.domain.rest.controllers.dto.DeliveryListDto;
import pl.pas.domain.rest.controllers.dto.DeliveryParcelDto;

@Log
@Path(value = "/deliveries")
@RequestScope
@NoArgsConstructor
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDelivery(@PathParam("id") UUID id) {
        try {
            return Response.ok().entity(deliveryService.getDelivery(id)).build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/current")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCurrentDeliveries(@QueryParam("telNumber") String telNumber) {
        try {
            return Response.ok().entity(deliveryService.getAllCurrentClientDeliveries(telNumber)).build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/received")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReceivedDelivery(@QueryParam("telNumber") String telNumber) {
        try {
            return Response.ok()
                .entity(deliveryService.getAllReceivedClientDeliveries(telNumber))
                .build();
        } catch (NoResultException | DeliveryManagerException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/list")
    public Response addListDelivery(@Valid DeliveryListDto deliveryListDto) {
        try {
            Delivery delivery =
                deliveryService.makeListDelivery(
                    deliveryListDto.pack.basePrice,
                    deliveryListDto.pack.isPriority,
                    deliveryListDto.shipperTel,
                    deliveryListDto.receiverTel,
                    deliveryListDto.lockerId);
            return Response.status(Response.Status.CREATED).entity(delivery).build();
        } catch (ValidationException | NullPointerException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } catch (DeliveryManagerException | EmptyResultDataAccessException | NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/parcel")
    public Response addParcelDelivery(@Valid DeliveryParcelDto deliveryParcelDto) {
        try {
            Delivery delivery =
                deliveryService.makeParcelDelivery(
                    deliveryParcelDto.pack.basePrice,
                    deliveryParcelDto.pack.width,
                    deliveryParcelDto.pack.height,
                    deliveryParcelDto.pack.length,
                    deliveryParcelDto.pack.weight,
                    deliveryParcelDto.pack.isFragile,
                    deliveryParcelDto.shipperTel,
                    deliveryParcelDto.receiverTel,
                    deliveryParcelDto.lockerId);
            return Response.status(Response.Status.CREATED).entity(delivery).build();
        } catch (ValidationException | NullPointerException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } catch (DeliveryManagerException | NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/put-in")
    public Response putInLocker(
        @PathParam("id") UUID deliveryId,
        @QueryParam("lockerId") String lockerId,
        @QueryParam("accessCode") String accessCode) {
        try {
            deliveryService.putInLocker(deliveryId, lockerId, accessCode);
            return Response.ok().build();
        } catch (ValidationException | NullPointerException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } catch (DeliveryManagerException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (LockerException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/take-out")
    public Response takeOutDelivery(
        @PathParam("id") UUID deliveryId,
        @QueryParam("telNumber") String telNumber,
        @QueryParam("accessCode") String accessCode) {
        try {
            deliveryService.takeOutDelivery(deliveryId, telNumber, accessCode);
            return Response.ok().build();
        } catch (ValidationException | NullPointerException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } catch (DeliveryManagerException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (LockerException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        }
    }
}
