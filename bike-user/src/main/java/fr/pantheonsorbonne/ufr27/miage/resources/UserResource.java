package fr.pantheonsorbonne.ufr27.miage.resources;

import fr.pantheonsorbonne.ufr27.miage.dao.BikeDAO;
import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import fr.pantheonsorbonne.ufr27.miage.service.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("user")
public class UserResource {

    @Inject
    UserService userService;

    @Inject
    BikeDAO bikeDAO;

    @Path("{userId}/bike/{bikeId}")
    @POST
    public void book(@PathParam("userId") int userId, @PathParam("bikeId") int bikeId) {
        userService.book(userId, bikeId);
    }

    @Path("bike/{idBike}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response bike(@PathParam("idBike") int idBike) {
        Bike b = bikeDAO.findById(idBike);
        return Response.ok(b).build();
    }

}
