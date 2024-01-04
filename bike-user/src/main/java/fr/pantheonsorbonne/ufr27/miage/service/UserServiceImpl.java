package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.camel.BikeGatewayImpl;
import fr.pantheonsorbonne.ufr27.miage.dao.BikeDAOImpl;
import fr.pantheonsorbonne.ufr27.miage.dao.BookingDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.UserDAO;
import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import fr.pantheonsorbonne.ufr27.miage.model.Booking;
import fr.pantheonsorbonne.ufr27.miage.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


@ApplicationScoped
public class UserServiceImpl implements UserService {

	@Inject
	BookingDAO bookingDAO;
	@Inject
	BikeGatewayImpl bikeGateway;
	@Inject
	UserDAO userDAO;
	@Inject
	BikeDAOImpl bikeDAO;


	@Override
	public Booking bookABike(long userId, int bikeId) {
		Bike bike = getABikeById(bikeId);
		User user = userDAO.findById(userId);

		if (user != null && bike != null) {
			Bike existingBike = bikeDAO.findById(bike.getIdBike());
			if (existingBike == null) {
				bikeDAO.save(bike);
			} else {
				// Si le vélo existe, mettre à jour les détails
				existingBike.setBatterie(bike.getBatterie());
				existingBike.setPositionX(bike.getPositionX());
				existingBike.setPositionY(bike.getPositionY());
				bikeDAO.merge(existingBike);
				bike = existingBike;
			}

			Booking booking = new Booking();
			booking.setBike(bike);
			booking.setUser(user);
			bookingDAO.save(booking);
			return booking;
		} else {
			throw new RuntimeException("Utilisateur ou vélo introuvable");
		}
	}


	@Override
	public Bike getABikeById(int idBike) {
		Bike b = bikeGateway.getABikeById(idBike);
		return b;	}

	@Override
	public Bike nextBikeAvailableByPosition(Double positionX, Double positionY) {
		Bike b = bikeGateway.nextBikeAvailableByPosition(positionX,positionY);
		return b;
	}
}
