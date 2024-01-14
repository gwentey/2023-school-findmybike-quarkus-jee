package fr.pantheonsorbonne.ufr27.miage.resources;

import fr.pantheonsorbonne.ufr27.miage.dao.BikeDAO;
import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("manager")
@RolesAllowed("user")
public class ManagerResource {
	@Inject
	BikeDAO bikeDAO;

	@Path("bike")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@POST
	public Response createBike(Bike bike) {
		try {
			Bike newBike = bikeDAO.createBike(bike);
			return Response.status(Response.Status.CREATED).entity(newBike).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erreur lors de la création du vélo ! ").build();
		}
	}

	@Path("bike/{bikeId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@PUT
	public Response updateBike(@PathParam("bikeId") int bikeId, Bike bike) {
		try {
			Bike updatedBike = bikeDAO.updateBike(bikeId, bike);
			if (updatedBike != null) {
				return Response.ok(updatedBike).build();
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity("Vélo non trouvé pour cet id").build();
			}
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erreur lors de la mise à jour du vélo !").build();
		}
	}

	@Path("bike/{bikeId}")
	@Produces(MediaType.APPLICATION_JSON)
	@DELETE
	public Response deleteBike(@PathParam("bikeId") int bikeId) {
		try {
			bikeDAO.deleteBike(bikeId);
			return Response.ok().entity("Vélo supprimé avec succès").build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erreur lors de la suppression du vélo !").build();
		}
	}

	@Path("bike/{bikeId}")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Response getBikeById(@PathParam("bikeId") int bikeId) {
		try {
			Bike bike = bikeDAO.findById(bikeId);
			if (bike != null) {
				return Response.ok(bike).build();
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity("Vélo non trouvé pour cet ID").build();
			}
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erreur lors de la recherche du vélo !").build();
		}
	}



}
