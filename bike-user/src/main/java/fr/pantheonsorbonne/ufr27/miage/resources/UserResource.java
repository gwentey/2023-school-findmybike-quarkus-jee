package fr.pantheonsorbonne.ufr27.miage.resources;

import fr.pantheonsorbonne.ufr27.miage.dao.UserDAO;
import fr.pantheonsorbonne.ufr27.miage.exception.BikeAlreadyBookedException;
import fr.pantheonsorbonne.ufr27.miage.exception.InternalServorException;
import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import fr.pantheonsorbonne.ufr27.miage.model.Booking;
import fr.pantheonsorbonne.ufr27.miage.model.User;
import fr.pantheonsorbonne.ufr27.miage.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.HashMap;
import java.util.Map;

@Path("user")
@RolesAllowed("user")
public class UserResource {

    @Context
    SecurityContext securityContext;

    @Inject
    UserService userService;

    @Inject
    UserDAO userDAO;

    @Path("bike/available")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response bikeAvailable(@QueryParam("positionX") double positionX, @QueryParam("positionY") double positionY) {
        try {
            Bike b = userService.nextBikeAvailableByPosition(positionX, positionY);
            if (b != null) {
                String itineraireUrl = userService.genererItineraireUrl(positionX, positionY, b);
                Map<String, Object> response = new HashMap<>();
                response.put("bike", b);
                response.put("itineraireUrl", itineraireUrl);

                return Response.ok(response).build();
            } else {
                return new WebApplicationException("Aucun vélo disponible", Response.Status.NOT_FOUND.getStatusCode()).getResponse();
            }
        } catch (InternalServorException e) {
            return new WebApplicationException("Erreur interne du serveur", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).getResponse();
        }
    }

    @Path("bike/{bikeId}/")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Response bookABike(@PathParam("bikeId") int bikeId,
                              @QueryParam("positionX") double userPosX,
                              @QueryParam("positionY") double userPosY) {
        try {
            String username = securityContext.getUserPrincipal().getName();
            User user = userDAO.findByUsername(username);

            Booking booking = userService.bookABike(user.id, bikeId);

            if (booking != null) {
                String itineraireUrl = userService.genererItineraireUrl(userPosX, userPosY, booking.getBike());
                Map<String, Object> response = new HashMap<>();
                response.put("booking", booking);
                response.put("itineraire", itineraireUrl);

                return Response.ok(response).build();
            } else {
                return new WebApplicationException("Réservation de vélo échouée", Response.Status.NOT_FOUND.getStatusCode()).getResponse();
            }
        } catch (BikeAlreadyBookedException e) {
            return new WebApplicationException("Le vélo est déjà réservé", Response.Status.CONFLICT.getStatusCode()).getResponse();
        } catch (Exception e) {
            return new WebApplicationException("Erreur lors de la réservation", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).getResponse();
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
            return new WebApplicationException("Échec du retour du vélo: " + e.getMessage(), Response.Status.NOT_FOUND.getStatusCode()).getResponse();
        } catch (Exception e) {
            return new WebApplicationException("Erreur lors du retour du vélo", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).getResponse();
        }
    }

    @Path("bike/{bikeId}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getABikeById(@PathParam("bikeId") int bikeId) {
        try {
            Bike b = userService.getABikeById(bikeId);
            if (b != null) {
                String mapsUrl = userService.genererUneUrlMaps(b);
                Map<String, Object> response = new HashMap<>();
                response.put("bike", b);
                response.put("mapsUrl", mapsUrl);

                return Response.ok(response).build();
            } else {
                return new WebApplicationException("Aucun vélo disponible pour cet id", Response.Status.NOT_FOUND.getStatusCode()).getResponse();
            }
        } catch (Exception e) {
            return new WebApplicationException("Erreur interne du serveur", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).getResponse();
        }
    }

}