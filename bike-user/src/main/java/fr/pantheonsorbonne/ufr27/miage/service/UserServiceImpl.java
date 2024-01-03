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

	@Override
	public User validateUser(String pseudo, String password) {
		// Récupérer l'utilisateur par son pseudo
		User user = userDAO.findByPseudo(pseudo);

		if (user != null) {
			// Vérifier que le mot de passe est correct
			if (user.getPassword().equals(password)) {
				System.out.println("User OKKKKK");
				return user;
			}
		}
		// Si l'utilisateur n'existe pas ou si le mot de passe est incorrect, retourner null
		System.out.println("User FAUXXXXXXX");
		return null;
	}


	/*
	@Override
	@Transactional
	public Booking book(Booking booking) throws UnsuficientQuotaForVenueException {
		try {
			VenueQuota vq = venueQuotaDAO.getMatchingQuota(booking.getVendorId(), booking.getVenueId(), booking.getStandingTicketsNumber(), booking.getSeatingTicketsNumber());
			vq.setSeatingQuota(vq.getSeatingQuota() - booking.getSeatingTicketsNumber());
			vq.setStandingQuota(vq.getStandingQuota() - booking.getStandingTicketsNumber());

			Venue venue = venueDAO.findById(booking.getVenueId());
			Vendor vendor = vendorDAO.findById(booking.getVendorId());


			for (int i = 0; i < booking.getStandingTicketsNumber(); i++) {
				Ticket ticket = ticketDAO.save(Instant.now().plus(10, ChronoUnit.HOURS), vendor, venue);
				booking.getStandingTransitionalTicket().add(ticket.getId());
			}

			for (int i = 0; i < booking.getSeatingTicketsNumber(); i++) {
				Ticket ticket = ticketDAO.save(Instant.now().plus(10, ChronoUnit.MINUTES), vendor, venue);
				booking.getSeatingTransitionalTicket().add(ticket.getId());
			}
		} catch (NonUniqueResultException | NoResultException e) {
			throw new UnsuficientQuotaForVenueException(booking.getVenueId());
		}
		return booking;
	}
	 */
}
