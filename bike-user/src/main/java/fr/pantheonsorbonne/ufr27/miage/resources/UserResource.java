package fr.pantheonsorbonne.ufr27.miage.resources;

import fr.pantheonsorbonne.ufr27.miage.dao.BikeDAO;
import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import fr.pantheonsorbonne.ufr27.miage.model.User;
import fr.pantheonsorbonne.ufr27.miage.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("user")
public class UserResource {

    @Context
    SecurityContext securityContext;

    @Inject
    UserService userService;

    @Path("bike/{bikeId}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("user")
    @POST
    public void book(@PathParam("bikeId") int bikeId) {
        User user = (User)securityContext.getUserPrincipal();
        long userId = user.id;
        userService.book(userId, bikeId);
    }

    @Path("bike/{bikeId}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("user")
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
