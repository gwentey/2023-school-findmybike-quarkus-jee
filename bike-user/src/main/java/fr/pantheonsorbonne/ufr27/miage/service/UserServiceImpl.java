package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.camel.BikeGatewayImpl;
import fr.pantheonsorbonne.ufr27.miage.dao.BikeDAOImpl;
import fr.pantheonsorbonne.ufr27.miage.dao.BookingDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.UserDAO;
import fr.pantheonsorbonne.ufr27.miage.exception.*;
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
	public Booking bookABike(long userId, int bikeId) throws BikeAlreadyBookedException {
		Bike bike = getABikeById(bikeId);
		User user = userDAO.findById(userId);

		if (user != null && bike != null) {
			boolean isBikeAlreadyBooked = bookingDAO.isBikeBooked(bikeId);
			if (isBikeAlreadyBooked) {
				throw new BikeAlreadyBookedException(bikeId);
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
				throw new ManagerException();
			}
		} else {
			throw new UserOrBikeNotFoundException();
		}
	}

	@Override
	public void returnBike(long userId, Bike bike) throws NoBookingException.NoBookingUserIDBikeID {
		User user = userDAO.findById(userId);
		if (user == null) {
			throw new NoUserFound.NoUserFoundByID((int)userId);
		}

		Bike returnedBike = bikeDAO.findById(bike.getIdBike());
		if (returnedBike == null) {
			throw new NoBikeFound.NoBikeFoundByID(bike.getIdBike());
		}

		// Vérification si l'utilisateur a une réservation pour ce vélo
		Booking booking = bookingDAO.findBookingByUserIdAndBikeId(userId, returnedBike.getIdBike());
		if (booking == null) {
			throw new NoBookingException.NoBookingUserIDBikeID((int)userId,returnedBike.getIdBike());
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
	public String genererItineraireUrl(double userPosX, double userPosY, Bike bike) {
		return "https://www.google.fr/maps/dir/" + userPosY + "," + userPosX + "/" +
				bike.getPositionY() + "," + bike.getPositionX() +
				"/data=!3m1!4b1!4m2!4m1!3e2?entry=ttu";
	}

	@Override
	public String genererUneUrlMaps(Bike bike) {
		return "https://www.google.fr/maps/dir/" + bike.getPositionY() + "," + bike.getPositionX();
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
