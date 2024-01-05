package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.camel.BikeGatewayImpl;
import fr.pantheonsorbonne.ufr27.miage.dao.BikeDAOImpl;
import fr.pantheonsorbonne.ufr27.miage.dao.BookingDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.UserDAO;
import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import fr.pantheonsorbonne.ufr27.miage.model.Booking;
import fr.pantheonsorbonne.ufr27.miage.model.User;
import io.quarkus.logging.Log;
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
			boolean isBikeAlreadyBooked = bookingDAO.isBikeBooked(bikeId);
			if (isBikeAlreadyBooked) {
				throw new RuntimeException("Le vélo est déjà réservé");
			}
			Bike existingBike = bikeDAO.findById(bike.getIdBike());
			if (existingBike == null) {
				bikeDAO.save(bike);
			} else {
				existingBike.setBatterie(bike.getBatterie());
				existingBike.setPositionX(bike.getPositionX());
				existingBike.setPositionY(bike.getPositionY());
				bikeDAO.merge(existingBike);
				bike = existingBike;
			}
			Booking booking = new Booking();
			booking.setBike(bike);
			booking.setUser(user);
			if(bikeGateway.bookBikeById(bike.getIdBike())) {
				bookingDAO.save(booking);
				return booking;
			} else {
				throw new RuntimeException("Erreur coté Manager");
			}
		} else {
			throw new RuntimeException("Utilisateur ou vélo introuvable");
		}
	}

	@Override
	public void returnBike(long userId, Bike bike) {
		User user = userDAO.findById(userId);
		if (user == null) {
			throw new RuntimeException("Utilisateur introuvable");
		}

		Bike returnedBike = bikeDAO.findById(bike.getIdBike());
		if (returnedBike == null) {
			throw new RuntimeException("Vélo introuvable");
		}

		// Vérification si l'utilisateur a une réservation pour ce vélo
		Booking booking = bookingDAO.findBookingByUserIdAndBikeId(userId, returnedBike.getIdBike());
		if (booking == null) {
			throw new RuntimeException("Aucune réservation trouvée pour cet utilisateur et ce vélo");
		} else {
			bookingDAO.deleteBookingByUserIdAndBikeId(userId, returnedBike.getIdBike());

			// Mise à jour du vélo dans le système
			returnedBike.setPositionX(bike.getPositionX());
			returnedBike.setPositionY(bike.getPositionY());
			returnedBike.setBatterie(bike.getBatterie());

			bikeDAO.merge(returnedBike);

			bikeGateway.returnABike(returnedBike);
			Log.info("Vélo " + returnedBike.getIdBike() + " retourné !");
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
