package fr.pantheonsorbonne.ufr27.miage.resources;

import fr.pantheonsorbonne.ufr27.miage.dao.BikeDAO;
import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import fr.pantheonsorbonne.ufr27.miage.model.User;
import fr.pantheonsorbonne.ufr27.miage.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("user")
public class UserResource {

    @Inject
    UserService userService;

    @Path("{userId}/bike/{bikeId}")
    @POST
    public void book(@PathParam("userId") int userId, @PathParam("bikeId") int bikeId) {
        userService.book(userId, bikeId);
    }

    @Path("bike/available/{positionX}/{positionY}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("user")
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



}
