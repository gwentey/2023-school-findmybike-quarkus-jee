package fr.pantheonsorbonne.ufr27.miage.resources;

import fr.pantheonsorbonne.ufr27.miage.dao.UserDAOImpl;
import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import fr.pantheonsorbonne.ufr27.miage.model.Booking;
import fr.pantheonsorbonne.ufr27.miage.model.User;
import fr.pantheonsorbonne.ufr27.miage.service.UserServiceImpl;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("user")
@RolesAllowed("user")
public class UserResource {

    @Context
    SecurityContext securityContext;

    @Inject
    UserServiceImpl userService;

    @Inject
    UserDAOImpl userDAO;

    @Path("bike/available/{positionX}/{positionY}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response bikeAvailable(@PathParam("positionX") double positionX, @PathParam("positionY") double positionY) {
        try {
            Bike b = userService.nextBikeAvailableByPosition(positionX, positionY);
            if (b != null) {
                return Response.ok(b).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Aucun vélo disponible").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erreur interne du serveur").build();
        }
    }

    @Path("bike/{bikeId}")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response bookABike(@PathParam("bikeId") int bikeId) {
        try {
            String username = securityContext.getUserPrincipal().getName();
            User user = userDAO.findByUsername(username);

            Booking booking = userService.bookABike(user.id, bikeId);

            if (booking != null) {
                return Response.ok(booking).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Réservation de vélo échouée").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erreur lors de la réservation: " + e.getMessage()).build();
        }
    }

    @Path("bike/return")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PUT
    public Response returnBike(Bike bike) {
        try {
            String username = securityContext.getUserPrincipal().getName();
            User user = userDAO.findByUsername(username);

            userService.returnBike(user.id, bike);

            return Response.ok().build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Échec du retour du vélo: " + e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erreur lors du retour du vélo: " + e.getMessage()).build();
        }

    }


    @Path("bike/{bikeId}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getABikeById(@PathParam("bikeId") int bikeId) {
        try {
            Bike b = userService.getABikeById(bikeId);
            if (b != null) {
                return Response.ok(b).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Aucun vélo disponible pour cet id").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erreur interne du serveur").build();
        }
    }




}
