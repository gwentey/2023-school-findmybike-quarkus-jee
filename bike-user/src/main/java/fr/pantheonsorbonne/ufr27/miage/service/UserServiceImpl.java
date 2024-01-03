package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.camel.BikeGateway;
import fr.pantheonsorbonne.ufr27.miage.dao.BookingDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.UserDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.UserDAOImpl;
import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import fr.pantheonsorbonne.ufr27.miage.model.Booking;
import fr.pantheonsorbonne.ufr27.miage.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Optional;

@ApplicationScoped
public class UserServiceImpl implements UserService {

	@Inject
	BookingDAO bookingDAO;
	@Inject
	UserDAOImpl userDAO;
	@Inject
	BikeGateway bikeGateway;

	@PersistenceContext
	EntityManager em;

	@Override
	public Booking book(int userId, int bikeId) {

		Bike bike = em.find(Bike.class, bikeId);
		System.out.println("BIKE DETECTED :" + bike);
		User user = em.find(User.class, userId);

		Booking booking = bookingDAO.save(bike, user);
		return booking;
	}

	@Override
	public Bike getABikeById(int idBike) {
		return em.find(Bike.class, idBike);
	}

	@Override
	public Bike nextBikeAvailableByPosition(Double positionX, Double positionY) {
		Bike b = bikeGateway.nextBikeAvailableByPosition(positionX,positionY);
		return b;
	}
}
